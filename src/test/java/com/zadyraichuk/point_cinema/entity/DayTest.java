package com.zadyraichuk.point_cinema.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Day Enum Tests")
class DayTest {

    @Test
    @DisplayName("Test Enum Values")
    void testValues() {
        Day[] expected = getArrayOfDays();
        Day[] actual = Day.values();

        assertNotNull(actual, "Enum cannot be null");
        assertNotEquals(0, actual.length, "Enum length should not be zero");
        assertEquals(expected.length, actual.length,
                String.format("Enum should have %d constants", expected.length));
        assertArrayEquals(expected, actual,"Enum constants do not match expected values");
    }

    @Test
    void testValueOf_whenNull() {
        assertThrows(NullPointerException.class,
                () -> Day.valueOf(null),
                "Enum should not be returned from null value");
    }

    @ParameterizedTest
    @ValueSource(strings = {"MONDAY", "FRIDAY", "SUNDAY"})
    void testValueOf_whenValidValue(String value) {
        assertDoesNotThrow(() -> Day.valueOf(value), "Enum should be returned from valid value");
    }

    @ParameterizedTest
    @ValueSource(strings = {"saturday", "", "Wednesday", "123", "Monda"})
    void testValueOf_whenInvalidValue(String value) {
        assertThrows(IllegalArgumentException.class,
                () -> Day.valueOf(value),
                "Enum should not be returned from invalid value");
    }

    private Day[] getArrayOfDays() {
        return new Day[]{
                Day.MONDAY,
                Day.TUESDAY,
                Day.WEDNESDAY,
                Day.THURSDAY,
                Day.FRIDAY,
                Day.SATURDAY,
                Day.SUNDAY
        };
    }
}