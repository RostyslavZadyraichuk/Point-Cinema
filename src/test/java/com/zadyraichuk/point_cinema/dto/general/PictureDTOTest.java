package com.zadyraichuk.point_cinema.dto.general;

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

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Picture data transfer object class tests")
class PictureDTOTest {

    private static byte[] pictureData;
    private static String format;
    private static Validator validator;

    @BeforeAll
    static void setupValidator() {
        pictureData = new byte[]{1, 2, 3};
        format = "png";

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        factory.close();
    }

    @Test
    @DisplayName("Test all-args constructor")
    void testAllArgsConstructor() {
        byte[] pictureDataLocal = new byte[10];
        Arrays.fill(pictureDataLocal, (byte) 15);
        String formatLocal = "png";

        PictureDTO picture = new PictureDTO(pictureDataLocal, formatLocal);

        assertEquals(pictureDataLocal, picture.getPictureData(), "Picture data does not match the expected value");
        assertEquals(formatLocal, picture.getFormat(), "Format does not match the expected value");
    }

    @Test
    @DisplayName("Test getter methods for all fields")
    void testGetterMethods() {
        PictureDTO picture = new PictureDTO(pictureData, format);

        assertEquals(pictureData, picture.getPictureData(), "Getter for Picture data returned an unexpected value");
        assertEquals(format, picture.getFormat(), "Getter for Format returned an unexpected value");
    }

    @ParameterizedTest
    @MethodSource("provideDataForPictureDataValidationTest")
    @DisplayName("Test picture data field validation")
    void testPictureDataValidation(byte[] pictureData, int expectedViolations) {
        PictureDTO pictureDTO = new PictureDTO(pictureData, format);
        Set<ConstraintViolation<PictureDTO>> violations = validator.validate(pictureDTO);

        assertEquals(violations.size(), expectedViolations, "Wrong number of violations");
        if (expectedViolations != 0) {
            assertEquals("Picture data cannot be empty", violations.iterator().next().getMessage(),
                    "Wrong error message");
        }
    }

    @ParameterizedTest
    @MethodSource("provideDataForFormatValidationTest")
    @DisplayName("Test format field validation")
    void testFormatValidation(String format, int expectedViolations) {
        PictureDTO pictureDTO = new PictureDTO(pictureData, format);
        Set<ConstraintViolation<PictureDTO>> violations = validator.validate(pictureDTO);

        assertEquals(violations.size(), expectedViolations, "Wrong number of violations");
        if (expectedViolations != 0) {
            assertEquals("Format cannot be empty", violations.iterator().next().getMessage(),
                    "Wrong error message");
        }
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
