package com.zadyraichuk.point_cinema.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("MPAA rating enum tests")
class MPAATest {

    @Test
    @DisplayName("Test enum values array is valid")
    void testValues() {
        MPAA[] expected = getArrayOfRatings();
        MPAA[] actual = MPAA.values();

        assertNotNull(actual, "Enum cannot be null");
        assertNotEquals(0, actual.length, "Enum length should not be zero");
        assertEquals(expected.length, actual.length,
                String.format("Enum should have %d constants", expected.length));
        //todo change all enum's assertArrayEquals to assertEqualLength and assertContainsAllElements
        assertArrayEquals(expected, actual,"Enum constants do not match expected values");
    }

    @Test
    @DisplayName("Test valueOf method with null value")
    void testValueOf_whenNull() {
        assertThrows(NullPointerException.class,
                () -> MPAA.valueOf(null),
                "Enum should not be returned from null value");
    }

    @ParameterizedTest
    @ValueSource(strings = {"R", "G", "PG_13"})
    @DisplayName("Test valueOf method with valid values")
    void testValueOf_whenValidValue(String value) {
        assertDoesNotThrow(() -> MPAA.valueOf(value), "Enum should be returned from valid value");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "g", "123", "PG-13"})
    @DisplayName("Test valueOf method with invalid values")
    void testValueOf_whenInvalidValue(String value) {
        assertThrows(IllegalArgumentException.class,
                () -> MPAA.valueOf(value),
                "Enum should not be returned from invalid value");
    }

    @ParameterizedTest
    @ValueSource(strings = {"R", "G", "PG"})
    @DisplayName("Test rating field getter returns proper values")
    void testGetRating(String rating) {
        MPAA mpaa = MPAA.valueOf(rating);
        String actual = mpaa.getRating();

        assertEquals(rating, actual, "Rating of mpaa should be equal to it's name");
    }

    private MPAA[] getArrayOfRatings() {
        return new MPAA[]{
                MPAA.G,
                MPAA.PG,
                MPAA.PG_13,
                MPAA.R,
                MPAA.NC_17
        };
    }

}