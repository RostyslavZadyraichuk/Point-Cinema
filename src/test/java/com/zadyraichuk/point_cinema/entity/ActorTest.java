package com.zadyraichuk.point_cinema.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Actor entity class test")
class ActorTest {
    
    private Actor actor;

    @BeforeEach
    void setUp() {
        actor = new Actor("John", "Doe", "1");
        actor.setId("1");
    }

    @Test
    @DisplayName("Test required-args constructor initializes fields correctly")
    void testRequiredArgsConstructor() {
        actor = new Actor("Jane", "Doe", "10");

        assertNull(actor.getId());
        assertEquals("Jane", actor.getFirstName());
        assertEquals("Doe", actor.getLastName());
        assertEquals("10", actor.getPictureId());
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "10", "100"})
    @DisplayName("Test setId method with multiple values")
    void testSetId(String id) {
        actor.setId(null);
        assertNull(actor.getId());

        actor.setId(id);
        assertEquals(id, actor.getId());
    }

    @Test
    @DisplayName("Test getId returns null for newly created Actor")
    void testGetId_whenNewCreated() {
        actor = new Actor("John", "Doe", "10");

        assertNull(actor.getId());
    }

    @Test
    @DisplayName("Test getter methods for all fields")
    void testGetterMethods() {
        assertEquals("1", actor.getId());
        assertEquals("John", actor.getFirstName());
        assertEquals("Doe", actor.getLastName());
        assertEquals("1", actor.getPictureId());
    }

}