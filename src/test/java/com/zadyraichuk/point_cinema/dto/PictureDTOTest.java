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

import static org.junit.jupiter.api.Assertions.*;

class PictureDTOTest {

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
        PictureDTO pictureDTO = new PictureDTO(id, new byte[]{1, 2, 3}, "png");
        Set<ConstraintViolation<PictureDTO>> violations = validator.validate(pictureDTO);

        assertEquals(violations.size(), expectedViolations, "Wrong number of violations");
    }

    @ParameterizedTest
    @MethodSource("provideDataForPictureDataValidationTest")
    void testPictureDataValidation(byte[] pictureData, int expectedViolations) {
        PictureDTO pictureDTO = new PictureDTO("1", pictureData, "png");
        Set<ConstraintViolation<PictureDTO>> violations = validator.validate(pictureDTO);

        assertEquals(violations.size(), expectedViolations, "Wrong number of violations");
        if (expectedViolations != 0) {
            assertEquals("Picture data cannot be empty", violations.iterator().next().getMessage(),
                    "Wrong error message");
        }
    }

    @ParameterizedTest
    @MethodSource("provideDataForFormatValidationTest")
    void testFormatValidation(String format, int expectedViolations) {
        PictureDTO pictureDTO = new PictureDTO("1", new byte[]{1, 2, 3}, format);
        Set<ConstraintViolation<PictureDTO>> violations = validator.validate(pictureDTO);

        assertEquals(violations.size(), expectedViolations, "Wrong number of violations");
        if (expectedViolations != 0) {
            assertEquals("Format cannot be empty", violations.iterator().next().getMessage(),
                    "Wrong error message");
        }
    }

    /**
     * Provides arguments for testing the testIdValidation.
     * The arguments are:
     * <ul>
     *     <li>id - string with picture identifier</li>
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
     * Provides arguments for testing the testPictureDataValidation.
     * The arguments are:
     * <ul>
     *     <li>pictureData - picture data</li>
     *     <li>expectedViolations - the expected number of violations</li>
     * </ul>
     *
     * @return a stream of arguments for parameterized tests
     */
    private static Stream<Arguments> provideDataForPictureDataValidationTest() {
        return Stream.of(
                Arguments.of(null, 1),
                Arguments.of(new byte[0], 1),
                Arguments.of(new byte[]{1, 2, 3}, 0)
        );
    }


    /**
     * Provides arguments for testing the testFormatValidation.
     * The arguments are:
     * <ul>
     *     <li>format - string with picture format</li>
     *     <li>expectedViolations - the expected number of violations</li>
     * </ul>
     *
     * @return a stream of arguments for parameterized tests
     */
    private static Stream<Arguments> provideDataForFormatValidationTest() {
        return Stream.of(
                Arguments.of(null, 1),
                Arguments.of("", 1),
                Arguments.of(" ", 1),
                Arguments.of("p", 0),
                Arguments.of("png", 0)
        );
    }

}
