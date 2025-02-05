package com.zadyraichuk.point_cinema.repository;

import com.zadyraichuk.point_cinema.entity.Actor;
import org.junit.jupiter.api.*;
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

@DisplayName("Actor repository tests")
@DataMongoTest
@ActiveProfiles("test")
class ActorRepositoryTest {

    @Autowired
    private ActorRepository actorRepository;

    private static Actor actor;

    @BeforeEach
    void setUp() {
        actor = initActor();
        actor = actorRepository.save(actor);
    }

    @AfterEach
    void afterEach() {
        actorRepository.deleteAll();
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForCreateAndUpdateTests")
    @DisplayName("Test creating an Actor")
    void testCreate(String firstName, String lastName, boolean shouldThrowException) {
        Actor actorLocal = new Actor(firstName, lastName, "Created");
        assertNull(actorLocal.getId(), "The actor has just created should have no ID");

        if (shouldThrowException) {
            assertThrows(DuplicateKeyException.class, () -> actorRepository.save(actorLocal),
                    "Should have thrown an exception due to duplicate first and last name");
        } else {
            Actor savedActor = assertDoesNotThrow(() -> actorRepository.save(actorLocal));
            assertNotNull(savedActor.getId(), "The saved actor should have a generated ID");
            assertEquals(actorLocal.getFirstName(), savedActor.getFirstName(), "The first name should match");
            assertEquals(actorLocal.getLastName(), savedActor.getLastName(), "The last name should match");
            assertEquals(actorLocal.getPictureId(), savedActor.getPictureId(), "The picture ID should match");
        }

    }

    @Test
    @DisplayName("Test finding an Actor by ID")
    void testFindById() {
        Optional<Actor> foundActorOpt = actorRepository.findById(actor.getId());
        assertTrue(foundActorOpt.isPresent(), "The actor should be found by ID");

        Actor foundActor = foundActorOpt.get();
        assertEquals(actor.getId(), foundActor.getId(), "The IDs should match");
        assertEquals(actor.getFirstName(), foundActor.getFirstName(), "The first name should match");
        assertEquals(actor.getLastName(), foundActor.getLastName(), "The last name should match");
        assertEquals(actor.getPictureId(), foundActor.getPictureId(), "The picture ID should match");
    }

    @Test
    @DisplayName("Test finding all Actors")
    void testFindAll() {
        Actor[] actors = getActorsForGeneralCrudTests();
        actorRepository.saveAll(Arrays.asList(actors));
        int expectedLength = actors.length + 1;
        List<Actor> foundActors = actorRepository.findAll();

        assertNotNull(foundActors, "The found list should not be null");
        assertFalse(foundActors.isEmpty(), "The found list should not be empty");
        assertEquals(expectedLength, foundActors.size(), "The size of the list should match the number of actors saved");
        assertTrue(foundActors.containsAll(List.of(actors)), "The found list should contain all actors saved before");
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForCreateAndUpdateTests")
    @DisplayName("Test updating an Actor")
    void testUpdate(String firstName, String lastName, boolean shouldThrowException) {
        Actor actorBeforeUpdate = new Actor("BeforeUpdate", "BeforeUpdate", "BeforeUpdate");
        actorBeforeUpdate = actorRepository.save(actorBeforeUpdate);
        Actor updatedActor = new Actor(firstName, lastName, "Updated");
        updatedActor.setId(actorBeforeUpdate.getId());

        if (shouldThrowException) {
            assertThrows(DuplicateKeyException.class, () -> actorRepository.save(updatedActor),
                    "Should have thrown an exception due to duplicate first and last name");
        } else {
            actor = assertDoesNotThrow(() -> actorRepository.save(updatedActor));
            assertEquals(updatedActor.getId(), actor.getId(), "The ID should remain the same after update");
            assertEquals(updatedActor.getFirstName(), actor.getFirstName(), "The first name should be updated");
            assertEquals(updatedActor.getLastName(), actor.getLastName(), "The last name should be updated");
            assertEquals(updatedActor.getPictureId(), actor.getPictureId(), "The picture ID should be updated");
        }
    }

    @Test
    @DisplayName("Test save all Actors")
    void testSaveAll() {
        Actor[] actors = getActorsForGeneralCrudTests();

        List<Actor> savedActors = actorRepository.saveAll(Arrays.asList(actors));

        assertNotNull(savedActors, "The saved actors list should not be null");
        assertEquals(actors.length, savedActors.size(), "The size of the saved actors should match the input list size");
        Stream<String> actorIds = savedActors.stream().map(Actor::getId);
        assertTrue(actorIds.allMatch(Objects::nonNull), "Each saved actor should have a generated ID");

        List<Actor> foundActors = actorRepository.findAll();
        int expectedSize = actors.length + 1;
        assertEquals(expectedSize, foundActors.size(), "The number of actors found should match the saved actors");
        assertTrue(foundActors.containsAll(savedActors), "The found actors should match the saved actors");
    }

    @Test
    @DisplayName("Test deleting an Actor")
    void testDelete() {
        actorRepository.delete(actor);

        Optional<Actor> deletedActorOpt = actorRepository.findById(actor.getId());
        assertFalse(deletedActorOpt.isPresent(), "The actor should be deleted and not found by ID");
    }

    @Test
    @DisplayName("Test deleting an Actor by ID")
    void testDeleteById() {
        actorRepository.deleteById(actor.getId());

        Optional<Actor> deletedActorOpt = actorRepository.findById(actor.getId());
        assertFalse(deletedActorOpt.isPresent(), "The actor should be deleted and not found by ID");
    }

    @Test
    @DisplayName("Test deleting all Actors")
    void testDeleteAll() {
        Actor[] actors = getActorsForGeneralCrudTests();
        actorRepository.saveAll(Arrays.asList(actors));

        List<Actor> foundActors = actorRepository.findAll();
        assertNotNull(foundActors, "The actors should exist before deleting");
        assertFalse(foundActors.isEmpty(), "The actors should exist before deleting");

        actorRepository.deleteAll();
        foundActors = actorRepository.findAll();
        assertNotNull(foundActors, "The actors should be deleted and actors list should not be null");
        assertTrue(foundActors.isEmpty(), "The actors should be deleted and actors list should be empty");
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForFindByFirstNameOrLastNameTest")
    @DisplayName("Test finding an Actor by First Name or Last Name")
    void testFindByFirstNameOrLastName(String firstOrLastNamePattern,
                                       int expectedSize,
                                       int[] expectedActorIndexes) {
        Actor[] actors = getActorsForSpecializedTests();
        actorRepository.saveAll(Arrays.asList(actors));

        List<Actor> foundActors = actorRepository.findByFirstNameOrLastName(firstOrLastNamePattern);

        assertNotNull(foundActors, "The result should not be null");
        assertEquals(expectedSize, foundActors.size(),
                String.format("The result should contain %d actors with firstOrLastName pattern '%s'", expectedSize, firstOrLastNamePattern));
        for (int i : expectedActorIndexes) {
            assertTrue(foundActors.contains(actors[i]), "Actor should be in the result");
        }
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForFindByFirstNameAndLastNameTest")
    @DisplayName("Test finding an Actor by First Name and Last Name")
    void testFindByFirstNameAndLastName(String firstNamePattern,
                                        String lastNamePattern,
                                        int expectedSize,
                                        int[] expectedActorIndexes) {
        Actor[] actors = getActorsForSpecializedTests();
        actorRepository.saveAll(Arrays.asList(actors));

        List<Actor> foundActors = actorRepository.findByFirstNameAndLastName(firstNamePattern, lastNamePattern);

        assertNotNull(foundActors, "The result should not be null");
        assertEquals(expectedSize, foundActors.size(),
                String.format("The result should contain %d actors with firstOrLastName pattern '%s %s'", expectedSize, firstNamePattern, lastNamePattern));
        for (int i : expectedActorIndexes) {
            assertTrue(foundActors.contains(actors[i]), "Actor should be in the result");
        }
    }

    private Actor initActor() {
        return new Actor("John", "Doe", "picId");
    }

    private Actor[] getActorsForGeneralCrudTests() {
        Actor unique1 = new Actor(actor.getFirstName(), "Unique", "Unique");
        Actor unique2 = new Actor("Unique", actor.getLastName(), "Unique");
        Actor unique3 = new Actor("Unique", "Unique", "Unique");

        return new Actor[]{unique1, unique2, unique3};
    }

    private Actor[] getActorsForSpecializedTests() {
        Actor actor1 = new Actor("One", "Two", "pic1");
        Actor actor2 = new Actor("Three", "ThreeOne", "pic2");
        Actor actor3 = new Actor("Two", "Four", "pic3");

        return new Actor[]{actor1, actor2, actor3};
    }

    /**
     * Provides arguments for testing create and update methods in {@link ActorRepository}.
     * The arguments are:
     * <ul>
     *     <li>firstName - the first name of the actor</li>
     *     <li>lastName - the last name of the actor</li>
     *     <li>shouldThrowException - whether the save operation should throw a DuplicateKeyException</li>
     * </ul>
     */
    private static Stream<Arguments> provideArgumentsForCreateAndUpdateTests() {
        return Stream.of(
                Arguments.of("Unique", "Unique", false),
                Arguments.of(actor.getFirstName(), "Unique", false),
                Arguments.of("Unique", actor.getLastName(), false),
                Arguments.of(actor.getFirstName(), actor.getLastName(), true)
        );
    }


    /**
     * Provides arguments for testing find by first name or last name methods in {@link ActorRepository}.
     * The arguments are:
     * <ul>
     *     <li>firstNamePattern - the pattern to match against the actor's first name, ignoring case</li>
     *     <li>lastNamePattern - the pattern to match against the actor's last name, ignoring case</li>
     *     <li>expectedSize - the expected number of actors containing the actor with the specified first name and last name</li>
     *     <li>expectedActorIndexes - the indexes of expected actors in the test data</li>
     * </ul>
     */
    private static Stream<Arguments> provideArgumentsForFindByFirstNameOrLastNameTest() {
        return Stream.of(
                Arguments.of("One", 2, new int[]{0, 1}),
                Arguments.of("one", 2, new int[]{0, 1}),
                Arguments.of("Two", 2, new int[]{0, 2}),
                Arguments.of("", 4, new int[]{0, 1, 2}),
                Arguments.of("123", 0, new int[]{}));
    }

    /**
     * Provides arguments for testing find by first name and last name methods in {@link ActorRepository}.
     * The arguments are:
     * <ul>
     *     <li>firstNamePattern - the pattern to match against the actor's first name, ignoring case</li>
     *     <li>lastNamePattern - the pattern to match against the actor's last name, ignoring case</li>
     *     <li>expectedSize - the expected number of actors containing the actor with the specified first name and last name</li>
     *     <li>expectedActorIndexes - the indexes of expected actors in the test data</li>
     * </ul>
     *
     * @return a stream of arguments for parameterized tests
     */
    private static Stream<Arguments> provideArgumentsForFindByFirstNameAndLastNameTest() {
        return Stream.of(
                Arguments.of("Three", "123", 0, new int[]{}),
                Arguments.of("123", "Four", 0, new int[]{}),
                Arguments.of("One", "Two", 1, new int[]{0}),
                Arguments.of("one", "two", 1, new int[]{0}),
                Arguments.of("o", "t", 1, new int[]{0}),
                Arguments.of("O", "T", 1, new int[]{0}),
                Arguments.of("123", "123", 0, new int[]{}),
                Arguments.of("", "", 4, new int[]{0, 1, 2})
        );
    }

}