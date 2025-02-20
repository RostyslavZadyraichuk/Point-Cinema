package com.zadyraichuk.point_cinema.dto.request;

import com.zadyraichuk.point_cinema.dto.ValidationTestUtils;
import com.zadyraichuk.point_cinema.entity.Technology;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("New hall data transfer object class tests")
class NewHallDTOTest {

    private static Integer number;
    private static Integer rows;
    private static Integer columns;
    private static Technology technology;
    private static String cinemaId;

    @BeforeAll
    static void setupClass() {
        number = 1;
        rows = 1;
        columns = 1;
        technology = Technology.TECHNOLOGY_3D;
        cinemaId = "test";
    }

    @Test
    @DisplayName("Test all-args constructor")
    void testAllArgsConstructor() {
        Integer numberLocal = 10;
        Integer rowsLocal = 10;
        Integer columnsLocal = 10;
        Technology technologyLocal = Technology.TECHNOLOGY_4D;
        String cinemaIdLocal = "allArgsConstructor";

        NewHallDTO newHallDTO = new NewHallDTO(numberLocal, rowsLocal, columnsLocal, technologyLocal, cinemaIdLocal);

        assertEquals(numberLocal, newHallDTO.getNumber(), "Number does not match the expected value");
        assertEquals(rowsLocal, newHallDTO.getRows(), "Rows do not match the expected value");
        assertEquals(columnsLocal, newHallDTO.getColumns(), "Columns do not match the expected value");
        assertEquals(technologyLocal, newHallDTO.getTechnology(), "Technology does not match the expected value");
        assertEquals(cinemaIdLocal, newHallDTO.getCinemaId(), "CinemaId does not match the expected value");
    }

    @Test
    @DisplayName("Test getter methods for all fields")
    void testGetterMethods() {
        NewHallDTO newHallDTO = new NewHallDTO(number, rows, columns, technology, cinemaId);

        assertEquals(number, newHallDTO.getNumber(), "Getter for Number returned an unexpected value");
        assertEquals(rows, newHallDTO.getRows(), "Getter for Rows returned an unexpected value");
        assertEquals(columns, newHallDTO.getColumns(), "Getter for Columns returned an unexpected value");
        assertEquals(technology, newHallDTO.getTechnology(), "Getter for Technology returned an unexpected value");
        assertEquals(cinemaId, newHallDTO.getCinemaId(), "Getter for CinemaId returned an unexpected value");
    }

    @DisplayName("Test equals and hashCode methods")
    @ParameterizedTest
    @MethodSource("provideDataForEqualsAndHashCodeTest")
    void testEqualsAndHashCode(NewHallDTO newHallDTO1, NewHallDTO newHallDTO2, boolean expectedResult) {
        assertEquals(expectedResult, newHallDTO1.equals(newHallDTO2), "Equals method returned false");
        assertEquals(expectedResult, newHallDTO1.hashCode() == newHallDTO2.hashCode(),
                "hashCode method returned different values");
    }

    @ParameterizedTest
    @MethodSource("com.zadyraichuk.point_cinema.dto.ValidationTestUtils#forPositiveValidation")
    @MethodSource("provideDataForNumberValidationTest")
    @DisplayName("Test number field validation")
    void testNumberValidation(Integer numberLocal, boolean isValid) {
        NewHallDTO newHallDTO = new NewHallDTO(numberLocal, rows, columns, technology, cinemaId);
        ValidationTestUtils.validate(newHallDTO, isValid);
    }

    @ParameterizedTest
    @MethodSource("provideDataForRowsAndColumnsValidationTests")
    @DisplayName("Test rows field validation")
    void testRowsValidation(Integer rowsLocal, boolean isValid) {
        NewHallDTO newHallDTO = new NewHallDTO(number, rowsLocal, columns, technology, cinemaId);
        ValidationTestUtils.validate(newHallDTO, isValid);
    }

    @ParameterizedTest
    @MethodSource("provideDataForRowsAndColumnsValidationTests")
    @DisplayName("Test columns field validation")
    void testColumnsValidation(Integer columnsLocal, boolean isValid) {
        NewHallDTO newHallDTO = new NewHallDTO(number, rows, columnsLocal, technology, cinemaId);
        ValidationTestUtils.validate(newHallDTO, isValid);
    }

    @ParameterizedTest
    @MethodSource("provideDataForTechnologyValidationTest")
    @DisplayName("Test technology field validation")
    void testTechnologyValidation(Technology technologyLocal, boolean isValid) {
        NewHallDTO newHallDTO = new NewHallDTO(number, rows, columns, technologyLocal, cinemaId);
        ValidationTestUtils.validate(newHallDTO, isValid);
    }

    @ParameterizedTest
    @MethodSource("com.zadyraichuk.point_cinema.dto.ValidationTestUtils#forNotBlankValidation")
    @DisplayName("Test cinemaId field validation")
    void testCinemaIdValidation(String cinemaIdLocal, boolean isValid) {
        NewHallDTO newHallDTO = new NewHallDTO(number, rows, columns, technology, cinemaIdLocal);
        ValidationTestUtils.validate(newHallDTO, isValid);
    }

    /**
     * Provides arguments for testing the testEqualsAndHashCode.
     * The arguments are:
     * <ul>
     *     <li>hall1 - the first hall</li>
     *     <li>hall2 - the second hall</li>
     *     <li>expectedResult - the expected result of equals method</li>
     * </ul>
     *
     * @return a stream of arguments for parameterized tests
     */
    private static Stream<Arguments> provideDataForEqualsAndHashCodeTest() {
        NewHallDTO hall1 = new NewHallDTO(number, null, null, null, cinemaId);
        NewHallDTO hall2 = new NewHallDTO(number, null, null, null, cinemaId);
        NewHallDTO hall3 = new NewHallDTO(number, null, null, null, "equalsAndHashCode");
        NewHallDTO hall4 = new NewHallDTO(100, null, null, null, cinemaId);

        return Stream.of(
                Arguments.of(hall1, hall2, true),
                Arguments.of(hall2, hall1, true),
                Arguments.of(hall1, hall3, false),
                Arguments.of(hall1, hall4, false)
        );
    }

    /**
     * Provides arguments for testing the testNumberValidation.
     * The arguments are:
     * <ul>
     *     <li>number - hall's number</li>
     *     <li>valid - is the number valid or not</li>
     * </ul>
     *
     * @return a stream of arguments for parameterized tests
     */
    private static Stream<Arguments> provideDataForNumberValidationTest() {
        return Stream.of(
                Arguments.of(null, false),
                Arguments.of(1, true)
        );
    }

    /**
     * Provides arguments for testing the testTechnologyValidation.
     * The arguments are:
     * <ul>
     *     <li>technology - hall's technology</li>
     *     <li>valid - is the number valid or not</li>
     * </ul>
     *
     * @return a stream of arguments for parameterized tests
     */
    private static Stream<Arguments> provideDataForTechnologyValidationTest() {
        return ValidationTestUtils.forEnumValidation(Technology.class);
    }

    /**
     * Provides arguments for testing the testRowsValidation and testColumnsValidation.
     * The arguments are:
     * <ul>
     *     <li>column/rows - hall's columns/rows count</li>
     *     <li>valid - is the columns/rows valid or not</li>
     * </ul>
     *
     * @return a stream of arguments for parameterized tests
     */
    private static Stream<Arguments> provideDataForRowsAndColumnsValidationTests() {
        return Stream.of(
                Arguments.of(-1, false),
                Arguments.of(0, false),
                Arguments.of(1, true),
                Arguments.of(50, true),
                Arguments.of(51, false)
        );
    }

}
