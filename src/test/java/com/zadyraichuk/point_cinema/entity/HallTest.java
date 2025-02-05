package com.zadyraichuk.point_cinema.entity;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Hall entity class tests")
class HallTest {

    private static String id;
    private static Integer number;
    private static Integer rows;
    private static Integer columns;
    private static Technology technology;
    private static String cinemaId;

    private Hall hall;

    @BeforeAll
    static void beforeAll() {
        HallTest.id = "1";
        HallTest.number = 1;
        HallTest.rows = 1;
        HallTest.columns = 1;
        HallTest.technology = Technology.TECHNOLOGY_3D;
        HallTest.cinemaId = "1";
    }

    @BeforeEach
    void setUp() {
        hall = initHall();
    }

    @Test
    @DisplayName("Test all-args constructor initializes fields correctly")
    void testAllArgsConstructor() {
        String idLocal = "100";
        int numberLocal = 100;
        int rowsLocal = 100;
        int columnsLocal = 100;
        Technology technologyLocal = Technology.TECHNOLOGY_2D;
        String cinemaIdLocal = "100";

        hall = new Hall(
                idLocal,
                numberLocal,
                rowsLocal,
                columnsLocal,
                technologyLocal,
                cinemaIdLocal
        );

        assertEquals(idLocal, hall.getId(), "Id does not match the expected value");
        assertEquals(numberLocal, hall.getNumber(), "Number does not match the expected value");
        assertEquals(rowsLocal, hall.getRows(), "Rows do not match the expected value");
        assertEquals(columnsLocal, hall.getColumns(), "Columns do not match the expected value");
        assertEquals(technologyLocal, hall.getTechnology(), "Technology does not match the expected value");
        assertEquals(cinemaIdLocal, hall.getCinemaId(), "CinemaId does not match the expected value");
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "10", "100"})
    @DisplayName("Test setId method with multiple values")
    void testSetId(String id) {
        hall.setId(id);

        assertEquals(id, hall.getId(), "Id does not match the value set");
    }

    @Test
    @DisplayName("Test getter methods for all fields")
    void testGetterMethods() {
        assertEquals(HallTest.id, hall.getId(), "Getter for id returned an unexpected value");
        assertEquals(HallTest.number, hall.getNumber(), "Getter for number returned an unexpected value");
        assertEquals(HallTest.rows, hall.getRows(), "Getter for rows returned an unexpected value");
        assertEquals(HallTest.columns, hall.getColumns(), "Getter for columns returned an unexpected value");
        assertEquals(HallTest.technology, hall.getTechnology(), "Getter for technology returned an unexpected value");
        assertEquals(HallTest.cinemaId, hall.getCinemaId(), "Getter for cinemaId returned an unexpected value");
    }

    private Hall initHall() {
        return Hall.builder()
                .id(HallTest.id)
                .number(HallTest.number)
                .rows(HallTest.rows)
                .columns(HallTest.columns)
                .technology(HallTest.technology)
                .cinemaId(HallTest.cinemaId)
                .build();
    }

    @Nested
    @DisplayName("HallBuilder nested class tests")
    class HallBuilderTest {

        @Test
        @DisplayName("Test builder initializes fields correctly")
        void testBuilderInitialization() {
            String idLocal = "1000";
            int numberLocal = 1000;
            int rowsLocal = 1000;
            int columnsLocal = 1000;
            Technology technologyLocal = Technology.TECHNOLOGY_4D;
            String cinemaIdLocal = "1000";

            hall = Hall.builder()
                    .id(idLocal)
                    .number(numberLocal)
                    .rows(rowsLocal)
                    .columns(columnsLocal)
                    .technology(technologyLocal)
                    .cinemaId(cinemaIdLocal)
                    .build();

            assertEquals(idLocal, hall.getId(), "Id does not match the expected value");
            assertEquals(numberLocal, hall.getNumber(), "Number does not match the expected value");
            assertEquals(rowsLocal, hall.getRows(), "Rows do not match the expected value");
            assertEquals(columnsLocal, hall.getColumns(), "Columns do not match the expected value");
            assertEquals(technologyLocal, hall.getTechnology(), "Technology does not match the expected value");
            assertEquals(cinemaIdLocal, hall.getCinemaId(), "CinemaId does not match the expected value");
        }

    }

}