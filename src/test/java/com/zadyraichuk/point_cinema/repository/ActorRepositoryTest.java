package com.zadyraichuk.point_cinema.repository;

import com.zadyraichuk.point_cinema.entity.Actor;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Actor repository tests")
@DataMongoTest
@ActiveProfiles("test")
class ActorRepositoryTest {

    @Autowired
    private ActorRepository actorRepository;

    private Actor actor;

    @BeforeEach
    void setUp() {
        actor = initActor();
        actor = actorRepository.save(actor);
    }

    @AfterEach
    void afterEach() {
        actorRepository.deleteAll();
    }

    @Test
    @DisplayName("Test creating an Actor")
    void testCreate() {
        Actor actorLocal = new Actor("Created", "Created", "Created");
        assertNull(actorLocal.getId(), "The actor has just created should have no ID");

        Actor savedActor = actorRepository.save(actorLocal);
        assertNotNull(savedActor.getId(), "The saved actor should have a generated ID");
        assertEquals(actorLocal.getFirstName(), savedActor.getFirstName(), "The first name should match");
        assertEquals(actorLocal.getLastName(), savedActor.getLastName(), "The last name should match");
        assertEquals(actorLocal.getPictureId(), savedActor.getPictureId(), "The picture ID should match");
    }

    @Test
    @DisplayName("Test unique combination of First Name and Last Name whilst creation")
    void testCreate_whenUniqueFirstNameAndLastName() {
        Actor[] actors = getActorsForGeneralCrudTests(false);

        for (Actor a : actors) {
            assertDoesNotThrow(() -> actorRepository.save(a));
        }
    }

    @Test
    @DisplayName("Test duplicate combination of First Name and Last Name whilst creation")
    void testCreate_whenDuplicateFirstNameAndLastName() {
        Actor duplicate = initActor();

        assertThrows(DuplicateKeyException.class, () -> actorRepository.save(duplicate),
                "Should have thrown an exception due to duplicate first and last name");
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
        Actor[] actors = getActorsForGeneralCrudTests(false);
        actorRepository.saveAll(Arrays.asList(actors));
        int expectedLength = actors.length + 1;
        List<Actor> foundActors = actorRepository.findAll();

        assertNotNull(foundActors, "The found list should not be null");
        assertFalse(foundActors.isEmpty(), "The found list should not be empty");
        assertEquals(expectedLength, foundActors.size(), "There should be 3 actors in the collection.");

        for (Actor a : actors) {
            assertTrue(foundActors.contains(a), "The found actors should contain all actors saved before");
        }
    }

    @Test
    @DisplayName("Test updating an Actor")
    void testUpdate() {
        Actor updatedActor = new Actor("UpdatedFirstName", "UpdatedLastName", "UpdatedPictureId");
        updatedActor.setId(actor.getId());
        actor = actorRepository.save(updatedActor);

        assertEquals(updatedActor.getId(), actor.getId(), "The ID should remain the same after update");
        assertEquals(updatedActor.getFirstName(), actor.getFirstName(), "The first name should be updated");
        assertEquals(updatedActor.getLastName(), actor.getLastName(), "The last name should be updated");
        assertEquals(updatedActor.getPictureId(), actor.getPictureId(), "The picture ID should be updated");
    }

    @Test
    @DisplayName("Test reset updated Actor with previous First Name and Last Name")
    void testUpdate_whenChangeBack() {
        Actor newValuesActor = new Actor("New", "New", "New");
        newValuesActor.setId(actor.getId());
        assertDoesNotThrow(() -> actorRepository.save(newValuesActor));

        Actor oldValuesActor = initActor();
        oldValuesActor.setId(actor.getId());
        assertDoesNotThrow(() -> actorRepository.save(oldValuesActor));

        Optional<Actor> foundActorOpt = actorRepository.findById(actor.getId());
        assertTrue(foundActorOpt.isPresent(), "The actor should be found by ID");
        Actor foundActor = foundActorOpt.get();

        assertEquals(oldValuesActor.getFirstName(), foundActor.getFirstName(), "The first name should be updated back");
        assertNotEquals(newValuesActor.getFirstName(), foundActor.getFirstName(), "The first name should not be the same as updated first time");
        assertEquals(actor.getFirstName(), foundActor.getFirstName(), "The first name should be the same as initial value");

        assertEquals(oldValuesActor.getLastName(), foundActor.getLastName(), "The last name should be updated back");
        assertNotEquals(newValuesActor.getLastName(), foundActor.getLastName(), "The last name should not be the same as updated first time");
        assertEquals(actor.getLastName(), foundActor.getLastName(), "The last name should be the same as initial value");
    }

    @Test
    @DisplayName("Test unique combination of First Name and Last Name whilst updating")
    void testUpdate_whenUniqueFirstNameAndLastName() {
        Actor[] actors = getActorsForGeneralCrudTests(true);

        for (Actor a : actors) {
            assertDoesNotThrow(() -> actorRepository.save(a), "Actor with unique combination of first and last name should be updated");
        }
    }

