package com.zadyraichuk.point_cinema.entity;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("Message entity class test")
class MessageTest {

    private static String id;
    private static String text;

    private Message message;

    @BeforeAll
    static void beforeAll() {
        MessageTest.id = "1";
        MessageTest.text = "test";
    }

    @BeforeEach
    void setUp() {
        message = initMessage();
        message.setId(MessageTest.id);
    }

    @Test
    @DisplayName("Test required-args constructor initializes fields correctly")
    void testRequiredArgsConstructor() {
        String textLocal = "100";

        message = new Message(textLocal);

        assertNull(message.getId(), "Id should be null when using the required-args constructor");
        assertEquals(textLocal, message.getText(), "Text does not match the expected value");
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "10", "100"})
    @DisplayName("Test setId method with multiple values")
    void testSetId(String id) {
        message.setId(id);

        assertEquals(id, message.getId(), "setId method failed to set the expected Id");
    }

    @Test
    @DisplayName("Test getId returns null for newly created Message")
    void testGetId_whenNewCreated() {
        message = initMessage();

        assertNull(message.getId(), "Newly created Actor should have null Id");
    }

    @Test
    @DisplayName("Test getter methods for all fields")
    void testGetterMethods() {
        assertEquals(MessageTest.id, message.getId(), "Getter for id returned an unexpected value");
        assertEquals(MessageTest.text, message.getText(), "Getter for text returned an unexpected value");
    }

    private Message initMessage() {
        return new Message(MessageTest.text);
    }

}