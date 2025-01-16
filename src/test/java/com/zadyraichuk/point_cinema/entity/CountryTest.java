package com.zadyraichuk.point_cinema.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Country Enum Tests")
class CountryTest {

    @Test
    @DisplayName("Test Enum Values")
    void testValues() {
        Country[] expected = getArrayOfEnums();
        Country[] actual = Country.values();

        assertNotNull(actual, "Enum cannot be null");
        assertNotEquals(0, actual.length, "Enum length should not be zero");
        assertEquals(expected.length, actual.length,
                String.format("Enum should have %d constants", expected.length));
        assertArrayEquals(expected, actual,"Enum constants do not match expected values");
    }

    @Test
    void testValueOf_whenNull() {
        assertThrows(NullPointerException.class,
                () -> Country.valueOf(null),
                "Enum should not be returned from null value");
    }

    @ParameterizedTest
    @ValueSource(strings = {"UKRAINE", "POLAND"})
    void testValueOf_whenValidValue(String value) {
        assertDoesNotThrow(() -> Country.valueOf(value), "Enum should be returned from valid value");
    }

    @ParameterizedTest
    @ValueSource(strings = {"ukraine", "", "Poland", "123"})
    void testValueOf_whenInvalidValue(String value) {
        assertThrows(IllegalArgumentException.class,
                () -> Country.valueOf(value),
                "Enum should not be returned from invalid value");
    }

    private Country[] getArrayOfEnums() {
        return new Country[]{
                Country.UNITED_KINGDOM,
                Country.UKRAINE,
                Country.POLAND
        };
    }
}