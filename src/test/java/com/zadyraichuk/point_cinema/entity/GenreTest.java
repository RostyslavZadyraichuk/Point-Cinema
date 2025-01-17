package com.zadyraichuk.point_cinema.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Genre enum tests")
class GenreTest {

    @Test
    @DisplayName("Test enum values array is valid")
    void testValues() {
        Genre[] expected = getArrayOfGenres();
        Genre[] actual = Genre.values();

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
                () -> Genre.valueOf(null),
                "Enum should not be returned from null value");
    }

    @ParameterizedTest
    @ValueSource(strings = {"CARTOON", "HORROR", "THRILLER"})
    @DisplayName("Test valueOf method with valid values")
    void testValueOf_whenValidValue(String value) {
        assertDoesNotThrow(() -> Genre.valueOf(value), "Enum should be returned from valid value");
    }

    @ParameterizedTest
    @ValueSource(strings = {"comedy", "", "Wednesday", "123", "documentar", "action"})
    @DisplayName("Test valueOf method with invalid values")
    void testValueOf_whenInvalidValue(String value) {
        assertThrows(IllegalArgumentException.class,
                () -> Genre.valueOf(value),
                "Enum should not be returned from invalid value");
    }

    private Genre[] getArrayOfGenres() {
        return new Genre[]{
                Genre.ACTION,
                Genre.COMEDY,
                Genre.CARTOON,
                Genre.ROMANCE,
                Genre.CRIMINAL,
                Genre.SCIENCE_FICTION,
                Genre.DOCUMENTARY,
                Genre.HORROR,
                Genre.FANTASY,
                Genre.ADVENTURE,
                Genre.DETECTIVE,
                Genre.THRILLER,
                Genre.HISTORICAL,
                Genre.DRAMA
        };
    }
}