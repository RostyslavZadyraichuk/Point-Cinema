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

@DisplayName("Picture entity test")
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
    void testRequiredArgsConstructor() {
        String formatLocal = "png";
        byte[] imageBytesLocal = new byte[10];
        Arrays.fill(imageBytesLocal, (byte) 15);
        Binary imageLocal = new Binary(imageBytesLocal);

        picture = new Picture(imageLocal, formatLocal);

        assertNull(picture.getId());
        assertEquals(formatLocal, picture.getFormat());
        assertEquals(imageLocal, picture.getImage());
    }

    @Test
    void testGetterMethods() {
        assertEquals(PictureTest.id, picture.getId());
        assertEquals(PictureTest.format, picture.getFormat());
        assertEquals(PictureTest.image, picture.getImage());
    }

    @Test
    @DisplayName("Check ID is null when a new Picture is created")
    void testGetId_whenNewCreated() {
        picture = initPicture();

        String actual = picture.getId();

        assertNull(actual);
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "10", "100"})
    @DisplayName("Check setId() correctly assigns the ID")
    void testSetId(String id) {
        picture.setId(id);

        assertEquals(id, picture.getId());
    }

    private Picture initPicture() {
        return new Picture(PictureTest.image, PictureTest.format);
    }

}