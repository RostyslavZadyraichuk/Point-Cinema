package com.zadyraichuk.point_cinema.dto.identified;

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

@DisplayName("Identified actor data transfer object class tests")
class IdentifiedPictureDTOTest {

    private static String id;
    private static byte[] pictureData;
    private static String format;

    @BeforeAll
    static void setupClass() {
        id = "1";
        pictureData = new byte[]{1, 2, 3};
        format = "png";
    }

    @Test
    @DisplayName("Test all-args constructor")
    void testAllArgsConstructor() {
        String idLocal = "10";
        byte[] pictureDataLocal = new byte[10];
        Arrays.fill(pictureDataLocal, (byte) 15);
        String formatLocal = "png";

        IdentifiedPictureDTO picture = new IdentifiedPictureDTO(idLocal, pictureDataLocal, formatLocal);

        assertEquals(idLocal, picture.getId(), "Id does not match the expected value");
        assertEquals(pictureDataLocal, picture.getPictureData(), "Picture data does not match the expected value");
        assertEquals(formatLocal, picture.getFormat(), "Format does not match the expected value");
    }

    @Test
    @DisplayName("Test getter methods for all fields")
    void testGetterMethods() {
        IdentifiedPictureDTO picture = new IdentifiedPictureDTO(id, pictureData, format);

        assertEquals(id, picture.getId(), "Getter for Id returned an unexpected value");
        assertEquals(pictureData, picture.getPictureData(), "Getter for Picture data returned an unexpected value");
        assertEquals(format, picture.getFormat(), "Getter for Format returned an unexpected value");
    }

    @DisplayName("Test equals and hashCode methods")
    @ParameterizedTest
    @MethodSource("provideDataForEqualsAndHashCodeTest")
    void testEqualsAndHashCode(IdentifiedPictureDTO picture1, IdentifiedPictureDTO picture2, boolean expectedResult) {
        assertEquals(expectedResult, picture1.equals(picture2), "Equals method returned false");
        assertEquals(expectedResult, picture1.hashCode() == picture2.hashCode(),
                "hashCode method returned different values");
    }

    @ParameterizedTest
    @MethodSource("provideDataForIdValidationTest")
    @DisplayName("Test id field validation")
    void testIdValidation(String id, boolean isValid) {
        IdentifiedPictureDTO pictureDTO = new IdentifiedPictureDTO(id, new byte[]{1, 2, 3}, "png");
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
        IdentifiedPictureDTO picture1 = new IdentifiedPictureDTO(id, pictureData, format);
        IdentifiedPictureDTO picture2 = new IdentifiedPictureDTO(id, pictureData, format);
        IdentifiedPictureDTO picture3 = new IdentifiedPictureDTO(id, new byte[]{1, 1}, "equalsHashCode");

        return Stream.of(
                Arguments.of(picture1, picture2, true),
                Arguments.of(picture2, picture1, true),
                Arguments.of(picture1, picture3, true)
        );
    }

    /**
     * Provides arguments for testing the testIdValidation.
     * The arguments are:
     * <ul>
     *     <li>id - string with picture identifier</li>
     *     <li>valid - is the id valid or not</li>
     * </ul>
     *
     * @return a stream of arguments for parameterized tests
     */
    private static Stream<Arguments> provideDataForIdValidationTest() {
        return ValidationTestUtils.forNotBlankValidation();
    }

}
