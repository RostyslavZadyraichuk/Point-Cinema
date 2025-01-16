package com.zadyraichuk.point_cinema.entity;

import org.bson.types.Binary;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Picture entity test")
class PictureTest {

    private static String format;
    private static Binary image;

    private Picture picture;

    @BeforeAll
    static void beforeAll() {
        PictureTest.format = "jpeg";
        byte[] imageBytes = new byte[]{1, 2, 3, 4, 5};
        PictureTest.image = new Binary(imageBytes);
    }

    @BeforeEach
    void setUp() {
        picture = new Picture(PictureTest.image, PictureTest.format);
    }

    @Test
    @DisplayName("Check ID is null when a new Picture is created")
    void getId_whenNewPicture() {
        String actual = picture.getId();

        assertNull(actual);
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "10", "100"})
    @DisplayName("Check getId() returns correct ID after being set")
    void getId_whenSetBefore(String id) {
        picture.setId(id);

        String actual = picture.getId();
        assertEquals(id, actual);
    }

    @Test
    @DisplayName("Check getPicture() returns the correct Binary object")
    void getPicture_whenSetBefore() {
        Binary expected = PictureTest.image;

        Binary actual = picture.getPicture();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Check getFormat() returns the correct format")
    void getFormat_whenSetBefore() {
        String expected = PictureTest.format;

        String actual = picture.getFormat();
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "10", "100"})
    @DisplayName("Check setId() correctly assigns the ID")
    void setId_whenValid(String id) {
        picture.setId(id);

        assertEquals(id, picture.getId());
    }
}