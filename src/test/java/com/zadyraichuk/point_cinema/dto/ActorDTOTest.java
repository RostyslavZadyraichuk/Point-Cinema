package com.zadyraichuk.point_cinema.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ActorDTOTest {

    private static Validator validator;

    @BeforeAll
    static void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        factory.close();
    }

    @ParameterizedTest
    @MethodSource("provideDataForIdValidationTest")
    void testIdValidation(String id, int expectedViolations) {
        ActorDTO actorDTO = new ActorDTO(id, "test", "test", null);
        Set<ConstraintViolation<ActorDTO>> violations = validator.validate(actorDTO);

        assertEquals(violations.size(), expectedViolations, "Wrong number of violations");
    }

    @ParameterizedTest
    @MethodSource("provideDataForFirstNameValidationTest")
    void testFirstNameValidation(String firstName, int expectedViolations) {
        ActorDTO actorDTO = new ActorDTO("1", firstName, "test", null);
        Set<ConstraintViolation<ActorDTO>> violations = validator.validate(actorDTO);

        assertEquals(violations.size(), expectedViolations, "Wrong number of violations");
        if (expectedViolations != 0) {
            assertEquals("First name cannot be empty", violations.iterator().next().getMessage(),
                    "Wrong error message");
        }
    }

    @ParameterizedTest
    @MethodSource("provideDataForLastNameValidationTest")
    void testLastNameValidation(String lastName, int expectedViolations) {
        ActorDTO actorDTO = new ActorDTO("1", "test", lastName, null);
        Set<ConstraintViolation<ActorDTO>> violations = validator.validate(actorDTO);

        assertEquals(violations.size(), expectedViolations, "Wrong number of violations");
        if (expectedViolations != 0) {
            assertEquals("Last name cannot be empty", violations.iterator().next().getMessage(),
                    "Wrong error message");
        }
    }

    @ParameterizedTest
    @MethodSource("provideDataForPictureValidationTest")
    void testPictureValidation(PictureDTO picture, int expectedViolations) {
        ActorDTO actorDTO = new ActorDTO("1", "test", "test", picture);
        Set<ConstraintViolation<ActorDTO>> violations = validator.validate(actorDTO);

        assertEquals(violations.size(), expectedViolations, "Wrong number of violations");
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
                Arguments.of(null, 0),
                Arguments.of("", 0),
                Arguments.of("1", 0)
        );
    }

    /**
     * Provides arguments for testing the testFirstNameValidation.
     * The arguments are:
     * <ul>
     *     <li>firstName - string with actor's first name</li>
     *     <li>expectedViolations - the expected number of violations</li>
     * </ul>
     *
     * @return a stream of arguments for parameterized tests
     */
    private static Stream<Arguments> provideDataForFirstNameValidationTest() {
        return Stream.of(
                Arguments.of(null, 1),
                Arguments.of("", 1),
                Arguments.of(" ", 1),
                Arguments.of("a", 0),
                Arguments.of("  a  ", 0),
                Arguments.of("abc", 0)
        );
    }

    /**
     * Provides arguments for testing the testLastNameValidation.
     * The arguments are:
     * <ul>
     *     <li>lastName - string with actor's last name</li>
     *     <li>expectedViolations - the expected number of violations</li>
     * </ul>
     *
     * @return a stream of arguments for parameterized tests
     */
    private static Stream<Arguments> provideDataForLastNameValidationTest() {
        return Stream.of(
                Arguments.of(null, 1),
                Arguments.of("", 1),
                Arguments.of(" ", 1),
                Arguments.of("a", 0),
                Arguments.of("  a  ", 0),
                Arguments.of("abc", 0)
        );
    }


    /**
     * Provides arguments for testing the testPictureValidation.
     * The arguments are:
     * <ul>
     *     <li>picture - picture data transfer object</li>
     *     <li>expectedViolations - the expected number of violations</li>
     * </ul>
     *
     * @return a stream of arguments for parameterized tests
     */
    private static Stream<Arguments> provideDataForPictureValidationTest() {
        return Stream.of(
                Arguments.of(null, 0),
                Arguments.of(new PictureDTO(), 0)
        );
    }

}
