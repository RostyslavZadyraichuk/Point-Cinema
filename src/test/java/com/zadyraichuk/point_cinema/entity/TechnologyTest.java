package com.zadyraichuk.point_cinema.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Technology enum tests")
class TechnologyTest {

    @Test
    @DisplayName("Test enum values array is valid")
    void testValues() {
        Set<Technology> expected = getSetOfExpectedTechnologies();
        Set<Technology> actual = Set.of(Technology.values());

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
                () -> Technology.valueOf(null),
                "Enum should not be returned from null value");
    }

    @ParameterizedTest
    @ValueSource(strings = {"TECHNOLOGY_2D", "TECHNOLOGY_RM", "TECHNOLOGY_RM_PLUS"})
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
        String enumValue = "TECHNOLOGY_" + type;
        Technology technology = Technology.valueOf(enumValue);
        String actual = technology.getType();

        assertEquals(type, actual, "Type of technology should be equal to it's name");
    }

    private Set<Technology> getSetOfExpectedTechnologies() {
        return Set.of(
                Technology.TECHNOLOGY_2D,
                Technology.TECHNOLOGY_3D,
                Technology.TECHNOLOGY_4D,
                Technology.TECHNOLOGY_RM,
                Technology.TECHNOLOGY_RM_PLUS
        );
    }
}