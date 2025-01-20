package com.zadyraichuk.point_cinema.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Lang enum tests")
class LangTest {

    @Test
    @DisplayName("Test enum values array is valid")
    void testValues() {
        Language[] expected = getArrayOfLanguages();
        Language[] actual = Language.values();

        assertNotNull(actual, "Enum cannot be null");
        assertNotEquals(0, actual.length, "Enum length should not be zero");
        assertEquals(expected.length, actual.length,
                String.format("Enum should have %d constants", expected.length));
        assertArrayEquals(expected, actual,"Enum constants do not match expected values");
    }

    @Test
    @DisplayName("Test valueOf method with null value")
    void testValueOf_whenNull() {
        assertThrows(NullPointerException.class,
                () -> Language.valueOf(null),
                "Enum should not be returned from null value");
    }

    @ParameterizedTest
    @ValueSource(strings = {"EN", "UA"})
    @DisplayName("Test valueOf method with valid values")
    void testValueOf_whenValidValue(String value) {
        assertDoesNotThrow(() -> Language.valueOf(value), "Enum should be returned from valid value");
    }

    @ParameterizedTest
    @ValueSource(strings = {"en", "", "Pol", "123", "ENG"})
    @DisplayName("Test valueOf method with invalid values")
    void testValueOf_whenInvalidValue(String value) {
        assertThrows(IllegalArgumentException.class,
                () -> Language.valueOf(value),
                "Enum should not be returned from invalid value");
    }

    private Language[] getArrayOfLanguages() {
        return new Language[]{
                Language.EN,
                Language.UA,
                Language.PL
        };
    }

}