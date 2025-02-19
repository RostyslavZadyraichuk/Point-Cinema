package com.zadyraichuk.point_cinema.dto.identified;

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

@DisplayName("Identified cinema data transfer object class tests")
class IdentifiedCinemaDTOTest {

    private static String id;
    private static String name;
    private static Country country;
    private static String city;
    private static String street;

    @BeforeAll
    static void setupValidator() {
        id = "1";
        name = "test";
        country = Country.UKRAINE;
        city = "test";
        street = "test";
    }

    @Test
    @DisplayName("Test all-args constructor")
    void testAllArgsConstructor() {
        String idLocal = "10";
        String nameLocal = "New";
        Country countryLocal = Country.UNITED_KINGDOM;
        String cityLocal = "New";
        String streetLocal = "New";

        IdentifiedCinemaDTO cinema = new IdentifiedCinemaDTO(idLocal, nameLocal, countryLocal, cityLocal, streetLocal);

        assertEquals(idLocal, cinema.getId(), "Id does not match the expected value");
        assertEquals(nameLocal, cinema.getName(), "Name does not match the expected value");
        assertEquals(countryLocal, cinema.getCountry(), "Country does not match the expected value");
        assertEquals(cityLocal, cinema.getCity(), "City does not match the expected value");
        assertEquals(streetLocal, cinema.getStreet(), "Street does not match the expected value");
    }

    @Test
    @DisplayName("Test getter methods for all fields")
    void testGetterMethods() {
        IdentifiedCinemaDTO cinema = new IdentifiedCinemaDTO(id, name, country, city, street);

        assertEquals(id, cinema.getId(), "Getter for Id returned an unexpected value");
        assertEquals(name, cinema.getName(), "Getter for Name returned an unexpected value");
        assertEquals(country, cinema.getCountry(), "Getter for Country returned an unexpected value");
        assertEquals(city, cinema.getCity(), "Getter for City returned an unexpected value");
        assertEquals(street, cinema.getStreet(), "Getter for Street returned an unexpected value");
    }

    @DisplayName("Test equals and hashCode methods")
    @ParameterizedTest
    @MethodSource("provideDataForEqualsAndHashCodeTest")
    void testEqualsAndHashCode(IdentifiedCinemaDTO actor1, IdentifiedCinemaDTO cinema2, boolean expectedResult) {
        assertEquals(expectedResult, actor1.equals(cinema2), "Equals method returned false");
        assertEquals(expectedResult, actor1.hashCode() == cinema2.hashCode(),
                "hashCode method returned different values");
    }

    @ParameterizedTest
    @MethodSource("provideDataForIdValidationTest")
    @DisplayName("Test id field validation")
    void testIdValidation(String id, boolean isValid) {
        IdentifiedCinemaDTO cinemaDTO = new IdentifiedCinemaDTO(id, name, country, city, street);
        ValidationTestUtils.validate(cinemaDTO, isValid);
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
        IdentifiedCinemaDTO cinema1 = new IdentifiedCinemaDTO(id, name, country, city, street);
        IdentifiedCinemaDTO cinema2 = new IdentifiedCinemaDTO(id, name, country, city, street);
        IdentifiedCinemaDTO cinema3 = new IdentifiedCinemaDTO("equalsAndHashcode", name, country, city, street);

        return Stream.of(
                Arguments.of(cinema1, cinema2, true),
                Arguments.of(cinema2, cinema1, true),
                Arguments.of(cinema1, cinema3, false)
        );
    }

    /**
     * Provides arguments for testing the testIdValidation.
     * The arguments are:
     * <ul>
     *     <li>id - string with cinema identifier</li>
     *     <li>valid - is the id valid or not</li>
     * </ul>
     *
     * @return a stream of arguments for parameterized tests
     */
    private static Stream<Arguments> provideDataForIdValidationTest() {
        return ValidationTestUtils.forNotBlankValidation();
    }

}
