package com.zadyraichuk.point_cinema.repository;

import com.zadyraichuk.point_cinema.entity.Cinema;
import com.zadyraichuk.point_cinema.entity.Country;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Cinema repository tests")
@DataMongoTest
@ActiveProfiles("test")
class CinemaRepositoryTest {

    @Autowired
    private CinemaRepository cinemaRepository;

    private static Cinema cinema;

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
    @DisplayName("Test creating a Cinema")
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
        Optional<Cinema> foundCinemaOpt = cinemaRepository.findById(cinema.getId());
        assertTrue(foundCinemaOpt.isPresent(), "The cinema should be found by ID");

        Cinema foundCinema = foundCinemaOpt.get();
        assertEquals(cinema.getId(), foundCinema.getId(), "The IDs should match");
        assertEquals(cinema.getName(), foundCinema.getName(), "The cinema name should match");
        assertEquals(cinema.getCountry(), foundCinema.getCountry(), "The cinema country should match");
        assertEquals(cinema.getCity(), foundCinema.getCity(), "The cinema city should match");
        assertEquals(cinema.getStreet(), foundCinema.getStreet(), "The cinema street should match");
    }

    @Test
    @DisplayName("Test finding all Cinemas")
    void testFindAll() {
        Cinema[] cinemas = getCinemasForGeneralCrudTests();
        cinemaRepository.saveAll(Arrays.asList(cinemas));
        int expectedSize = cinemas.length + 1;

        List<Cinema> foundCinemas = cinemaRepository.findAll();
        assertNotNull(foundCinemas, "The found list should not be null");
        assertFalse(foundCinemas.isEmpty(), "The found list should not be empty");
        assertEquals(expectedSize, foundCinemas.size(), "The size of the list should match the number of cinemas saved");
        assertTrue(foundCinemas.containsAll(Arrays.asList(cinemas)), "The found cinemas should contain all saved cinemas");
    }

    @Test
    @DisplayName("Test updating a Cinema")
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
        Stream<String> cinemaIds = savedCinemas.stream().map(Cinema::getId);
        assertTrue(cinemaIds.allMatch(Objects::nonNull), "Each saved cinema should have a generated ID");

        List<Cinema> foundCinemas = cinemaRepository.findAll();
        int expectedSize = cinemas.length + 1;
        assertEquals(expectedSize, foundCinemas.size(), "The number of cinemas found should match the saved cinemas");
        assertTrue(foundCinemas.containsAll(savedCinemas), "The found cinemas should match the saved cinemas");
    }

    @Test
    @DisplayName("Test deleting a Cinema")
    void testDelete() {
        cinemaRepository.delete(cinema);

        Optional<Cinema> deletedCinemaOpt = cinemaRepository.findById(cinema.getId());
        assertFalse(deletedCinemaOpt.isPresent(), "The cinema should be deleted and not found by ID");
    }

    @Test
    @DisplayName("Test deleting a Cinema by ID")
    void testDeleteById() {
        cinemaRepository.deleteById(cinema.getId());

        Optional<Cinema> deletedCinemaOpt = cinemaRepository.findById(cinema.getId());
        assertFalse(deletedCinemaOpt.isPresent(), "The cinema should be deleted and not found by ID");
    }

    @Test
    @DisplayName("Test deleting all Cinemas")
    void testDeleteAll() {
        Cinema[] cinemas = getCinemasForGeneralCrudTests();
        cinemaRepository.saveAll(Arrays.asList(cinemas));

        List<Cinema> foundCinemas = cinemaRepository.findAll();
        assertNotNull(foundCinemas, "The cinemas should exist before deleting");
        assertFalse(foundCinemas.isEmpty(), "The cinemas should exist before deleting");

        cinemaRepository.deleteAll();
        foundCinemas = cinemaRepository.findAll();
        assertNotNull(foundCinemas, "The cinemas should be deleted and cinemas list should not be null");
        assertTrue(foundCinemas.isEmpty(), "The cinemas should be deleted and cinemas list should be empty");
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForFindByCountryAndCityTest")
    @DisplayName("Test finding a Cinema by Country and City")
    void testFindByCountryAndCity(Country country,
                                  String city,
                                  int expectedSize,
                                  int[] expectedCinemaIndexes) {
        Cinema[] cinemas = getCinemasForSpecializedTests();
        cinemaRepository.saveAll(Arrays.asList(cinemas));

        List<Cinema> foundCinemas = cinemaRepository.findByCountryAndCity(country, city);

        assertNotNull(foundCinemas, "The result should not be null");
        assertEquals(expectedSize, foundCinemas.size(),
                String.format("The result should contain %d cinemas with city pattern '%s'", expectedSize, city));
        for (int i : expectedCinemaIndexes) {
            assertTrue(foundCinemas.contains(cinemas[i]), "Cinema should be in the result");
        }
    }

    private Cinema initCinema() {
        return new Cinema("test", Country.UKRAINE, "test", "test");
    }

    private Cinema[] getCinemasForGeneralCrudTests() {
        Cinema unique1 = new Cinema("1", cinema.getCountry(), "1", "1");
        Cinema unique2 = new Cinema("2", cinema.getCountry(), "2", "2");
        Cinema unique3 = new Cinema("3", cinema.getCountry(), "3", "3");

        return new Cinema[]{unique1, unique2, unique3};
    }

    private Cinema[] getCinemasForSpecializedTests() {
        Cinema cinema1 = new Cinema("1", Country.UKRAINE, "Lviv", "1");
        Cinema cinema2 = new Cinema("2", Country.UNITED_KINGDOM, "Nottingham", "2");
        Cinema cinema3 = new Cinema("3", Country.UKRAINE, "Kharkiv", "3");
        Cinema cinema4 = new Cinema("4", Country.POLAND, "Warsaw", "4");

        return new Cinema[]{cinema1, cinema2, cinema3, cinema4};
    }

    /**
     * Provides arguments for testing create and update methods in {@link CinemaRepository}.
     * The arguments are:
     * <ul>
     *     <li>country - the country to search for cinemas</li>
     *     <li>city - the city pattern to search for cinemas</li>
     *     <li>expectedSize - the expected number of cinemas matching the criteria</li>
     *     <li>expectedCinemaIndexes - the indexes of expected cinemas in the test data</li>
     * </ul>
     */
    private static Stream<Arguments> provideArgumentsForFindByCountryAndCityTest() {
        return Stream.of(
                Arguments.of(Country.UKRAINE, "a", 1, new int[]{2}),
                Arguments.of(Country.UNITED_KINGDOM, "a", 1, new int[]{1}),
                Arguments.of(Country.POLAND, "a", 1, new int[]{3}),
                Arguments.of(Country.UKRAINE, "iv", 2, new int[]{0, 2}),
                Arguments.of(Country.POLAND, "iv", 0, new int[]{}),
                Arguments.of(Country.UKRAINE, "Lviv", 1, new int[]{0}),
                Arguments.of(Country.UKRAINE, "iv", 2, new int[]{0, 2}),
                Arguments.of(Country.UKRAINE, "Iv", 2, new int[]{0, 2}),
                Arguments.of(Country.UKRAINE, "iV", 2, new int[]{0, 2}),
                Arguments.of(Country.UKRAINE, "IV", 2, new int[]{0, 2}),
                Arguments.of(Country.UKRAINE, "", 3, new int[]{0, 2}),
                Arguments.of(Country.UNITED_KINGDOM, "", 1, new int[]{1}),
                Arguments.of(Country.POLAND, "", 1, new int[]{3}),
                Arguments.of(Country.UKRAINE, "123", 0, new int[]{}),
                Arguments.of(Country.UNITED_KINGDOM, "123", 0, new int[]{}),
                Arguments.of(Country.POLAND, "123", 0, new int[]{})
        );
    }

}