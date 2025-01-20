package com.zadyraichuk.point_cinema.repository;

import com.zadyraichuk.point_cinema.entity.Actor;
import org.junit.jupiter.api.*;
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
    void testCreate_whenUniqueFirstNameAndLastName () {
        Actor[] actors = getActorsForGeneralCrudTests(false);

        assertDoesNotThrow(() -> actorRepository.save(actors[0]));
        assertDoesNotThrow(() -> actorRepository.save(actors[1]));
        assertDoesNotThrow(() -> actorRepository.save(actors[2]));
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
        Optional<Actor> retrievedActorOpt = actorRepository.findById(actor.getId());
        assertTrue(retrievedActorOpt.isPresent(), "The actor should be found by ID");

        Actor retrievedActor = retrievedActorOpt.get();
        assertEquals(actor.getId(), retrievedActor.getId(), "The IDs should match");
        assertEquals(actor.getFirstName(), retrievedActor.getFirstName(), "The first name should match");
        assertEquals(actor.getLastName(), retrievedActor.getLastName(), "The last name should match");
        assertEquals(actor.getPictureId(), retrievedActor.getPictureId(), "The picture ID should match");
    }

    @Test
    @DisplayName("Test finding all Actors")
    void testFindAll() {
        Actor[] actors = getActorsForFindTests();
        actorRepository.saveAll(Arrays.asList(actors));
        int expectedLength = actors.length + 1;
        List<Actor> retrievedActors = actorRepository.findAll();

        assertNotNull(retrievedActors, "The retrieved list should not be null");
        assertFalse(retrievedActors.isEmpty(), "The retrieved list should not be empty");
        assertEquals(expectedLength, retrievedActors.size(), "There should be 3 actors in the collection.");

        List<String> retrievedActorIds = retrievedActors.stream()
                .map(Actor::getId)
                .toList();
        for (Actor a : actors) {
            assertTrue(retrievedActorIds.contains(a.getId()), "The retrieved actors should contain all actors saved before");
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
        Actor newValues = new Actor("New", "New", "New");
        newValues.setId(actor.getId());
        assertDoesNotThrow(() -> actorRepository.save(newValues));

        Actor oldValues = initActor();
        oldValues.setId(actor.getId());
        assertDoesNotThrow(() -> actorRepository.save(oldValues));

        Optional<Actor> foundActorOpt = actorRepository.findById(actor.getId());
        assertTrue(foundActorOpt.isPresent(), "The actor should be found by ID");
        Actor foundActor = foundActorOpt.get();

        assertEquals(oldValues.getFirstName(), foundActor.getFirstName(), "The first name should be updated back");
        assertNotEquals(newValues.getFirstName(), foundActor.getFirstName(), "The first name should not be the same as updated first time");
        assertEquals(actor.getFirstName(), foundActor.getFirstName(), "The first name should be the same as initial value");

        assertEquals(oldValues.getLastName(), foundActor.getLastName(), "The last name should be updated back");
        assertNotEquals(newValues.getLastName(), foundActor.getLastName(), "The last name should not be the same as updated first time");
        assertEquals(actor.getLastName(), foundActor.getLastName(), "The last name should be the same as initial value");
    }

    @Test
    @DisplayName("Test unique combination of First Name and Last Name whilst updating")
    void testUpdate_whenUniqueFirstNameAndLastName () {
        Actor[] actors = getActorsForGeneralCrudTests(true);

        assertDoesNotThrow(() -> actorRepository.save(actors[0]), "Actor with unique combination of first and last name should be updated");
        assertDoesNotThrow(() -> actorRepository.save(actors[1]), "Actor with unique combination of first and last name should be updated");
        assertDoesNotThrow(() -> actorRepository.save(actors[2]), "Actor with unique combination of first and last name should be updated");
    }

    @Test
    @DisplayName("Test duplicate combination of First Name and Last Name whilst updating")
    void testUpdate_whenDuplicateFirstNameAndLastName() {
        Actor unique = new Actor("Unique", "Unique", "Unique");
        unique = actorRepository.save(unique);

        assertNotNull(unique.getId(), "The new unique Actor ID should exist");
        assertNotEquals(unique.getId(), actor.getId(), "The new unique Actor and initial Actor IDs should not match");

        Actor updatedUnique = new Actor(actor.getFirstName(), actor.getLastName(), actor.getPictureId());
        updatedUnique.setId(unique.getId());

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

        List<Actor> retrievedActors = actorRepository.findAll();
        int expectedSize = actors.length + 1;
        assertEquals(expectedSize, retrievedActors.size(), "The number of actors retrieved should match the saved actors");
        assertTrue(retrievedActors.containsAll(savedActors), "The retrieved actors should match the saved actors");
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
        Actor[] actors = getActorsForFindTests();
        actorRepository.saveAll(Arrays.asList(actors));

        List<Actor> actual = actorRepository.findAll();
        assertNotNull(actual, "The actors should exist before deleting");
        assertFalse(actual.isEmpty(), "The actors should exist before deleting");

        actorRepository.deleteAll();
        actual = actorRepository.findAll();
        assertNotNull(actual, "The actors should be deleted and actors list should not be null");
        assertTrue(actual.isEmpty(), "The actors should be deleted and actors list should be empty");
    }

    @Test
    @DisplayName("Test finding an Actor by First Name or Last Name with valid pattern")
    void testFindByFirstNameRegexOrLastNameRegex_whenValidPattern() {
        Actor[] actors = getActorsForFindTests();
        actorRepository.saveAll(Arrays.asList(actors));

        String pattern = "One";
        List<Actor> actual = actorRepository.findByFirstNameRegexOrLastNameRegex(pattern, pattern);

        assertNotNull(actual, "The result should not be null");
        assertEquals(2, actual.size(), "The result should contain 2 actors with pattern '" + pattern + "'");
        assertTrue(actual.contains(actors[0]), "Actor should be in the result");
        assertTrue(actual.contains(actors[1]), "Actor should be in the result");

        pattern = "Two";
        actual = actorRepository.findByFirstNameRegexOrLastNameRegex(pattern, pattern);

        assertNotNull(actual, "The result should not be null");
        assertEquals(2, actual.size(), "The result should contain 2 actors with pattern '" + pattern + "'");
        assertTrue(actual.contains(actors[0]), "Actor should be in the result");
        assertTrue(actual.contains(actors[2]), "Actor should be in the result");
    }

    @Test
    @DisplayName("Test finding an Actor by First Name or Last Name with lower case")
    void testFindByFirstNameRegexOrLastNameRegex_whenInvalidLowerCaseName() {
        Actor[] actors = getActorsForFindTests();
        actorRepository.saveAll(Arrays.asList(actors));

        String pattern = "one";
        List<Actor> actual = actorRepository.findByFirstNameRegexOrLastNameRegex(pattern, pattern);

        assertNotNull(actual, "The result should not be null");
        assertTrue(actual.isEmpty(), "The result should not contain any actors with pattern '" + pattern + "'");
    }

    @Test
    @DisplayName("Test finding an Actor by First Name or Last Name with empty String pattern")
    void testFindByFirstNameRegexOrLastNameRegex_whenEmptyStringPattern() {
        Actor[] actors = getActorsForFindTests();
        actorRepository.saveAll(Arrays.asList(actors));
        int expectedSize = actors.length + 1;

        String pattern = "";
        List<Actor> actual = actorRepository.findByFirstNameRegexOrLastNameRegex(pattern, pattern);

        assertNotNull(actual, "The result should not be null");
        assertEquals(expectedSize, actual.size(), "The result should contain all actors with empty String pattern");
        for (Actor value : actors) {
            assertTrue(actual.contains(value), "Actor should be in the result");
        }
    }

    @Test
    @DisplayName("Test finding an Actor by First Name or Last Name with different parts")
    void testFindByFirstNameRegexOrLastNameRegex_whenValidDifferentParts() {
        Actor[] actors = getActorsForFindTests();
        actorRepository.saveAll(Arrays.asList(actors));

        String firstName = "Three";
        String lastName = "Four";
        List<Actor> actual = actorRepository.findByFirstNameRegexOrLastNameRegex(firstName, lastName);

        assertNotNull(actual, "The result should not be null");
        assertEquals(2, actual.size(), "The result should contain 2 actors with pattern '" + firstName +
                " " + lastName + "'");
        assertTrue(actual.contains(actors[1]), "Actor should be in the result");
        assertTrue(actual.contains(actors[2]), "Actor should be in the result");
    }

    @Test
    @DisplayName("Test finding an Actor by First Name or Last Name with invalid pattern")
    void testFindByFirstNameRegexOrLastNameRegex_whenInvalidPattern() {
        Actor[] actors = getActorsForFindTests();
        actorRepository.saveAll(Arrays.asList(actors));

        String pattern = "123";
        List<Actor> actual = actorRepository.findByFirstNameRegexOrLastNameRegex(pattern, pattern);

        assertNotNull(actual, "The result should not be null");
        assertTrue(actual.isEmpty(), "The result should not contain any actors with pattern '" + pattern + "'");
    }

    private Actor initActor() {
        return new Actor("John", "Doe", "picId");
    }

    private Actor[] getActorsForGeneralCrudTests(boolean setOriginalId) {
        Actor unique1 = new Actor(actor.getFirstName(), "Unique", "Unique");
        Actor unique2 = new Actor("Unique", actor.getLastName(), "Unique");
        Actor unique3 = new Actor("Unique", "Unique", "Unique");

        if (setOriginalId) {
            unique1.setId(actor.getId());
            unique2.setId(actor.getId());
            unique3.setId(actor.getId());
        }

        return new Actor[] {unique1, unique2, unique3};
    }

    private Actor[] getActorsForFindTests() {
        Actor actor1 = new Actor("One", "Two", "pic1");
        Actor actor2 = new Actor("Three", "ThreeOne", "pic2");
        Actor actor3 = new Actor("Two", "Four", "pic3");

        return new Actor[] {actor1, actor2, actor3};
    }

}