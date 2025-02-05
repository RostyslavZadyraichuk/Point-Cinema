package com.zadyraichuk.point_cinema.repository;

import com.zadyraichuk.point_cinema.entity.Day;
import com.zadyraichuk.point_cinema.entity.Language;
import com.zadyraichuk.point_cinema.entity.Seance;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Seance repository tests")
@DataMongoTest
@ActiveProfiles("test")
class SeanceRepositoryTest {

    @Autowired
    private SeanceRepository seanceRepository;

    private static Seance seance;

    @BeforeEach
    void setUp() {
        seance = initSeance();
        seance = seanceRepository.save(seance);
    }

    @AfterEach
    void afterEach() {
        seanceRepository.deleteAll();
    }

    @Test
    @DisplayName("Test creating an Seance")
    void testCreate() {
        Seance seanceLocal = initSeance();
        assertNull(seanceLocal.getId(), "The seance has just created should have no ID");

        Seance savedSeance = assertDoesNotThrow(() -> seanceRepository.save(seanceLocal));
        assertNotNull(savedSeance.getId(), "The saved seance should have a generated ID");
        assertEquals(seanceLocal.getStartSeance(), savedSeance.getStartSeance(), "The start seance should match");
        assertEquals(seanceLocal.getEndSeance(), savedSeance.getEndSeance(), "The end seance should match");
        assertEquals(seanceLocal.getDateFrom(), savedSeance.getDateFrom(), "The date from should match");
        assertEquals(seanceLocal.getDateTo(), savedSeance.getDateTo(), "The date to should match");
        assertEquals(seanceLocal.getTicketPrice(), savedSeance.getTicketPrice(), "The ticket price should match");
        assertEquals(seanceLocal.getHallId(), savedSeance.getHallId(), "The hall ID should match");
        assertEquals(seanceLocal.getMovieId(), savedSeance.getMovieId(), "The movie ID should match");
        assertEquals(seanceLocal.getSeanceLanguage(), savedSeance.getSeanceLanguage(), "The seance language should match");
        assertEquals(seanceLocal.getDays(), savedSeance.getDays(), "The days should match");
    }

    @Test
    @DisplayName("Test finding an Seance by ID")
    void testFindById() {
        Optional<Seance> foundSeanceOpt = seanceRepository.findById(seance.getId());
        assertTrue(foundSeanceOpt.isPresent(), "The seance should be found by ID");

        Seance foundSeance = foundSeanceOpt.get();
        assertEquals(seance.getId(), foundSeance.getId(), "The IDs should match");
        assertEquals(seance.getStartSeance(), foundSeance.getStartSeance(), "The start seance should match");
        assertEquals(seance.getEndSeance(), foundSeance.getEndSeance(), "The end seance should match");
        assertEquals(seance.getDateFrom(), foundSeance.getDateFrom(), "The date from should match");
        assertEquals(seance.getDateTo(), foundSeance.getDateTo(), "The date to should match");
        assertEquals(seance.getTicketPrice(), foundSeance.getTicketPrice(), "The ticket price should match");
        assertEquals(seance.getHallId(), foundSeance.getHallId(), "The hall ID should match");
        assertEquals(seance.getMovieId(), foundSeance.getMovieId(), "The movie ID should match");
        assertEquals(seance.getSeanceLanguage(), foundSeance.getSeanceLanguage(), "The seance language should match");
        assertEquals(seance.getDays(), foundSeance.getDays(), "The days should match");
    }

    @Test
    @DisplayName("Test finding all Seances")
    void testFindAll() {
        Seance[] seances = getSeancesForGeneralCrudTests();
        seanceRepository.saveAll(Arrays.asList(seances));
        int expectedLength = seances.length + 1;
        List<Seance> foundSeances = seanceRepository.findAll();

        assertNotNull(foundSeances, "The found list should not be null");
        assertFalse(foundSeances.isEmpty(), "The found list should not be empty");
        assertEquals(expectedLength, foundSeances.size(), "The size of the list should match the number of seances saved");
        assertTrue(foundSeances.containsAll(List.of(seances)), "The found seances should contain all seances saved before");
    }

