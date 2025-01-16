package com.zadyraichuk.point_cinema.entity;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

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

        assertNull(cinema.getId());
        assertEquals(nameLocal, cinema.getName());
        assertEquals(countryLocal, cinema.getCountry());
        assertEquals(cityLocal, cinema.getCity());
        assertEquals(streetLocal, cinema.getStreet());
    }

    @Test
    void testGetId_whenNewCreated() {
        cinema = initCinema();

        assertNull(cinema.getId());
    }

    @Test
    void testGetterMethods() {
        assertEquals(CinemaTest.id, cinema.getId());
        assertEquals(CinemaTest.name, cinema.getName());
        assertEquals(CinemaTest.country, cinema.getCountry());
        assertEquals(CinemaTest.city, cinema.getCity());
        assertEquals(CinemaTest.street, cinema.getStreet());
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "10", "100"})
    void testSetId(String id) {
        cinema.setId(id);

        assertEquals(id, cinema.getId());
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