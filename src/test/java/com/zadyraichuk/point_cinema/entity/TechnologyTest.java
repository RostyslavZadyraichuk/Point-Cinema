package com.zadyraichuk.point_cinema.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Technology enum tests")
class TechnologyTest {

    @Test
    @DisplayName("Test enum values array is valid")
    void testValues() {
        Technology[] expected = getArrayOfTechnologies();
        Technology[] actual = Technology.values();

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
                () -> Technology.valueOf(null),
                "Enum should not be returned from null value");
    }

    @ParameterizedTest
    @ValueSource(strings = {"_2D", "_RM", "_RM_PLUS"})
    @DisplayName("Test valueOf method with valid values")
    void testValueOf_whenValidValue(String value) {
        assertDoesNotThrow(() -> Technology.valueOf(value), "Enum should be returned from valid value");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "rm", "123", "2D", "RM", "_3d"})
    @DisplayName("Test valueOf method with invalid values")
    void testValueOf_whenInvalidValue(String value) {
        assertThrows(IllegalArgumentException.class,
                () -> Technology.valueOf(value),
                "Enum should not be returned from invalid value");
    }

    @ParameterizedTest
    @ValueSource(strings = {"2D", "4D", "RM"})
    @DisplayName("Test type field getter returns proper values")
    void testGetType(String type) {
        String enumValue = '_' + type;
        Technology technology = Technology.valueOf(enumValue);
        String actual = technology.getType();

        assertEquals(type, actual, "Type of technology should be equal to it's name");
    }

    private Technology[] getArrayOfTechnologies() {
        return new Technology[]{
                Technology._2D,
                Technology._3D,
                Technology._4D,
                Technology._RM,
                Technology._RM_PLUS
        };
    }
}