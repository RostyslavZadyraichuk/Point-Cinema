package com.zadyraichuk.point_cinema.repository;

import com.zadyraichuk.point_cinema.entity.Hall;
import com.zadyraichuk.point_cinema.entity.Technology;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

//TODO make fail messages formatted for better clarity
@DisplayName("Hall repository tests")
@DataMongoTest
@ActiveProfiles("test")
class HallRepositoryTest {

    @Autowired
    private HallRepository hallRepository;

    private static Hall hall;

    @BeforeEach
    void setUp() {
        hall = initHall();
        hall = hallRepository.save(hall);
    }

    @AfterEach
    void afterEach() {
        hallRepository.deleteAll();
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForCreateAndUpdateTests")
    @DisplayName("Test creating an Hall")
    void testCreate(int number,
                    int rows,
                    int columns,
                    Technology technology,
                    String cinemaId,
                    boolean shouldThrowException) {
        Hall hallLocal = new Hall(null, number, rows, columns, technology, cinemaId);
        assertNull(hallLocal.getId(), "The hall has just created should have no ID");

        if (shouldThrowException) {
            assertThrows(DuplicateKeyException.class, () -> hallRepository.save(hallLocal),
                    "Should have thrown an exception due to duplicate first and last name");
        } else {
            Hall savedHall = assertDoesNotThrow(() -> hallRepository.save(hallLocal), "The hall should be saved");
            assertNotNull(savedHall.getId(), "The saved hall should have a generated ID");
            assertEquals(hallLocal.getNumber(), savedHall.getNumber(), "The number should match");
            assertEquals(hallLocal.getRows(), savedHall.getRows(), "The rows should match");
            assertEquals(hallLocal.getColumns(), savedHall.getColumns(), "The columns should match");
            assertEquals(hallLocal.getTechnology(), savedHall.getTechnology(), "The technology should match");
            assertEquals(hallLocal.getCinemaId(), savedHall.getCinemaId(), "The cinema ID should match");
        }
    }

    @Test
    @DisplayName("Test finding an Hall by ID")
    void testFindById() {
        Optional<Hall> foundHallOpt = hallRepository.findById(hall.getId());
        assertTrue(foundHallOpt.isPresent(), "The hall should be found by ID");

        Hall foundHall = foundHallOpt.get();
        assertEquals(hall.getId(), foundHall.getId(), "The IDs should match");
        assertEquals(hall.getNumber(), foundHall.getNumber(), "The number should match");
        assertEquals(hall.getRows(), foundHall.getRows(), "The rows should match");
        assertEquals(hall.getColumns(), foundHall.getColumns(), "The columns should match");
        assertEquals(hall.getTechnology(), foundHall.getTechnology(), "The technology should match");
        assertEquals(hall.getCinemaId(), foundHall.getCinemaId(), "The cinema ID should match");
    }

    @Test
    @DisplayName("Test finding all Halls")
    void testFindAll() {
        Hall[] halls = getHallsForGeneralCrudTests();
        hallRepository.saveAll(Arrays.asList(halls));
        int expectedLength = halls.length + 1;
        List<Hall> foundHalls = hallRepository.findAll();

        assertNotNull(foundHalls, "The found list should not be null");
        assertFalse(foundHalls.isEmpty(), "The found list should not be empty");
        assertEquals(expectedLength, foundHalls.size(), "The size of the list should match the number of halls saved");
        assertTrue(foundHalls.containsAll(Arrays.asList(halls)), "The found halls should contain all halls saved before");
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForCreateAndUpdateTests")
    @DisplayName("Test updating an Hall")
    void testUpdate(int number,
                    int rows,
                    int columns,
                    Technology technology,
                    String cinemaId,
                    boolean shouldThrowException) {
        Hall hallBeforeUpdate = new Hall(null, 1, 1, 1, Technology.TECHNOLOGY_3D, "BeforeUpdate");
        hallBeforeUpdate = hallRepository.save(hallBeforeUpdate);
        Hall updatedHall = new Hall(hallBeforeUpdate.getId(), number, rows, columns, technology, cinemaId);

        if (shouldThrowException) {
            assertThrows(DuplicateKeyException.class, () -> hallRepository.save(updatedHall),
                    "Should have thrown an exception due to duplicate first and last name");
        } else {
            hall = assertDoesNotThrow(() -> hallRepository.save(updatedHall), "The hall should be updated");
            assertEquals(updatedHall.getId(), hall.getId(), "The ID should remain the same after update");
            assertEquals(updatedHall.getNumber(), hall.getNumber(), "The number should match");
            assertEquals(updatedHall.getRows(), hall.getRows(), "The rows should match");
            assertEquals(updatedHall.getColumns(), hall.getColumns(), "The columns should match");
            assertEquals(updatedHall.getTechnology(), hall.getTechnology(), "The technology should match");
            assertEquals(updatedHall.getCinemaId(), hall.getCinemaId(), "The cinema ID should match");
        }
    }

    @Test
    @DisplayName("Test save all Halls")
    void testSaveAll() {
        Hall[] halls = getHallsForGeneralCrudTests();

        List<Hall> savedHalls = hallRepository.saveAll(Arrays.asList(halls));

        assertNotNull(savedHalls, "The saved halls list should not be null");
        assertEquals(halls.length, savedHalls.size(), "The size of the saved halls should match the input list size");
        Stream<String> hallIds = savedHalls.stream().map(Hall::getId);
        assertTrue(hallIds.allMatch(Objects::nonNull), "Each saved hall should have a generated ID");

        List<Hall> foundHalls = hallRepository.findAll();
        int expectedSize = halls.length + 1;
        assertEquals(expectedSize, foundHalls.size(), "The number of halls found should match the saved halls");
        assertTrue(foundHalls.containsAll(savedHalls), "The found halls should match the saved halls");
    }

    @Test
    @DisplayName("Test deleting an Hall")
    void testDelete() {
        hallRepository.delete(hall);

        Optional<Hall> deletedHallOpt = hallRepository.findById(hall.getId());
        assertFalse(deletedHallOpt.isPresent(), "The hall should be deleted and not found by ID");
    }

    @Test
    @DisplayName("Test deleting an Hall by ID")
    void testDeleteById() {
        hallRepository.deleteById(hall.getId());

        Optional<Hall> deletedHallOpt = hallRepository.findById(hall.getId());
        assertFalse(deletedHallOpt.isPresent(), "The hall should be deleted and not found by ID");
    }

    @Test
    @DisplayName("Test deleting all Halls")
    void testDeleteAll() {
        Hall[] halls = getHallsForGeneralCrudTests();
        hallRepository.saveAll(Arrays.asList(halls));

        List<Hall> foundHalls = hallRepository.findAll();
        assertNotNull(foundHalls, "The halls should exist before deleting");
        assertFalse(foundHalls.isEmpty(), "The halls should exist before deleting");

        hallRepository.deleteAll();
        foundHalls = hallRepository.findAll();
        assertNotNull(foundHalls, "The halls should be deleted and halls list should not be null");
        assertTrue(foundHalls.isEmpty(), "The halls should be deleted and halls list should be empty");
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForFindByCinemaIdTest")
    @DisplayName("Test finding all Halls by cinema ID")
    void testFindByCinemaId(String cinemaId,
                            int expectedSize,
                            int[] expectedHallIndexes) {
        Hall[] halls = getHallsForSpecializedTests();
        hallRepository.saveAll(Arrays.asList(halls));

        List<Hall> foundHalls = hallRepository.findByCinemaId(cinemaId);

        assertNotNull(foundHalls, "The found halls list should not be null");
        assertEquals(expectedSize, foundHalls.size(), "The size of the found halls should match the saved halls");
        for (int i : expectedHallIndexes) {
            assertTrue(foundHalls.contains(halls[i]), "The found halls should contain all saved halls");
        }
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForFindByCinemaIdAndTechnologyTest")
    @DisplayName("Test finding all Halls by cinema ID and technology")
    void testFindByCinemaIdAndTechnology(String cinemaId,
                                         Technology technology,
                                         int expectedSize,
                                         int[] expectedHallIndexes) {
        Hall[] halls = getHallsForSpecializedTests();
        hallRepository.saveAll(Arrays.asList(halls));

        List<Hall> foundHalls = hallRepository.findByCinemaIdAndTechnology(cinemaId, technology);

        assertNotNull(foundHalls, "The found halls list should not be null");
        assertEquals(expectedSize, foundHalls.size(), "The size of the found halls should match the saved halls");
        for (int i : expectedHallIndexes) {
            assertTrue(foundHalls.contains(halls[i]), "The found halls should contain all saved halls");
        }
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForFindByCinemaIdAndTechnologyInTest")
    @DisplayName("Test finding all Halls by cinema ID and technology in")
    void testFindByCinemaIdAndTechnologyIn(String cinemaId,
                                           List<Technology> technologies,
                                           int expectedSize,
                                           int[] expectedHallIndexes) {
        Hall[] halls = getHallsForSpecializedTests();
        hallRepository.saveAll(Arrays.asList(halls));

        List<Hall> foundHalls = hallRepository.findByCinemaIdAndTechnologyIn(cinemaId, technologies);

        assertNotNull(foundHalls, "The found halls list should not be null");
        assertEquals(expectedSize, foundHalls.size(), "The size of the found halls should match the saved halls");
        for (int i : expectedHallIndexes) {
            assertTrue(foundHalls.contains(halls[i]), "The found halls should contain all saved halls");
        }
    }

    private Hall initHall() {
        return Hall.builder()
                .number(1)
                .rows(1)
                .columns(1)
                .technology(Technology.TECHNOLOGY_3D)
                .cinemaId("test")
                .build();
    }

    private Hall[] getHallsForGeneralCrudTests() {
        Hall unique1 = new Hall(null, 1, 1, 1, Technology.TECHNOLOGY_2D, "1");
        Hall unique2 = new Hall(null, 2, 2, 2, Technology.TECHNOLOGY_3D, "2");
        Hall unique3 = new Hall(null, 3, 3, 3, Technology.TECHNOLOGY_4D, "3");

        return new Hall[]{unique1, unique2, unique3};
    }

    private Hall[] getHallsForSpecializedTests() {
        Hall hall1 = new Hall(null, 1, 1, 1, Technology.TECHNOLOGY_2D, "1");
        Hall hall2 = new Hall(null, 2, 1, 1, Technology.TECHNOLOGY_3D, "1");
        Hall hall3 = new Hall(null, 1, 1, 1, Technology.TECHNOLOGY_3D, "2");
        Hall hall4 = new Hall(null, 1, 1, 1, Technology.TECHNOLOGY_3D, "3");

        return new Hall[]{hall1, hall2, hall3, hall4};
    }

    /**
     * Provides arguments for testing create and update methods in {@link HallRepository}.
     * The arguments are:
     * <ul>
     *     <li>number - the number of the hall</li>
     *     <li>rows - the number of rows in the hall</li>
     *     <li>columns - the number of columns in the hall</li>
     *     <li>technology - the type of technology used in the hall</li>
     *     <li>cinemaId - the ID of the cinema that the hall belongs to</li>
     *     <li>shouldThrowException - whether an exception should be thrown due to duplicate name and surname</li>
     * </ul>
     */
    private static Stream<Arguments> provideArgumentsForCreateAndUpdateTests() {
        return Stream.of(
                Arguments.of(1, 1, 1, Technology.TECHNOLOGY_3D, "Unique", false),
                Arguments.of(hall.getNumber(), 1, 1, Technology.TECHNOLOGY_3D, "Unique", false),
                Arguments.of(100, 2, 2, Technology.TECHNOLOGY_3D, hall.getCinemaId(), false),
                Arguments.of(hall.getNumber(), 1, 1, Technology.TECHNOLOGY_3D, hall.getCinemaId(), true)
        );
    }

    /**
     * Provides arguments for testing the testFindByCinemaId method in {@link HallRepository}.
     * The arguments are:
     * <ul>
     *     <li>cinemaId - the ID of the cinema whose halls are to be retrieved</li>
     *     <li>expectedSize - the expected number of halls matching the cinema ID</li>
     *     <li>expectedHallIndexes - the indexes of expected halls in the test data</li>
     * </ul>
     *
     * @return a stream of arguments for parameterized tests
     */
    private static Stream<Arguments> provideArgumentsForFindByCinemaIdTest() {
        return Stream.of(
                Arguments.of("1", 2, new int[]{0, 1}),
                Arguments.of("2", 1, new int[]{2}),
                Arguments.of("3", 1, new int[]{3}),
                Arguments.of("4", 0, new int[]{})
        );
    }

    /**
     * Provides arguments for testing the testFindByCinemaIdAndTechnology method in {@link HallRepository}.
     * The arguments are:
     * <ul>
     *     <li>cinemaId - the ID of the cinema whose halls are to be retrieved</li>
     *     <li>technology - the type of technology to search for</li>
     *     <li>expectedSize - the expected number of halls matching the cinema ID and technology</li>
     *     <li>expectedHallIndexes - the indexes of expected halls in the test data</li>
     * </ul>
     *
     * @return a stream of arguments for parameterized tests
     */
    private static Stream<Arguments> provideArgumentsForFindByCinemaIdAndTechnologyTest() {
        return Stream.of(
                Arguments.of("1", Technology.TECHNOLOGY_2D, 1, new int[]{0}),
                Arguments.of("1", Technology.TECHNOLOGY_3D, 1, new int[]{1}),
                Arguments.of("2", Technology.TECHNOLOGY_2D, 0, new int[]{}),
                Arguments.of("2", Technology.TECHNOLOGY_3D, 1, new int[]{2})
        );
    }

    /**
     * Provides arguments for testing the testFindByCinemaIdAndTechnologyIn method in {@link HallRepository}.
     * The arguments are:
     * <ul>
     *     <li>cinemaId - the ID of the cinema whose halls are to be retrieved</li>
     *     <li>technologies - the list of technologies to search for</li>
     *     <li>expectedSize - the expected number of halls matching the cinema ID and technology in</li>
     *     <li>expectedHallIndexes - the indexes of expected halls in the test data</li>
     * </ul>
     *
     * @return a stream of arguments for parameterized tests
     */
    private static Stream<Arguments> provideArgumentsForFindByCinemaIdAndTechnologyInTest() {
        List<Technology> technologies3D = List.of(Technology.TECHNOLOGY_3D);
        List<Technology> technologies2D3D = List.of(Technology.TECHNOLOGY_2D, Technology.TECHNOLOGY_3D);
        List<Technology> technologies4D = List.of(Technology.TECHNOLOGY_4D);
        List<Technology> technologies2D3D4D = List.of(Technology.TECHNOLOGY_2D, Technology.TECHNOLOGY_3D, Technology.TECHNOLOGY_4D);

        return Stream.of(
                Arguments.of("1", technologies3D, 1, new int[]{1}),
                Arguments.of("1", technologies2D3D, 2, new int[]{0, 1}),
                Arguments.of("1", technologies4D, 0, new int[]{}),
                Arguments.of("1", technologies2D3D4D, 2, new int[]{0, 1})
        );
    }

}