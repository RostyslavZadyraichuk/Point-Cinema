package com.zadyraichuk.point_cinema.repository;

import com.zadyraichuk.point_cinema.entity.Cinema;
import com.zadyraichuk.point_cinema.entity.Country;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Cinema repository tests")
@DataMongoTest
@ActiveProfiles("test")
class CinemaRepositoryTest {

    @Autowired
    private CinemaRepository cinemaRepository;

    private Cinema cinema;

    @BeforeEach
    void setUp() {
        cinema = initCinema();
        cinema = cinemaRepository.save(cinema);
    }

    @AfterEach
    void afterEach() {
        cinemaRepository.deleteAll();
    }

    @Test
    @DisplayName("Test creating an Cinema")
    void testCreate() {
        Cinema cinemaLocal = new Cinema("Created", Country.UNITED_KINGDOM, "Created", "Created");
        assertNull(cinemaLocal.getId(), "The cinema has just created should have no ID");

        Cinema savedCinema = cinemaRepository.save(cinemaLocal);
        assertNotNull(savedCinema.getId(), "The saved cinema should have a generated ID");
        assertEquals(cinemaLocal.getName(), savedCinema.getName(), "The cinema name should match");
        assertEquals(cinemaLocal.getCountry(), savedCinema.getCountry(), "The cinema country should match");
        assertEquals(cinemaLocal.getCity(), savedCinema.getCity(), "The cinema city should match");
        assertEquals(cinemaLocal.getStreet(), savedCinema.getStreet(), "The cinema street should match");
    }

    @Test
    @DisplayName("Test finding a Cinema by ID")
    void testFindById() {
        Optional<Cinema> retrievedCinemaOpt = cinemaRepository.findById(cinema.getId());
        assertTrue(retrievedCinemaOpt.isPresent(), "The cinema should be found by ID");

        Cinema retrievedCinema = retrievedCinemaOpt.get();
        assertEquals(cinema.getId(), retrievedCinema.getId(), "The IDs should match");
        assertEquals(cinema.getName(), retrievedCinema.getName(), "The cinema name should match");
        assertEquals(cinema.getCountry(), retrievedCinema.getCountry(), "The cinema country should match");
        assertEquals(cinema.getCity(), retrievedCinema.getCity(), "The cinema city should match");
        assertEquals(cinema.getStreet(), retrievedCinema.getStreet(), "The cinema street should match");
    }

    @Test
    @DisplayName("Test finding all Cinemas")
    void testFindAll() {
        Cinema[] cinemas = getCinemasForGeneralCrudTests();
        cinemaRepository.saveAll(Arrays.asList(cinemas));
        int expectedSize = cinemas.length + 1;

        List<Cinema> retrievedCinemas = cinemaRepository.findAll();
        assertNotNull(retrievedCinemas, "The retrieved list should not be null");
        assertFalse(retrievedCinemas.isEmpty(), "The retrieved list should not be empty");
        assertEquals(expectedSize, retrievedCinemas.size(), "The size of the list should match the number of cinemas saved");

        List<String> retrievedCinemaIds = retrievedCinemas.stream()
                .map(Cinema::getId)
                .toList();
        for (Cinema c : cinemas) {
            assertTrue(retrievedCinemaIds.contains(c.getId()), "The retrieved cinemas should contain all saved cinemas");
        }
    }

    @Test
    @DisplayName("Test updating an Cinema")
    void testUpdate() {
        Cinema updatedCinema = new Cinema("UpdatedName", Country.POLAND, "UpdatedCity", "UpdatedStreet");
        updatedCinema.setId(cinema.getId());
        cinema = cinemaRepository.save(updatedCinema);

        assertEquals(updatedCinema.getId(), cinema.getId(), "The ID should remain the same after update");
        assertEquals(updatedCinema.getName(), cinema.getName(), "The name should be updated");
        assertEquals(updatedCinema.getCountry(), cinema.getCountry(), "The country should be updated");
        assertEquals(updatedCinema.getCity(), cinema.getCity(), "The city should be updated");
        assertEquals(updatedCinema.getStreet(), cinema.getStreet(), "The street should be updated");
    }

    @Test
    @DisplayName("Test save all Cinemas")
    void testSaveAll() {
        Cinema[] cinemas = getCinemasForGeneralCrudTests();

        List<Cinema> savedCinemas = cinemaRepository.saveAll(Arrays.asList(cinemas));

        assertNotNull(savedCinemas, "The saved cinemas list should not be null");
        assertEquals(cinemas.length, savedCinemas.size(), "The size of the saved cinemas should match the input list size");
        for (Cinema c : savedCinemas) {
            assertNotNull(c.getId(), "Each saved cinema should have a generated ID");
        }

        List<Cinema> retrievedCinemas = cinemaRepository.findAll();
        int expectedSize = cinemas.length + 1;
        assertEquals(expectedSize, retrievedCinemas.size(), "The number of cinemas retrieved should match the saved cinemas");
        assertTrue(retrievedCinemas.containsAll(savedCinemas), "The retrieved cinemas should match the saved cinemas");
    }

    @Test
    @DisplayName("Test deleting an Cinema")
    void testDelete() {
        cinemaRepository.delete(cinema);

        Optional<Cinema> deletedCinemaOpt = cinemaRepository.findById(cinema.getId());
        assertFalse(deletedCinemaOpt.isPresent(), "The cinema should be deleted and not found by ID");
    }