    @Test
    @DisplayName("Test updating an Seance")
    void testUpdate() {
        Seance seanceBeforeUpdate = Seance.builder()
                .startSeance(LocalTime.of(10, 0))
                .endSeance(LocalTime.of(11, 0))
                .dateFrom(LocalDate.of(1, 1, 1))
                .dateTo(LocalDate.of(1, 1, 1))
                .ticketPrice(1.0)
                .hallId("beforeUpdate")
                .movieId("beforeUpdate")
                .seanceLanguage(Language.PL)
                .days(List.of(Day.MONDAY))
                .build();
        seanceBeforeUpdate = seanceRepository.save(seanceBeforeUpdate);
        Seance updatedSeance = initSeance();
        updatedSeance.setId(seanceBeforeUpdate.getId());

        seance = assertDoesNotThrow(() -> seanceRepository.save(updatedSeance));
        assertEquals(updatedSeance.getId(), seance.getId(), "The ID should remain the same after update");
        assertEquals(updatedSeance.getStartSeance(), seance.getStartSeance(), "The start seance should match");
        assertEquals(updatedSeance.getEndSeance(), seance.getEndSeance(), "The end seance should match");
        assertEquals(updatedSeance.getDateFrom(), seance.getDateFrom(), "The date from should match");
        assertEquals(updatedSeance.getDateTo(), seance.getDateTo(), "The date to should match");
        assertEquals(updatedSeance.getTicketPrice(), seance.getTicketPrice(), "The ticket price should match");
        assertEquals(updatedSeance.getHallId(), seance.getHallId(), "The hall ID should match");
        assertEquals(updatedSeance.getMovieId(), seance.getMovieId(), "The movie ID should match");
        assertEquals(updatedSeance.getSeanceLanguage(), seance.getSeanceLanguage(), "The seance language should match");
        assertEquals(updatedSeance.getDays(), seance.getDays(), "The days should match");
    }

    @Test
    @DisplayName("Test save all Seances")
    void testSaveAll() {
        Seance[] seances = getSeancesForGeneralCrudTests();

        List<Seance> savedSeances = seanceRepository.saveAll(Arrays.asList(seances));

        assertNotNull(savedSeances, "The saved seances list should not be null");
        assertEquals(seances.length, savedSeances.size(), "The size of the saved seances should match the input list size");
        Stream<String> seanceIds = savedSeances.stream().map(Seance::getId);
        assertTrue(seanceIds.allMatch(Objects::nonNull), "Each saved seance should have a generated ID");

        List<Seance> foundSeances = seanceRepository.findAll();
        int expectedSize = seances.length + 1;
        assertEquals(expectedSize, foundSeances.size(), "The number of seances found should match the saved seances");
        assertTrue(foundSeances.containsAll(savedSeances), "The found seances should match the saved seances");
    }

    @Test
    @DisplayName("Test deleting an Seance")
    void testDelete() {
        seanceRepository.delete(seance);

        Optional<Seance> deletedSeanceOpt = seanceRepository.findById(seance.getId());
        assertFalse(deletedSeanceOpt.isPresent(), "The seance should be deleted and not found by ID");
    }

    @Test
    @DisplayName("Test deleting an Seance by ID")
    void testDeleteById() {
        seanceRepository.deleteById(seance.getId());

        Optional<Seance> deletedSeanceOpt = seanceRepository.findById(seance.getId());
        assertFalse(deletedSeanceOpt.isPresent(), "The seance should be deleted and not found by ID");
    }