    @Test
    @DisplayName("Test duplicate combination of First Name and Last Name whilst updating")
    void testUpdate_whenDuplicateFirstNameAndLastName() {
        Actor uniqueActor = new Actor("Unique", "Unique", "Unique");
        uniqueActor = actorRepository.save(uniqueActor);

        assertNotNull(uniqueActor.getId(), "The new unique Actor ID should exist");
        assertNotEquals(uniqueActor.getId(), actor.getId(), "The new unique Actor and initial Actor IDs should not match");

        Actor updatedUnique = new Actor(actor.getFirstName(), actor.getLastName(), actor.getPictureId());
        updatedUnique.setId(uniqueActor.getId());

        assertThrows(DuplicateKeyException.class, () -> actorRepository.save(updatedUnique),
                "Actor with duplicate combination of first and last name should not be updated");
    }

    @Test
    @DisplayName("Test save all Actors")
    void testSaveAll() {
        Actor[] actors = getActorsForGeneralCrudTests(false);

        List<Actor> savedActors = actorRepository.saveAll(Arrays.asList(actors));

        assertNotNull(savedActors, "The saved actors list should not be null");
        assertEquals(actors.length, savedActors.size(), "The size of the saved actors should match the input list size");
        for (Actor a : savedActors) {
            assertNotNull(a.getId(), "Each saved actor should have a generated ID");
        }

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
    @DisplayName("Test deleting all Actors")
    void testDeleteAll() {
        Actor[] actors = getActorsForGeneralCrudTests(false);
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
    @CsvSource(delimiter = '|', textBlock = """
                    One | 2 | 0,1
                    one | 2 | 0,1
                    Two | 2 | 0,2
                    ''  | 4 | 0,1,2
                    123 | 0 | ''
            """)
    @DisplayName("Test finding an Actor by First Name or Last Name")
    void testFindByFirstNameOrLastName(String firstOrLastNamePattern,
                                       int expectedSize,
                                       String expectedActorIndexesArray) {
        Actor[] actors = getActorsForSpecializedFindTests();
        actorRepository.saveAll(Arrays.asList(actors));

        List<Actor> foundActors = actorRepository.findByFirstNameOrLastName(firstOrLastNamePattern);
        int[] expectedActorIndexes = expectedActorIndexesArray.isEmpty() ? new int[0] :
                Arrays.stream(expectedActorIndexesArray.split(","))
                        .mapToInt(Integer::parseInt)
                        .toArray();

        assertNotNull(foundActors, "The result should not be null");
        assertEquals(expectedSize, foundActors.size(),
                String.format("The result should contain %d actors with firstOrLastName pattern '%s'", expectedSize, firstOrLastNamePattern));
        for (int index : expectedActorIndexes) {
            assertTrue(foundActors.contains(actors[index]), "Actor should be in the result");
        }
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', textBlock = """
                Three | 123  | 0 | ''
                123   | Four | 0 | ''
                One   | Two  | 1 | 0
                one   | two  | 1 | 0
                o     | t    | 1 | 0
                O     | T    | 1 | 0
                123   | 123  | 0 | ''
                ''    | ''   | 4 | 0,1,2
            """)
    @DisplayName("Test finding an Actor by First Name and Last Name")
    void testFindByFirstNameAndLastName(String firstNamePattern,
                                        String lastNamePattern,
                                        int expectedSize,
                                        String expectedActorIndexesArray) {
        Actor[] actors = getActorsForSpecializedFindTests();
        actorRepository.saveAll(Arrays.asList(actors));

        List<Actor> foundActors = actorRepository.findByFirstNameAndLastName(firstNamePattern, lastNamePattern);
        int[] expectedActorIndexes = expectedActorIndexesArray.isEmpty() ? new int[0] :
                Arrays.stream(expectedActorIndexesArray.split(","))
                        .mapToInt(Integer::parseInt)
                        .toArray();

        assertNotNull(foundActors, "The result should not be null");
        assertEquals(expectedSize, foundActors.size(),
                String.format("The result should contain %d actors with firstOrLastName pattern '%s %s'", expectedSize, firstNamePattern, lastNamePattern));
        for (int index : expectedActorIndexes) {
            assertTrue(foundActors.contains(actors[index]), "Actor should be in the result");
        }
    }

    private Actor initActor() {
        return new Actor("John", "Doe", "picId");
    }

    private Actor[] getActorsForGeneralCrudTests(boolean setIdFromOriginal) {
        Actor unique1 = new Actor(actor.getFirstName(), "Unique", "Unique");
        Actor unique2 = new Actor("Unique", actor.getLastName(), "Unique");
        Actor unique3 = new Actor("Unique", "Unique", "Unique");

        if (setIdFromOriginal) {
            unique1.setId(actor.getId());
            unique2.setId(actor.getId());
            unique3.setId(actor.getId());
        }

        return new Actor[]{unique1, unique2, unique3};
    }

    private Actor[] getActorsForSpecializedFindTests() {
        Actor actor1 = new Actor("One", "Two", "pic1");
        Actor actor2 = new Actor("Three", "ThreeOne", "pic2");
        Actor actor3 = new Actor("Two", "Four", "pic3");

        return new Actor[]{actor1, actor2, actor3};
    }

}