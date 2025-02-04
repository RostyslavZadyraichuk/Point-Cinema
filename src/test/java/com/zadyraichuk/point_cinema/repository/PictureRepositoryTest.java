package com.zadyraichuk.point_cinema.repository;

import com.zadyraichuk.point_cinema.entity.Picture;
import org.bson.types.Binary;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

//TODO make fail messages formatted for better clarity
@DisplayName("Picture repository tests")
@DataMongoTest
@ActiveProfiles("test")
class PictureRepositoryTest {

    @Autowired
    private PictureRepository pictureRepository;

    private static Picture picture;

    @BeforeEach
    void setUp() {
        picture = initPicture();
        picture = pictureRepository.save(picture);
    }

    @AfterEach
    void afterEach() {
        pictureRepository.deleteAll();
    }

    @Test
    @DisplayName("Test creating an Picture")
    void testCreate() {
        Binary binaryLocal = new Binary("created".getBytes());
        Picture pictureLocal = new Picture(binaryLocal, "jpg");
        assertNull(pictureLocal.getId(), "The picture has just created should have no ID");

        Picture savedPicture = assertDoesNotThrow(() -> pictureRepository.save(pictureLocal));
        assertNotNull(savedPicture.getId(), "The saved picture should have a generated ID");
        assertEquals(pictureLocal.getImage(), savedPicture.getImage(), "The image should match");
        assertEquals(pictureLocal.getFormat(), savedPicture.getFormat(), "The format should match");
    }

    @Test
    @DisplayName("Test finding an Picture by ID")
    void testFindById() {
        Optional<Picture> foundPictureOpt = pictureRepository.findById(picture.getId());
        assertTrue(foundPictureOpt.isPresent(), "The picture should be found by ID");

        Picture foundPicture = foundPictureOpt.get();
        assertEquals(picture.getId(), foundPicture.getId(), "The IDs should match");
        assertEquals(picture.getImage(), foundPicture.getImage(), "The image should match");
        assertEquals(picture.getFormat(), foundPicture.getFormat(), "The format should match");
    }

    @Test
    @DisplayName("Test finding all Pictures")
    void testFindAll() {
        Picture[] pictures = getPicturesForGeneralCrudTests();
        pictureRepository.saveAll(Arrays.asList(pictures));
        int expectedLength = pictures.length + 1;
        List<Picture> foundPictures = pictureRepository.findAll();

        assertNotNull(foundPictures, "The found list should not be null");
        assertFalse(foundPictures.isEmpty(), "The found list should not be empty");
        assertEquals(expectedLength, foundPictures.size(), "The size of the list should match the number of pictures saved");
        assertTrue(foundPictures.containsAll(Arrays.asList(pictures)), "The found pictures should contain all pictures saved before");
    }

    @Test
    @DisplayName("Test updating an Picture")
    void testUpdate() {
        Picture pictureBeforeUpdate = new Picture(new Binary("beforeUpdate".getBytes()), "jpg");
        pictureBeforeUpdate = pictureRepository.save(pictureBeforeUpdate);
        Picture updatedPicture = new Picture(new Binary("updated".getBytes()), "jpg");
        updatedPicture.setId(pictureBeforeUpdate.getId());

        picture = assertDoesNotThrow(() -> pictureRepository.save(updatedPicture));
        assertEquals(updatedPicture.getId(), picture.getId(), "The ID should remain the same after update");
        assertEquals(updatedPicture.getImage(), picture.getImage(), "The image should match");
        assertEquals(updatedPicture.getFormat(), picture.getFormat(), "The format should match");
    }

    @Test
    @DisplayName("Test save all Pictures")
    void testSaveAll() {
        Picture[] pictures = getPicturesForGeneralCrudTests();

        List<Picture> savedPictures = pictureRepository.saveAll(Arrays.asList(pictures));

        assertNotNull(savedPictures, "The saved pictures list should not be null");
        assertEquals(pictures.length, savedPictures.size(), "The size of the saved pictures should match the input list size");
        Stream<String> pictureIds = savedPictures.stream().map(Picture::getId);
        assertTrue(pictureIds.allMatch(Objects::nonNull), "Each saved picture should have a generated ID");

        List<Picture> foundPictures = pictureRepository.findAll();
        int expectedSize = pictures.length + 1;
        assertEquals(expectedSize, foundPictures.size(), "The number of pictures found should match the saved pictures");
        assertTrue(foundPictures.containsAll(savedPictures), "The found pictures should match the saved pictures");
    }

    @Test
    @DisplayName("Test deleting an Picture")
    void testDelete() {
        pictureRepository.delete(picture);

        Optional<Picture> deletedPictureOpt = pictureRepository.findById(picture.getId());
        assertFalse(deletedPictureOpt.isPresent(), "The picture should be deleted and not found by ID");
    }

    @Test
    @DisplayName("Test deleting an Picture by ID")
    void testDeleteById() {
        pictureRepository.deleteById(picture.getId());

        Optional<Picture> deletedPictureOpt = pictureRepository.findById(picture.getId());
        assertFalse(deletedPictureOpt.isPresent(), "The picture should be deleted and not found by ID");
    }

    @Test
    @DisplayName("Test deleting all Pictures")
    void testDeleteAll() {
        Picture[] pictures = getPicturesForGeneralCrudTests();
        pictureRepository.saveAll(Arrays.asList(pictures));

        List<Picture> foundPictures = pictureRepository.findAll();
        assertNotNull(foundPictures, "The pictures should exist before deleting");
        assertFalse(foundPictures.isEmpty(), "The pictures should exist before deleting");

        pictureRepository.deleteAll();
        foundPictures = pictureRepository.findAll();
        assertNotNull(foundPictures, "The pictures should be deleted and pictures list should not be null");
        assertTrue(foundPictures.isEmpty(), "The pictures should be deleted and pictures list should be empty");
    }

    private Picture initPicture() {
        return new Picture(new Binary("test".getBytes()), "jpg");
    }

    private Picture[] getPicturesForGeneralCrudTests() {
        Picture unique1 = initPicture();
        Picture unique2 = initPicture();
        Picture unique3 = initPicture();

        return new Picture[]{unique1, unique2, unique3};
    }

}