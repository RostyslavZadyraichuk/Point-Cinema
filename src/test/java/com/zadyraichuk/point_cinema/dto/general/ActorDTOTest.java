package com.zadyraichuk.point_cinema.dto.general;

import com.zadyraichuk.point_cinema.dto.ValidationTestUtils;
import com.zadyraichuk.point_cinema.dto.identified.IdentifiedPictureDTO;
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

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Actor data transfer object class tests")
@ExtendWith(MockitoExtension.class)
class ActorDTOTest {

    private static String firstName;
    private static String lastName;
    @Mock
    private static PictureDTO picture;

    @BeforeAll
    static void setupClass() {
        firstName = "test";
        lastName = "test";
    }

    @Test
    @DisplayName("Test all-args constructor")
    void testAllArgsConstructor() {
        String firstNameLocal = "Jane";
        String lastNameLocal = "Doe";
        PictureDTO pictureLocal = Mockito.mock(PictureDTO.class);

        ActorDTO actor = new ActorDTO(firstNameLocal, lastNameLocal, pictureLocal);

        assertEquals(firstNameLocal, actor.getFirstName(), "First name does not match the expected value");
        assertEquals(lastNameLocal, actor.getLastName(), "Last name does not match the expected value");
        assertEquals(pictureLocal, actor.getPicture(), "Picture does not match the expected value");
    }

    @Test
    @DisplayName("Test getter methods for all fields")
    void testGetterMethods() {
        ActorDTO actor = new ActorDTO(firstName, lastName, picture);

        assertEquals(firstName, actor.getFirstName(), "Getter for First name returned an unexpected value");
        assertEquals(lastName, actor.getLastName(), "Getter for Last name returned an unexpected value");
        assertEquals(picture, actor.getPicture(), "Getter for Picture returned an unexpected value");
    }

    @DisplayName("Test equals and hashCode methods")
    @ParameterizedTest
    @MethodSource("provideDataForEqualsAndHashCodeTest")
    void testEqualsAndHashCode(ActorDTO actor1, ActorDTO actor2, boolean expectedResult) {
        assertEquals(expectedResult, actor1.equals(actor2), "Equals method returned false");
        assertEquals(expectedResult, actor1.hashCode() == actor2.hashCode(),
                "hashCode method returned different values");
    }

    @Test
    @DisplayName("Test with method for updating picture")
    void testWith() {
        ActorDTO actor = new ActorDTO(firstName, lastName, picture);
        PictureDTO localPicture = Mockito.mock(PictureDTO.class);
        ActorDTO actorUpdated = actor.withPicture(localPicture);

        assertEquals(localPicture, actorUpdated.getPicture(), "Picture does not match the expected value");
        assertNotEquals(localPicture, actor.getPicture(), "Picture match the unexpected value");
        assertNotEquals(actor.getPicture(), actorUpdated.getPicture(), "Picture was not updated");
    }

    @ParameterizedTest
    @MethodSource("provideDataForFirstNameValidationTest")
    @DisplayName("Test first name field validation")
    void testFirstNameValidation(String firstNameLocal, boolean isValid) {
        ActorDTO actorDTO = new ActorDTO(firstNameLocal, "test", null);
        ValidationTestUtils.validate(actorDTO, isValid);
    }

    @ParameterizedTest
    @MethodSource("provideDataForLastNameValidationTest")
    @DisplayName("Test last name field validation")
    void testLastNameValidation(String lastNameLocal, boolean isValid) {
        ActorDTO actorDTO = new ActorDTO("test", lastNameLocal, null);
        ValidationTestUtils.validate(actorDTO, isValid);
    }

    @ParameterizedTest
    @MethodSource("provideDataForPictureValidationTest")
    @DisplayName("Test picture field validation")
    void testPictureValidation(PictureDTO pictureLocal, boolean isValid) {
        ActorDTO actorDTO = new ActorDTO("test", "test", pictureLocal);
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
        ActorDTO actor1 = new ActorDTO(firstName, lastName, picture);
        ActorDTO actor2 = new ActorDTO(firstName, lastName, picture);
        ActorDTO actor3 = new ActorDTO(firstName, lastName, null);
        ActorDTO actor4 = new ActorDTO(firstName, "equalsHashCode", picture);
        ActorDTO actor5 = new ActorDTO("equalsHashCode", lastName, picture);

        return Stream.of(
                Arguments.of(actor1, actor2, true),
                Arguments.of(actor2, actor1, true),
                Arguments.of(actor1, actor3, true),
                Arguments.of(actor1, actor4, false),
                Arguments.of(actor1, actor5, false)
        );
    }

    /**
     * Provides arguments for testing the testFirstNameValidation.
     * The arguments are:
     * <ul>
     *     <li>firstName - string with actor's first name</li>
     *     <li>valid - is the first name valid or not</li>
     * </ul>
     *
     * @return a stream of arguments for parameterized tests
     */
    private static Stream<Arguments> provideDataForFirstNameValidationTest() {
        return ValidationTestUtils.forNotBlankValidation();
    }

    /**
     * Provides arguments for testing the testLastNameValidation.
     * The arguments are:
     * <ul>
     *     <li>lastName - string with actor's last name</li>
     *     <li>valid - is the last name valid or not</li>
     * </ul>
     *
     * @return a stream of arguments for parameterized tests
     */
    private static Stream<Arguments> provideDataForLastNameValidationTest() {
        return ValidationTestUtils.forNotBlankValidation();
    }


    /**
     * Provides arguments for testing the testPictureValidation.
     * The arguments are:
     * <ul>
     *     <li>picture - picture data transfer object</li>
     *     <li>valid - is the picture valid or not</li>
     * </ul>
     *
     * @return a stream of arguments for parameterized tests
     */
    private static Stream<Arguments> provideDataForPictureValidationTest() {
        return Stream.of(
                Arguments.of(null, true),
                Arguments.of(Mockito.mock(PictureDTO.class), true),
                Arguments.of(Mockito.mock(IdentifiedPictureDTO.class), true)
        );
    }

}
