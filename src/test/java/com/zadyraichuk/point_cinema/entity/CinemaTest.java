package com.zadyraichuk.point_cinema.entity;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Cinema entity class test")
class CinemaTest {

    private static String id;
    private static String name;
    private static Country country;
    private static String city;
    private static String street;

    private Cinema cinema;

    @BeforeAll
    static void beforeAll() {
        CinemaTest.id = "1";
        CinemaTest.name = "Cinema";
        CinemaTest.country = Country.UKRAINE;
        CinemaTest.city = "Main City";
        CinemaTest.street = "Main Street";
    }

    @BeforeEach
    void setUp() {
        cinema = initCinema();
        cinema.setId(CinemaTest.id);
    }

    @Test
    @DisplayName("Test required-args constructor initializes fields correctly")
    void testRequiredArgsConstructor() {
        String nameLocal = "test1";
        Country countryLocal = Country.UNITED_KINGDOM;
        String cityLocal = "test2";
        String streetLocal = "test3";

        cinema = new Cinema(
                nameLocal,
                countryLocal,
                cityLocal,
                streetLocal
        );

        assertNull(cinema.getId(), "Id should be null when using the required-args constructor");
        assertEquals(nameLocal, cinema.getName(), "Name does not match the expected value");
        assertEquals(countryLocal, cinema.getCountry(), "Country does not match the expected value");
        assertEquals(cityLocal, cinema.getCity(), "City does not match the expected value");
        assertEquals(streetLocal, cinema.getStreet(), "Street does not match the expected value");
    }

    @Test
    @DisplayName("Test getId returns null for newly created Cinema")
    void testGetId_whenNewCreated() {
        cinema = initCinema();

        assertNull(cinema.getId(), "Id should be null for a newly created Cinema instance");
    }

    @Test
    @DisplayName("Test getter methods for all fields")
    void testGetterMethods() {
        assertEquals(CinemaTest.id, cinema.getId(), "Getter for Id returned an unexpected value");
        assertEquals(CinemaTest.name, cinema.getName(), "Getter for Name returned an unexpected value");
        assertEquals(CinemaTest.country, cinema.getCountry(), "Getter for Country returned an unexpected value");
        assertEquals(CinemaTest.city, cinema.getCity(), "Getter for City returned an unexpected value");
        assertEquals(CinemaTest.street, cinema.getStreet(), "Getter for Street returned an unexpected value");
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "10", "100"})
    @DisplayName("Test setId method with multiple values")
    void testSetId(String id) {
        cinema.setId(id);

        assertEquals(id, cinema.getId(), "setId method failed to set the expected Id");
    }

    private Cinema initCinema() {
        return new Cinema(
                CinemaTest.name,
                CinemaTest.country,
                CinemaTest.city,
                CinemaTest.street
        );
    }
}