    @Test
    @DisplayName("Test deleting all Cinemas")
    void testDeleteAll() {
        Cinema[] cinemas = getCinemasForGeneralCrudTests();
        cinemaRepository.saveAll(Arrays.asList(cinemas));

        List<Cinema> actual = cinemaRepository.findAll();
        assertNotNull(actual, "The cinemas should exist before deleting");
        assertFalse(actual.isEmpty(), "The cinemas should exist before deleting");

        cinemaRepository.deleteAll();
        actual = cinemaRepository.findAll();
        assertNotNull(actual, "The cinemas should be deleted and cinemas list should not be null");
        assertTrue(actual.isEmpty(), "The cinemas should be deleted and cinemas list should be empty");
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', textBlock = """
                UKRAINE        | a    | 1 | 2
                UNITED_KINGDOM | a    | 1 | 1
                POLAND         | a    | 1 | 3
                UKRAINE        | iv   | 2 | 0,2
                POLAND         | iv   | 0 | ''
                UKRAINE        | Lviv | 1 | 0
            """)
    @DisplayName("Test finding a Cinema by Country and City with valid values")
    void testFindByCountryAndCityContaining_whenValidPattern(Country country, String pattern, int expected, String cinemasIndexes) {
        Cinema[] cinemas = getCinemasForSpecializedFindTests();
        cinemaRepository.saveAll(Arrays.asList(cinemas));
        int[] indexes = cinemasIndexes.isEmpty() ? new int[0] : Arrays.stream(cinemasIndexes.split(","))
                .mapToInt(Integer::parseInt)
                .toArray();

        List<Cinema> actual = cinemaRepository.findAllByCountryAndCityContainingIgnoreCase(country, pattern);
        assertNotNull(actual, "The result should not be null");
        assertEquals(expected, actual.size(), String.format("The result should contain %d cinemas with pattern '%s'", expected, pattern));
        for (int i : indexes) {
            assertTrue(actual.contains(cinemas[i]), "Cinema should be in the result");
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"iv", "Iv", "iV", "IV"})
    @DisplayName("Test finding a Cinema by Country and City with values in different case")
    void testFindByCountryAndCityContaining_whenPatternInDifferentCase(String pattern) {
        Cinema[] cinemas = getCinemasForSpecializedFindTests();
        cinemaRepository.saveAll(Arrays.asList(cinemas));

        Country country = Country.UKRAINE;
        int expected = 2;
        List<Cinema> actual = cinemaRepository.findAllByCountryAndCityContainingIgnoreCase(country, pattern);

        assertNotNull(actual, "The result should not be null");
        assertEquals(expected, actual.size(), "The result should contain 2 cinemas with pattern '" + pattern + "'");
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', textBlock = """
                    UKRAINE        | 3
                    UNITED_KINGDOM | 1
                    POLAND         | 1
                """)
    @DisplayName("Test finding a Cinema by Country and City with empty String pattern")
    void testFindByFirstNameRegexOrLastNameRegex_whenEmptyStringPattern(Country country, int expected) {
        Cinema[] cinemas = getCinemasForSpecializedFindTests();
        cinemaRepository.saveAll(Arrays.asList(cinemas));

        String pattern = "";
        List<Cinema> actual = cinemaRepository.findAllByCountryAndCityContainingIgnoreCase(country, pattern);

        assertNotNull(actual, "The result should not be null");
        assertEquals(expected, actual.size(), String.format("The result should contain all cinemas with country %s and empty String pattern", country));
    }

    @ParameterizedTest
    @EnumSource(value = Country.class, names = {"UNITED_KINGDOM", "UKRAINE", "POLAND"})
    @DisplayName("Test finding a Cinema by Country and City with invalid pattern")
    void testFindByFirstNameRegexOrLastNameRegex_whenInvalidPattern(Country country) {
        Cinema[] cinemas = getCinemasForSpecializedFindTests();
        cinemaRepository.saveAll(Arrays.asList(cinemas));

        String pattern = "123";
        List<Cinema> actual = cinemaRepository.findAllByCountryAndCityContainingIgnoreCase(country, pattern);

        assertNotNull(actual, "The result should not be null");
        assertTrue(actual.isEmpty(), String.format("The result should contain all cinemas with country %s and city pattern %s", country, pattern));
    }

    private Cinema initCinema() {
        return new Cinema("test", Country.UKRAINE, "test", "test");
    }

    private Cinema[] getCinemasForGeneralCrudTests() {
        Cinema unique1 = new Cinema("1", cinema.getCountry(), "1", "1");
        Cinema unique2 = new Cinema("2", cinema.getCountry(), "2", "2");
        Cinema unique3 = new Cinema("3", cinema.getCountry(), "3", "3");

        return new Cinema[] {unique1, unique2, unique3};
    }

    private Cinema[] getCinemasForSpecializedFindTests() {
        Cinema cinema1 = new Cinema("1", Country.UKRAINE, "Lviv", "1");
        Cinema cinema2 = new Cinema("2", Country.UNITED_KINGDOM, "Nottingham", "2");
        Cinema cinema3 = new Cinema("3", Country.UKRAINE, "Kharkiv", "3");
        Cinema cinema4 = new Cinema("4", Country.POLAND, "Warsaw", "4");

        return new Cinema[] {cinema1, cinema2, cinema3, cinema4};
    }

}