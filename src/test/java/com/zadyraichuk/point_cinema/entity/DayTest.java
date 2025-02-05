package com.zadyraichuk.point_cinema.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Day enum tests")
class DayTest {

    @Test
    @DisplayName("Test enum values array is valid")
    void testValues() {
        Set<Day> expected = getSetOfExpectedDays();
        Set<Day> actual = Set.of(Day.values());

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
                () -> Day.valueOf(null),
                "Enum should not be returned from null value");
    }

    @ParameterizedTest
    @ValueSource(strings = {"MONDAY", "FRIDAY", "SUNDAY"})
    @DisplayName("Test valueOf method with valid values")
    void testValueOf_whenValidValue(String value) {
        assertDoesNotThrow(() -> Day.valueOf(value), "Enum should be returned from valid value");
    }

    @ParameterizedTest
    @ValueSource(strings = {"saturday", "", "Wednesday", "123", "Monda"})
    @DisplayName("Test valueOf method with invalid values")
    void testValueOf_whenInvalidValue(String value) {
        assertThrows(IllegalArgumentException.class,
                () -> Day.valueOf(value),
                "Enum should not be returned from invalid value");
    }

    private Set<Day> getSetOfExpectedDays() {
        return Set.of(
                Day.MONDAY,
                Day.TUESDAY,
                Day.WEDNESDAY,
                Day.THURSDAY,
                Day.FRIDAY,
                Day.SATURDAY,
                Day.SUNDAY
        );
    }
}