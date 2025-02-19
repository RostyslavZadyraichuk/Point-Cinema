package com.zadyraichuk.point_cinema.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IdentifiedDTOTest {

    private static String id;

    @BeforeEach
    void setupClass() {
        id = "test";
    }

    @Test
    @DisplayName("Test all-args constructor")
    void testAllArgsConstructor() {
        String idLocal = "10";

        TestIdentifiedDTO dto = new TestIdentifiedDTO(idLocal);

        assertEquals(idLocal, dto.getId(), "Id does not match the expected value");
    }

    @Test
    @DisplayName("Test getter methods for all fields")
    void testGetterMethods() {
        TestIdentifiedDTO dto = new TestIdentifiedDTO(id);

        assertEquals(id, dto.getId(), "Getter for Id returned an unexpected value");
    }

    @DisplayName("Test equals and hashCode methods")
    @ParameterizedTest
    @MethodSource("provideDataForEqualsAndHashCodeTest")
    void testEqualsAndHashCode(TestIdentifiedDTO dto1, TestIdentifiedDTO dto2, boolean expectedResult) {
        assertEquals(expectedResult, dto1.equals(dto2), "Equals method returned false");
        assertEquals(expectedResult, dto1.hashCode() == dto2.hashCode(),
                "hashCode method returned different values");
    }

    @ParameterizedTest
    @MethodSource("provideDataForIdValidationTest")
    @DisplayName("Test id field validation")
    void testIdValidation(String id, boolean isValid) {
        TestIdentifiedDTO dto = new TestIdentifiedDTO(id);
        ValidationTestUtils.validate(dto, isValid);
    }

    /**
     * Provides arguments for testing the testEqualsAndHashCode.
     * The arguments are:
     * <ul>
     *     <li>ide1 - the first id</li>
     *     <li>id2 - the second id</li>
     *     <li>expectedResult - the expected result of equals method</li>
     * </ul>
     *
     * @return a stream of arguments for parameterized tests
     */
    private static Stream<Arguments> provideDataForEqualsAndHashCodeTest() {
        TestIdentifiedDTO dto1 = new TestIdentifiedDTO(id);
        TestIdentifiedDTO dto2 = new TestIdentifiedDTO(id);
        TestIdentifiedDTO dto3 = new TestIdentifiedDTO("equalsHashCode");

        return Stream.of(
                Arguments.of(dto1, dto2, true),
                Arguments.of(dto2, dto1, true),
                Arguments.of(dto1, dto3, false)
        );
    }

    /**
     * Provides arguments for testing the testIdValidation.
     * The arguments are:
     * <ul>
     *     <li>id - string with picture identifier</li>
     *     <li>valid - is the id valid or not</li>
     * </ul>
     *
     * @return a stream of arguments for parameterized tests
     */
    private static Stream<Arguments> provideDataForIdValidationTest() {
        return ValidationTestUtils.forNotBlankValidation();
    }

    @Getter
    @EqualsAndHashCode(callSuper = true)
    private static class TestIdentifiedDTO extends IdentifiedDTO {
        public TestIdentifiedDTO(String id) {
            super(id);
        }
    }

}
