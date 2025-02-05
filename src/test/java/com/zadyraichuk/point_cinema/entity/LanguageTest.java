package com.zadyraichuk.point_cinema.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Language enum tests")
class LanguageTest {

    @Test
    @DisplayName("Test enum values array is valid")
    void testValues() {
        Set<Language> expected = getSetOfExpectedLanguages();
        Set<Language> actual = Set.of(Language.values());

        assertNotNull(actual, "Enum cannot be null");
        assertNotEquals(0, actual.size(), "Enum length should not be zero");
        assertEquals(expected.size(), actual.size(),
                String.format("Enum should have %d constants", expected.size()));
        assertTrue(actual.containsAll(expected), "Enum constants do not match expected values");
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

    private Set<Language> getSetOfExpectedLanguages() {
        return Set.of(
                Language.EN,
                Language.UA,
                Language.PL
        );
    }

}