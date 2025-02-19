package com.zadyraichuk.point_cinema.dto.identified;

import com.zadyraichuk.point_cinema.dto.ValidationTestUtils;
import com.zadyraichuk.point_cinema.dto.general.PictureDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Identified actor data transfer object class tests")
@ExtendWith(MockitoExtension.class)
class IdentifiedActorDTOTest {

    private static String id;
    private static String firstName;
    private static String lastName;
    @Mock
    private static PictureDTO picture;

    @BeforeAll
    static void setupClass() {
        id = "1";
        firstName = "test";
        lastName = "test";
    }

    @Test
    @DisplayName("Test all-args constructor")
    void testAllArgsConstructor() {
        String idLocal = "10";
        String firstNameLocal = "Jane";
        String lastNameLocal = "Doe";
        PictureDTO pictureLocal = Mockito.mock(PictureDTO.class);

        IdentifiedActorDTO actor = new IdentifiedActorDTO(idLocal, firstNameLocal, lastNameLocal, pictureLocal);

        assertEquals(idLocal, actor.getId(), "Id does not match the expected value");
        assertEquals(firstNameLocal, actor.getFirstName(), "First name does not match the expected value");
        assertEquals(lastNameLocal, actor.getLastName(), "Last name does not match the expected value");
        assertEquals(pictureLocal, actor.getPicture(), "Picture does not match the expected value");
    }

    @Test
    @DisplayName("Test getter methods for all fields")
    void testGetterMethods() {
        IdentifiedActorDTO actor = new IdentifiedActorDTO(id, firstName, lastName, picture);

        assertEquals(id, actor.getId(), "Getter for Id returned an unexpected value");
        assertEquals(firstName, actor.getFirstName(), "Getter for First name returned an unexpected value");
        assertEquals(lastName, actor.getLastName(), "Getter for Last name returned an unexpected value");
        assertEquals(picture, actor.getPicture(), "Getter for Picture returned an unexpected value");
    }

    @DisplayName("Test equals and hashCode methods")
    @ParameterizedTest
    @MethodSource("provideDataForEqualsAndHashCodeTest")
    void testEqualsAndHashCode(IdentifiedActorDTO actor1, IdentifiedActorDTO actor2, boolean expectedResult) {
        assertEquals(expectedResult, actor1.equals(actor2), "Equals method returned false");
        assertEquals(expectedResult, actor1.hashCode() == actor2.hashCode(),
                "hashCode method returned different values");
    }

    @ParameterizedTest
    @MethodSource("provideDataForIdValidationTest")
    @DisplayName("Test id field validation")
    void testIdValidation(String id, boolean isValid) {
        IdentifiedActorDTO actorDTO = new IdentifiedActorDTO(id, "test", "test", null);
        ValidationTestUtils.validate(actorDTO, isValid);
    }

    /**
     * Provides arguments for testing the testEqualsAndHashCode.
     * The arguments are:
     * <ul>
     *     <li>actor1 - the first actor</li>
     *     <li>actor2 - the second actor</li>
     *     <li>expectedResult - the expected result of equals method</li>
     * </ul>
     *
     * @return a stream of arguments for parameterized tests
     */
    private static Stream<Arguments> provideDataForEqualsAndHashCodeTest() {
        IdentifiedActorDTO actor1 = new IdentifiedActorDTO(id, firstName, lastName, picture);
        IdentifiedActorDTO actor2 = new IdentifiedActorDTO(id, firstName, lastName, picture);
        IdentifiedActorDTO actor3 = new IdentifiedActorDTO(id, "equalsHashCode", "equalsHashCode", null);

        return Stream.of(
                Arguments.of(actor1, actor2, true),
                Arguments.of(actor2, actor1, true),
                Arguments.of(actor1, actor3, true)
        );
    }

    /**
     * Provides arguments for testing the testIdValidation.
     * The arguments are:
     * <ul>
     *     <li>id - string with actor identifier</li>
     *     <li>valid - is the id valid or not</li>
     * </ul>
     *
     * @return a stream of arguments for parameterized tests
     */
    private static Stream<Arguments> provideDataForIdValidationTest() {
        return ValidationTestUtils.forNotBlankValidation();
    }

}
