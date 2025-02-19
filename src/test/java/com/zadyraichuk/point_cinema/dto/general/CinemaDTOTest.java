package com.zadyraichuk.point_cinema.dto.general;

import com.zadyraichuk.point_cinema.dto.ValidationTestUtils;
import com.zadyraichuk.point_cinema.entity.Country;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Cinema data transfer object class tests")
class CinemaDTOTest {

    private static String name;
    private static Country country;
    private static String city;
    private static String street;

    @BeforeAll
    static void setupClass() {
        name = "test";
        country = Country.UKRAINE;
        city = "test";
        street = "test";
    }

    @Test
    @DisplayName("Test all-args constructor")
    void testAllArgsConstructor() {
        String nameLocal = "Name";
        Country countryLocal = Country.UNITED_KINGDOM;
        String cityLocal = "City";
        String streetLocal = "Street";

        CinemaDTO cinema = new CinemaDTO(nameLocal, countryLocal, cityLocal, streetLocal);

        assertEquals(nameLocal, cinema.getName(), "Name does not match the expected value");
        assertEquals(countryLocal, cinema.getCountry(), "Country does not match the expected value");
        assertEquals(cityLocal, cinema.getCity(), "City does not match the expected value");
        assertEquals(streetLocal, cinema.getStreet(), "Street does not match the expected value");
    }

    @Test
    @DisplayName("Test getter methods for all fields")
    void testGetterMethods() {
        CinemaDTO cinema = new CinemaDTO(name, country, city, street);

        assertEquals(name, cinema.getName(), "Getter for Name returned an unexpected value");
        assertEquals(country, cinema.getCountry(), "Getter for Country returned an unexpected value");
        assertEquals(city, cinema.getCity(), "Getter for City returned an unexpected value");
        assertEquals(street, cinema.getStreet(), "Getter for Street returned an unexpected value");
    }

    @DisplayName("Test equals and hashCode methods")
    @ParameterizedTest
    @MethodSource("provideDataForEqualsAndHashCodeTest")
    void testEqualsAndHashCode(CinemaDTO cinema1, CinemaDTO cinema2, boolean expectedResult) {
        assertEquals(expectedResult, cinema1.equals(cinema2), "Equals method returned false");
        assertEquals(expectedResult, cinema1.hashCode() == cinema2.hashCode(),
                "hashCode method returned different values");
    }

    @ParameterizedTest
    @MethodSource("provideDataForNameValidationTest")
    @DisplayName("Test name field validation")
    void testNameValidation(String localName, boolean isValid) {
        CinemaDTO cinema = new CinemaDTO(localName, country, city, street);
        ValidationTestUtils.validate(cinema, isValid);
    }

    @ParameterizedTest
    @MethodSource("provideDataForCountryValidationTest")
    @DisplayName("Test country field validation")
    void testCountryValidation(Country localCountry, boolean isValid) {
        CinemaDTO cinema = new CinemaDTO(name, localCountry, city, street);
        ValidationTestUtils.validate(cinema, isValid);
    }

    @ParameterizedTest
    @MethodSource("provideDataForCityValidationTest")
    @DisplayName("Test city field validation")
    void testCityValidation(String localCity, boolean isValid) {
        CinemaDTO cinema = new CinemaDTO(name, country, localCity, street);
        ValidationTestUtils.validate(cinema, isValid);
    }

    @ParameterizedTest
    @MethodSource("provideDataForStreetValidationTest")
    @DisplayName("Test street field validation")
    void testStreetValidation(String localStreet, boolean isValid) {
        CinemaDTO cinema = new CinemaDTO(name, country, city, localStreet);
        ValidationTestUtils.validate(cinema, isValid);
    }

    /**
     * Provides arguments for testing the testEqualsAndHashCode.
     * The arguments are:
     * <ul>
     *     <li>cinema1 - the first cinema</li>
     *     <li>cinema2 - the second cinema</li>
     *     <li>expectedResult - the expected result of equals method</li>
     * </ul>
     *
     * @return a stream of arguments for parameterized tests
     */
    private static Stream<Arguments> provideDataForEqualsAndHashCodeTest() {
        CinemaDTO cinema1 = new CinemaDTO(name, country, city, street);
        CinemaDTO cinema2 = new CinemaDTO(name, country, city, street);
        CinemaDTO cinema3 = new CinemaDTO("equalsAndHashcode", country, city, street);
        CinemaDTO cinema4 = new CinemaDTO("equalsAndHashcode", Country.POLAND, city, street);
        CinemaDTO cinema5 = new CinemaDTO("equalsAndHashcode", country, "equalsAndHashcode", street);
        CinemaDTO cinema6 = new CinemaDTO("equalsAndHashcode", country, city, "equalsAndHashcode");

        return Stream.of(
                Arguments.of(cinema1, cinema2, true),
                Arguments.of(cinema2, cinema1, true),
                Arguments.of(cinema1, cinema3, true),
                Arguments.of(cinema1, cinema4, false),
                Arguments.of(cinema1, cinema5, false),
                Arguments.of(cinema1, cinema6, false)
        );
    }

    /**
     * Provides arguments for testing the testNameValidation.
     * The arguments are:
     * <ul>
     *     <li>name - string with cinema's name</li>
     *     <li>valid - is the name valid or not</li>
     * </ul>
     *
     * @return a stream of arguments for parameterized tests
     */
    private static Stream<Arguments> provideDataForNameValidationTest() {
        return ValidationTestUtils.forNotBlankValidation();
    }

    /**
     * Provides arguments for testing the testCountryValidation.
     * The arguments are:
     * <ul>
     *     <li>country - country of cinema</li>
     *     <li>valid - is the country valid or not</li>
     * </ul>
     *
     * @return a stream of arguments for parameterized tests
     */
    private static Stream<Arguments> provideDataForCountryValidationTest() {
        return Stream.of(
                Arguments.of(null, false),
                Arguments.of(Country.UKRAINE, true),
                Arguments.of(Country.UNITED_KINGDOM, true)
        );
    }

    /**
     * Provides arguments for testing the testCityValidation.
     * The arguments are:
     * <ul>
     *     <li>city - string with cinema's city</li>
     *     <li>valid - is the city valid or not</li>
     * </ul>
     *
     * @return a stream of arguments for parameterized tests
     */
    private static Stream<Arguments> provideDataForCityValidationTest() {
        return ValidationTestUtils.forNotBlankValidation();
    }

    /**
     * Provides arguments for testing the testStreetValidation.
     * The arguments are:
     * <ul>
     *     <li>street - string with cinema's street</li>
     *     <li>valid - is the street valid or not</li>
     * </ul>
     *
     * @return a stream of arguments for parameterized tests
     */
    private static Stream<Arguments> provideDataForStreetValidationTest() {
        return ValidationTestUtils.forNotBlankValidation();
    }

}
