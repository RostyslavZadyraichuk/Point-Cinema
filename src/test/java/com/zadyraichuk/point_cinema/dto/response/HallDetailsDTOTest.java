package com.zadyraichuk.point_cinema.dto.response;

import com.zadyraichuk.point_cinema.dto.request.NewHallDTO;
import com.zadyraichuk.point_cinema.entity.Technology;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Hall details data transfer object class tests")
class HallDetailsDTOTest {

    private static String id;
    private static Integer number;
    private static Integer rows;
    private static Integer columns;
    private static Technology technology;
    private static String cinemaId;

    @BeforeAll
    static void setupClass() {
        id = "test";
        number = 1;
        rows = 1;
        columns = 1;
        technology = Technology.TECHNOLOGY_3D;
        cinemaId = "test";
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
    void testEqualsAndHashCode(HallDetailsDTO hallDetailsDTO1, HallDetailsDTO hallDetailsDTO2, boolean expectedResult) {
        assertEquals(expectedResult, hallDetailsDTO1.equals(hallDetailsDTO2), "Equals method returned false");
        assertEquals(expectedResult, hallDetailsDTO1.hashCode() == hallDetailsDTO2.hashCode(),
                "hashCode method returned different values");
    }

    @Nested
    @DisplayName("HallDetailsDTOBuilder nested class tests")
    class HallDetailsDTOBuilderTest {

        @Test
        @DisplayName("Test builder initializes fields correctly")
        void testBuilderInitialization() {
            String idLocal = "1000";
            int numberLocal = 1000;
            int rowsLocal = 1000;
            int columnsLocal = 1000;
            Technology technologyLocal = Technology.TECHNOLOGY_4D;
            String cinemaIdLocal = "1000";

            HallDetailsDTO hallDetailsDTO = HallDetailsDTO.builder()
                    .id(idLocal)
                    .number(numberLocal)
                    .rows(rowsLocal)
                    .columns(columnsLocal)
                    .technology(technologyLocal)
                    .cinemaId(cinemaIdLocal)
                    .build();

            assertEquals(idLocal, hallDetailsDTO.getId(), "Id does not match the expected value");
            assertEquals(numberLocal, hallDetailsDTO.getNumber(), "Number does not match the expected value");
            assertEquals(rowsLocal, hallDetailsDTO.getRows(), "Rows do not match the expected value");
            assertEquals(columnsLocal, hallDetailsDTO.getColumns(), "Columns do not match the expected value");
            assertEquals(technologyLocal, hallDetailsDTO.getTechnology(), "Technology does not match the expected value");
            assertEquals(cinemaIdLocal, hallDetailsDTO.getCinemaId(), "CinemaId does not match the expected value");
        }

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
        HallDetailsDTO hall1 = createHallDetailsDTO(id);
        HallDetailsDTO hall2 = createHallDetailsDTO(id);
        HallDetailsDTO hall3 = createHallDetailsDTO("equalsAndHashCode");

        return Stream.of(
                Arguments.of(hall1, hall2, true),
                Arguments.of(hall2, hall1, true),
                Arguments.of(hall1, hall3, false)
        );
    }

    private static HallDetailsDTO createHallDetailsDTO(String id) {
        return HallDetailsDTO.builder()
                .id(id)
                .number(number)
                .rows(rows)
                .columns(columns)
                .technology(technology)
                .cinemaId(cinemaId)
                .build();
    }

}
