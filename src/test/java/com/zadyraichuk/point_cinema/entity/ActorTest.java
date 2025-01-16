package com.zadyraichuk.point_cinema.entity;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Actor entity class test")
class ActorTest {

    private static String id;
    private static String firstName;
    private static String lastName;
    private static String pictureId;

    private Actor actor;

    @BeforeAll
    static void beforeAll() {
        ActorTest.id = "1";
        ActorTest.firstName = "John";
        ActorTest.lastName = "Doe";
        ActorTest.pictureId = "1";
    }

    @BeforeEach
    void setUp() {
        actor = initActor();
        actor.setId(ActorTest.id);
    }

    @Test
    @DisplayName("Test required-args constructor initializes fields correctly")
    void testRequiredArgsConstructor() {
        String firstNameLocal = "Jane";
        String lastNameLocal = "Doe";
        String pictureIdLocal = "100";

        actor = new Actor(firstNameLocal, lastNameLocal, pictureIdLocal);

        assertNull(actor.getId());
        assertEquals(firstNameLocal, actor.getFirstName());
        assertEquals(lastNameLocal, actor.getLastName());
        assertEquals(pictureIdLocal, actor.getPictureId());
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "10", "100"})
    @DisplayName("Test setId method with multiple values")
    void testSetId(String id) {
        actor.setId(id);

        assertEquals(id, actor.getId());
    }

    @Test
    @DisplayName("Test getId returns null for newly created Actor")
    void testGetId_whenNewCreated() {
        actor = initActor();

        assertNull(actor.getId());
    }

    @Test
    @DisplayName("Test getter methods for all fields")
    void testGetterMethods() {
        assertEquals(ActorTest.id, actor.getId());
        assertEquals(ActorTest.firstName, actor.getFirstName());
        assertEquals(ActorTest.lastName, actor.getLastName());
        assertEquals(ActorTest.pictureId, actor.getPictureId());
    }

    private Actor initActor() {
        return new Actor(
                ActorTest.firstName,
                ActorTest.lastName,
                ActorTest.pictureId
        );
    }

}