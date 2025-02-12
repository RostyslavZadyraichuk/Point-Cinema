package com.zadyraichuk.point_cinema.dto.identified;

import com.zadyraichuk.point_cinema.dto.general.PictureDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Identified picture data transfer object class tests")
class IdentitfiedActorDTOTest {

    private static String id;
    private static String firstName;
    private static String lastName;
    private static PictureDTO picture;
    private static Validator validator;

    @BeforeAll
    static void setupValidator() {
        id = "1";
        firstName = "test";
        lastName = "test";
        picture = null;

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        factory.close();
    }

    @Test
    @DisplayName("Test all-args constructor")
    void testAllArgsConstructor() {
        String idLocal = "10";
        String firstNameLocal = "Jane";
        String lastNameLocal = "Doe";
        PictureDTO pictureLocal = Mockito.mock(PictureDTO.class);

        IdentitfiedActorDTO actor = new IdentitfiedActorDTO(idLocal, firstNameLocal, lastNameLocal, pictureLocal);

        assertEquals(idLocal, actor.getId(), "Id does not match the expected value");
        assertEquals(firstNameLocal, actor.getFirstName(), "First name does not match the expected value");
        assertEquals(lastNameLocal, actor.getLastName(), "Last name does not match the expected value");
        assertEquals(pictureLocal, actor.getPicture(), "Picture does not match the expected value");
    }

    @Test
    @DisplayName("Test getter methods for all fields")
    void testGetterMethods() {
        IdentitfiedActorDTO actor = new IdentitfiedActorDTO(id, firstName, lastName, picture);

        assertEquals(id, actor.getId(), "Getter for Id returned an unexpected value");
        assertEquals(firstName, actor.getFirstName(), "Getter for First name returned an unexpected value");
        assertEquals(lastName, actor.getLastName(), "Getter for Last name returned an unexpected value");
        assertEquals(picture, actor.getPicture(), "Getter for Picture returned an unexpected value");
    }

    @ParameterizedTest
    @MethodSource("provideDataForIdValidationTest")
    @DisplayName("Test id field validation")
    void testIdValidation(String id, int expectedViolations) {
        IdentitfiedActorDTO actorDTO = new IdentitfiedActorDTO(id, "test", "test", null);
        Set<ConstraintViolation<IdentitfiedActorDTO>> violations = validator.validate(actorDTO);

        assertEquals(violations.size(), expectedViolations, "Wrong number of violations");
        if (expectedViolations != 0) {
            assertEquals("Id cannot be null, empty or blank", violations.iterator().next().getMessage(),
                    "Wrong error message");
        }
    }

    /**
     * Provides arguments for testing the testIdValidation.
     * The arguments are:
     * <ul>
     *     <li>id - string with actor identifier</li>
     *     <li>expectedViolations - the expected number of violations</li>
     * </ul>
     *
     * @return a stream of arguments for parameterized tests
     */
    private static Stream<Arguments> provideDataForIdValidationTest() {
        return Stream.of(
                Arguments.of(null, 1),
                Arguments.of("", 1),
                Arguments.of("  ", 1),
                Arguments.of("1", 0),
                Arguments.of(" 1  ", 0)
        );
    }

}
