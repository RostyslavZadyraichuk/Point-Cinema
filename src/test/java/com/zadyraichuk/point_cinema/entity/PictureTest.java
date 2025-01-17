package com.zadyraichuk.point_cinema.entity;

import org.bson.types.Binary;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Picture entity class tests")
class PictureTest {

    private static String id;
    private static String format;
    private static Binary image;

    private Picture picture;

    @BeforeAll
    static void beforeAll() {
        PictureTest.id = "1";
        PictureTest.format = "jpeg";
        byte[] imageBytes = new byte[]{1, 2, 3, 4, 5};
        PictureTest.image = new Binary(imageBytes);
    }

    @BeforeEach
    void setUp() {
        picture = initPicture();
        picture.setId(PictureTest.id);
    }

    @Test
    @DisplayName("Test required-args constructor initializes fields correctly")
    void testRequiredArgsConstructor() {
        String formatLocal = "png";
        byte[] imageBytesLocal = new byte[10];
        Arrays.fill(imageBytesLocal, (byte) 15);
        Binary imageLocal = new Binary(imageBytesLocal);

        picture = new Picture(imageLocal, formatLocal);

        assertNull(picture.getId(), "Id should be null when using the required-args constructor");
        assertEquals(formatLocal, picture.getFormat(), "Format does not match the expected value");
        assertEquals(imageLocal, picture.getImage(), "Image does not match the expected value");
    }

    @Test
    @DisplayName("Test getter methods for all fields")
    void testGetterMethods() {
        assertEquals(PictureTest.id, picture.getId(), "Getter for Id returned an unexpected value");
        assertEquals(PictureTest.format, picture.getFormat(), "Getter for Format returned an unexpected value");
        assertEquals(PictureTest.image, picture.getImage(), "Getter for Image returned an unexpected value");
    }

    @Test
    @DisplayName("Test getId returns null for newly created Picture")
    void testGetId_whenNewCreated() {
        picture = initPicture();

        String actual = picture.getId();

        assertNull(actual, "Id should be null for a newly created Picture instance");
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "10", "100"})
    @DisplayName("Test setId method with multiple values")
    void testSetId(String id) {
        picture.setId(id);

        assertEquals(id, picture.getId(), "setId() failed to correctly set the expected Id");
    }

    private Picture initPicture() {
        return new Picture(PictureTest.image, PictureTest.format);
    }

}