    @Test
    @DisplayName("Test deleting all Seances")
    void testDeleteAll() {
        Seance[] seances = getSeancesForGeneralCrudTests();
        seanceRepository.saveAll(Arrays.asList(seances));

        List<Seance> foundSeances = seanceRepository.findAll();
        assertNotNull(foundSeances, "The seances should exist before deleting");
        assertFalse(foundSeances.isEmpty(), "The seances should exist before deleting");

        seanceRepository.deleteAll();
        foundSeances = seanceRepository.findAll();
        assertNotNull(foundSeances, "The seances should be deleted and seances list should not be null");
        assertTrue(foundSeances.isEmpty(), "The seances should be deleted and seances list should be empty");
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForFindByHallIdTest")
    @DisplayName("Test finding an Seance by Hall Id")
    void testFindByHallId(String hallId,
                          int expectedSize,
                          int[] expectedSeanceIndexes) {
        Seance[] seances = getSeancesForSpecializedTests();
        seanceRepository.saveAll(Arrays.asList(seances));

        Pageable pageable = PageRequest.of(0, 10);
        Page<Seance> foundSeances = seanceRepository.findByHallId(hallId, pageable);

        assertNotNull(foundSeances, "The result should not be null");
        assertEquals(expectedSize, foundSeances.getTotalElements(),
                String.format("The result should contain %d seances with hallId '%s'", expectedSize, hallId));
        for (int i : expectedSeanceIndexes) {
            assertTrue(foundSeances.getContent().contains(seances[i]), "Seance should be in the result");
        }
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForFindByMovieIdTest")
    @DisplayName("Test finding an Seance by Movie Id")
    void testFindByMovieId(String movieId,
                           int expectedSize,
                           int[] expectedSeanceIndexes) {
        Seance[] seances = getSeancesForSpecializedTests();
        seanceRepository.saveAll(Arrays.asList(seances));

        Pageable pageable = PageRequest.of(0, 10);
        Page<Seance> foundSeances = seanceRepository.findByMovieId(movieId, pageable);

        assertNotNull(foundSeances, "The result should not be null");
        assertEquals(expectedSize, foundSeances.getTotalElements(),
                String.format("The result should contain %d seances with movieId '%s'", expectedSize, movieId));
        for (int i : expectedSeanceIndexes) {
            assertTrue(foundSeances.getContent().contains(seances[i]), "Seance should be in the result");
        }
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForFindByDateIsInSeanceDatesRangeTest")
    @DisplayName("Test finding an Seance by Date is in Seance dates range")
    void testFindByDateIsInSeanceDatesRange(LocalDate date,
                                            int expectedSize,
                                            int[] expectedSeanceIndexes) {
        Seance[] seances = getSeancesForSpecializedTests();
        seanceRepository.saveAll(Arrays.asList(seances));

        Pageable pageable = PageRequest.of(0, 10);
        Page<Seance> foundSeances = seanceRepository.findByDateIsInSeanceDatesRange(date, pageable);

        assertNotNull(foundSeances, "The result should not be null");
        assertEquals(expectedSize, foundSeances.getTotalElements(),
                String.format("The result should contain %d seances with date '%s' in range", expectedSize, date));
        for (int i : expectedSeanceIndexes) {
            assertTrue(foundSeances.getContent().contains(seances[i]), "Seance should be in the result");
        }
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForDeleteByDateToBeforeTest")
    @DisplayName("Test deleting Seances by Date To is before")
    void testDeleteByDateToBefore(LocalDate date,
                                  int expectedRemovedSize,
                                  int[] expectedRemovedRatingIndexes) {
        Seance[] seances = getSeancesForSpecializedTests();
        seanceRepository.saveAll(Arrays.asList(seances));

        seanceRepository.deleteByDateToBefore(date);
        List<Seance> foundSeances = seanceRepository.findAll();

        assertNotNull(foundSeances, "The result should not be null");
        assertEquals(expectedRemovedSize, seances.length - foundSeances.size() + 1,
                String.format("The result should contain %d seances with dateTo before '%s'", expectedRemovedSize, date));
        for (int i : expectedRemovedRatingIndexes) {
            assertFalse(foundSeances.contains(seances[i]), "Seance should not be in the result");
        }
    }

    private Seance initSeance() {
        return Seance.builder()
                .startSeance(LocalTime.of(1, 1, 1))
                .endSeance(LocalTime.of(1, 1, 1))
                .dateFrom(LocalDate.of(1, 1, 1))
                .dateTo(LocalDate.of(1, 1, 1))
                .ticketPrice(1.0)
                .hallId("test")
                .movieId("test")
                .seanceLanguage(Language.EN)
                .days(List.of(Day.MONDAY, Day.TUESDAY, Day.WEDNESDAY))
                .build();
    }

    private Seance[] getSeancesForGeneralCrudTests() {
        Seance unique1 = initSeance();
        Seance unique2 = initSeance();
        Seance unique3 = initSeance();

        return new Seance[]{unique1, unique2, unique3};
    }

    private Seance[] getSeancesForSpecializedTests() {
        Seance seance1 = new Seance(null, LocalTime.of(0, 0), LocalTime.of(1, 1),
                LocalDate.of(1, 1, 1), LocalDate.of(10, 10, 10),
                1.0, "1", "1", Language.UA, Set.of(Day.MONDAY, Day.TUESDAY, Day.WEDNESDAY));
        Seance seance2 = new Seance(null, LocalTime.of(2, 2), LocalTime.of(4, 4),
                LocalDate.of(3, 3, 3), LocalDate.of(7, 7, 7),
                1.0, "2", "1", Language.EN, Set.of(Day.FRIDAY));
        Seance seance3 = new Seance(null, LocalTime.of(1, 0), LocalTime.of(5, 0),
                LocalDate.of(9, 9, 9), LocalDate.of(11, 11, 11),
                1.0, "2", "2", Language.UA, Set.of(Day.SUNDAY, Day.MONDAY, Day.FRIDAY));
        Seance seance4 = new Seance(null, LocalTime.of(3, 0), LocalTime.of(8, 0),
                LocalDate.of(5, 5, 5), LocalDate.of(20, 1, 1),
                1.0, "3", "3", Language.PL, Set.of(Day.FRIDAY));

        return new Seance[]{seance1, seance2, seance3, seance4};
    }

    /**
     * Provides arguments for testing the testFindByHallId method in {@link SeanceRepository}.
     * The arguments are:
     * <ul>
     *     <li>hallId - string with hall identifier</li>
     *     <li>expectedSize - the expected number of seances matching the hall identifier</li>
     *     <li>expectedSeanceIndexes - the indexes of expected seances in the test data</li>
     * </ul>
     *
     * @return a stream of arguments for parameterized tests
     */
    private static Stream<Arguments> provideArgumentsForFindByHallIdTest() {
        return Stream.of(
                Arguments.of("0", 0, new int[]{}),
                Arguments.of("1", 1, new int[]{0}),
                Arguments.of("2", 2, new int[]{1, 2})
        );
    }

    /**
     * Provides arguments for testing the testFindByMovieId method in {@link SeanceRepository}.
     * The arguments are:
     * <ul>
     *     <li>movieId - string with movie identifier</li>
     *     <li>expectedSize - the expected number of seances matching the movie identifier</li>
     *     <li>expectedSeanceIndexes - the indexes of expected seances in the test data</li>
     * </ul>
     *
     * @return a stream of arguments for parameterized tests
     */
    private static Stream<Arguments> provideArgumentsForFindByMovieIdTest() {
        return Stream.of(
                Arguments.of("0", 0, new int[]{}),
                Arguments.of("1", 2, new int[]{0, 1}),
                Arguments.of("2", 1, new int[]{2})
        );
    }

    /**
     * Provides arguments for testing the testFindByDateIsInSeanceDatesRange method in {@link SeanceRepository}.
     * The arguments are:
     * <ul>
     *     <li>date - the date in which to search for seances</li>
     *     <li>expectedSize - the expected number of seances with a date within the specified date</li>
     *     <li>expectedSeanceIndexes - the indexes of expected seances in the test data</li>
     * </ul>
     *
     * @return a stream of arguments for parameterized tests
     */
    private static Stream<Arguments> provideArgumentsForFindByDateIsInSeanceDatesRangeTest() {
        return Stream.of(
                Arguments.of(LocalDate.of(2, 2, 2), 1, new int[]{0}),
                Arguments.of(LocalDate.of(3, 3, 3), 2, new int[]{0, 1}),
                Arguments.of(LocalDate.of(8, 8, 8), 2, new int[]{0, 3}),
                Arguments.of(LocalDate.of(11, 11, 11), 2, new int[]{2, 3}),
                Arguments.of(LocalDate.of(12, 1, 1), 1, new int[]{3}),
                Arguments.of(LocalDate.of(50, 1, 1), 0, new int[]{})
        );
    }

    /**
     * Provides arguments for testing the testDeleteByDateToBefore method in {@link SeanceRepository}.
     * The arguments are:
     * <ul>
     *     <li>date - the date to delete seances with dateTo before</li>
     *     <li>expectedRemovedSize - the expected number of removed seances</li>
     *     <li>expectedRemovedSeanceIndexes - the indexes of expected removed seances in the test data</li>
     * </ul>
     *
     * @return a stream of arguments for parameterized tests
     */
    private static Stream<Arguments> provideArgumentsForDeleteByDateToBeforeTest() {
        return Stream.of(
                Arguments.of(LocalDate.of(11, 1, 1), 3, new int[]{0, 1}),
                Arguments.of(LocalDate.of(12, 1, 1), 4, new int[]{0, 1, 2})
        );
    }

}