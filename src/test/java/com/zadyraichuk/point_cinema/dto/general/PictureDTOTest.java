package com.zadyraichuk.point_cinema.dto.general;

import com.zadyraichuk.point_cinema.dto.ValidationTestUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Picture data transfer object class tests")
class PictureDTOTest {

    private static byte[] pictureData;
    private static String format;

    @BeforeAll
    static void setupClass() {
        pictureData = new byte[]{1, 2, 3};
        format = "png";
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

    @DisplayName("Test equals and hashCode methods")
    @ParameterizedTest
    @MethodSource("provideDataForEqualsAndHashCodeTest")
    void testEqualsAndHashCode(PictureDTO picture1, PictureDTO picture2, boolean expectedResult) {
        assertEquals(expectedResult, picture1.equals(picture2), "Equals method returned false");
        assertEquals(expectedResult, picture1.hashCode() == picture2.hashCode(),
                "hashCode method returned different values");
    }

    @ParameterizedTest
    @MethodSource("provideDataForPictureDataValidationTest")
    @DisplayName("Test picture data field validation")
    void testPictureDataValidation(byte[] pictureDataLocal, boolean isValid) {
        PictureDTO pictureDTO = new PictureDTO(pictureDataLocal, format);
        ValidationTestUtils.validate(pictureDTO, isValid);
    }

    @ParameterizedTest
    @MethodSource("provideDataForFormatValidationTest")
    @DisplayName("Test format field validation")
    void testFormatValidation(String formatLocal, boolean isValid) {
        PictureDTO pictureDTO = new PictureDTO(pictureData, formatLocal);
        ValidationTestUtils.validate(pictureDTO, isValid);
    }

    /**
     * Provides arguments for testing the testEqualsAndHashCode.
     * The arguments are:
     * <ul>
     *     <li>picture1 - the first picture</li>
     *     <li>picture2 - the second picture</li>
     *     <li>expectedResult - the expected result of equals method</li>
     * </ul>
     *
     * @return a stream of arguments for parameterized tests
     */
    private static Stream<Arguments> provideDataForEqualsAndHashCodeTest() {
        PictureDTO picture1 = new PictureDTO(pictureData, format);
        PictureDTO picture2 = new PictureDTO(pictureData, format);
        PictureDTO picture3 = new PictureDTO(pictureData, "equalsHashCode");
        PictureDTO picture4 = new PictureDTO(new byte[]{1, 1}, format);

        return Stream.of(
                Arguments.of(picture1, picture2, true),
                Arguments.of(picture2, picture1, true),
                Arguments.of(picture1, picture3, false),
                Arguments.of(picture1, picture4, false)
        );
    }

    /**
     * Provides arguments for testing the testPictureDataValidation.
     * The arguments are:
     * <ul>
     *     <li>pictureData - picture data</li>
     *     <li>valid - is the picture data valid or not</li>
     * </ul>
     *
     * @return a stream of arguments for parameterized tests
     */
    private static Stream<Arguments> provideDataForPictureDataValidationTest() {
        return Stream.of(
                Arguments.of(null, false),
                Arguments.of(new byte[0], false),
                Arguments.of(new byte[]{1, 2, 3}, true)
        );
    }

    /**
     * Provides arguments for testing the testFormatValidation.
     * The arguments are:
     * <ul>
     *     <li>format - string with picture format</li>
     *     <li>valid - is the format valid or not</li>
     * </ul>
     *
     * @return a stream of arguments for parameterized tests
     */
    private static Stream<Arguments> provideDataForFormatValidationTest() {
        return ValidationTestUtils.forNotBlankValidation();
    }

}
