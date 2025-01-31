package com.zadyraichuk.point_cinema.repository;

import com.zadyraichuk.point_cinema.entity.Rating;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

//TODO make fail messages formatted for better clarity
@DisplayName("Rating repository tests")
@DataMongoTest
@ActiveProfiles("test")
class RatingRepositoryTest {

    @Autowired
    private RatingRepository ratingRepository;

    private static Rating rating;

    @BeforeEach
    void setUp() {
        rating = initRating();
        rating = ratingRepository.save(rating);
    }

    @AfterEach
    void afterEach() {
        ratingRepository.deleteAll();
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForCreateAndUpdateTests")
    @DisplayName("Test creating an Rating")
    void testCreate(String userId, String movieId, boolean shouldThrowException) {
        Rating ratingLocal = new Rating(1, userId, movieId);
        assertNull(ratingLocal.getId(), "The rating has just created should have no ID");

        if (shouldThrowException) {
            assertThrows(DuplicateKeyException.class, () -> ratingRepository.save(ratingLocal),
                    "Should have thrown an exception due to duplicate userId and movieId");
        } else {
            Rating savedRating = assertDoesNotThrow(() -> ratingRepository.save(ratingLocal));
            assertNotNull(savedRating.getId(), "The saved rating should have a generated ID");
            assertEquals(ratingLocal.getUserRating(), savedRating.getUserRating(), "The user rating should match");
            assertEquals(ratingLocal.getUserId(), savedRating.getUserId(), "The user ID should match");
            assertEquals(ratingLocal.getMovieId(), savedRating.getMovieId(), "The movie ID should match");
        }

    }

    @Test
    @DisplayName("Test finding an Rating by ID")
    void testFindById() {
        Optional<Rating> foundRatingOpt = ratingRepository.findById(rating.getId());
        assertTrue(foundRatingOpt.isPresent(), "The rating should be found by ID");

        Rating foundRating = foundRatingOpt.get();
        assertEquals(rating.getId(), foundRating.getId(), "The IDs should match");
        assertEquals(rating.getUserRating(), foundRating.getUserRating(), "The user rating should match");
        assertEquals(rating.getUserId(), foundRating.getUserId(), "The user ID should match");
        assertEquals(rating.getMovieId(), foundRating.getMovieId(), "The movie ID should match");
    }

    @Test
    @DisplayName("Test finding all Ratings")
    void testFindAll() {
        Rating[] ratings = getRatingsForGeneralCrudTests();
        ratingRepository.saveAll(Arrays.asList(ratings));
        int expectedLength = ratings.length + 1;
        List<Rating> foundRatings = ratingRepository.findAll();

        assertNotNull(foundRatings, "The found list should not be null");
        assertFalse(foundRatings.isEmpty(), "The found list should not be empty");
        assertEquals(expectedLength, foundRatings.size(), "The size of the list should match the number of ratings saved");

        for (Rating a : ratings) {
            assertTrue(foundRatings.contains(a), "The found ratings should contain all ratings saved before");
        }
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForCreateAndUpdateTests")
    @DisplayName("Test updating an Rating")
    void testUpdate(String userId, String movieId, boolean shouldThrowException) {
        Rating ratingBeforeUpdate = new Rating(1, "beforeUpdate", "beforeUpdate");
        ratingBeforeUpdate = ratingRepository.save(ratingBeforeUpdate);
        Rating updatedRating = new Rating(1, userId, movieId);
        updatedRating.setId(ratingBeforeUpdate.getId());

        if (shouldThrowException) {
            assertThrows(DuplicateKeyException.class, () -> ratingRepository.save(updatedRating),
                    "Should have thrown an exception due to duplicate userId and movieId");
        } else {
            rating = assertDoesNotThrow(() -> ratingRepository.save(updatedRating));
            assertEquals(updatedRating.getId(), rating.getId(), "The ID should remain the same after update");
            assertEquals(updatedRating.getUserRating(), rating.getUserRating(), "The user rating should match");
            assertEquals(updatedRating.getUserId(), rating.getUserId(), "The user ID should match");
            assertEquals(updatedRating.getMovieId(), rating.getMovieId(), "The movie ID should match");
        }
    }

    @Test
    @DisplayName("Test save all Ratings")
    void testSaveAll() {
        Rating[] ratings = getRatingsForGeneralCrudTests();

        List<Rating> savedRatings = ratingRepository.saveAll(Arrays.asList(ratings));

        assertNotNull(savedRatings, "The saved ratings list should not be null");
        assertEquals(ratings.length, savedRatings.size(), "The size of the saved ratings should match the input list size");
        for (Rating a : savedRatings) {
            assertNotNull(a.getId(), "Each saved rating should have a generated ID");
        }

        List<Rating> foundRatings = ratingRepository.findAll();
        int expectedSize = ratings.length + 1;
        assertEquals(expectedSize, foundRatings.size(), "The number of ratings found should match the saved ratings");
        assertTrue(foundRatings.containsAll(savedRatings), "The found ratings should match the saved ratings");
    }

    @Test
    @DisplayName("Test deleting an Rating")
    void testDelete() {
        ratingRepository.delete(rating);

        Optional<Rating> deletedRatingOpt = ratingRepository.findById(rating.getId());
        assertFalse(deletedRatingOpt.isPresent(), "The rating should be deleted and not found by ID");
    }

    @Test
    @DisplayName("Test deleting an Rating by ID")
    void testDeleteById() {
        ratingRepository.deleteById(rating.getId());

        Optional<Rating> deletedRatingOpt = ratingRepository.findById(rating.getId());
        assertFalse(deletedRatingOpt.isPresent(), "The rating should be deleted and not found by ID");
    }

    @Test
    @DisplayName("Test deleting all Ratings")
    void testDeleteAll() {
        Rating[] ratings = getRatingsForGeneralCrudTests();
        ratingRepository.saveAll(Arrays.asList(ratings));

        List<Rating> foundRatings = ratingRepository.findAll();
        assertNotNull(foundRatings, "The ratings should exist before deleting");
        assertFalse(foundRatings.isEmpty(), "The ratings should exist before deleting");

        ratingRepository.deleteAll();
        foundRatings = ratingRepository.findAll();
        assertNotNull(foundRatings, "The ratings should be deleted and ratings list should not be null");
        assertTrue(foundRatings.isEmpty(), "The ratings should be deleted and ratings list should be empty");
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForFindAndDeleteByUserId")
    @DisplayName("Test finding an Rating by User Id")
    void testFindByUserId(String userId,
                          int expectedSize,
                          int[] expectedRatingIndexes) {
        Rating[] ratings = getRatingsForSpecializedTests();
        ratingRepository.saveAll(Arrays.asList(ratings));

        Pageable pageable = PageRequest.of(0, 10);
        Page<Rating> foundRatings = ratingRepository.findByUserId(userId, pageable);
        List<Rating> foundContent = foundRatings.getContent();

        assertNotNull(foundRatings, "The result should not be null");
        assertEquals(expectedSize, foundContent.size(),
                String.format("The result should contain %d ratings with userId '%s'", expectedSize, userId));
        for (int index : expectedRatingIndexes) {
            assertTrue(foundContent.contains(ratings[index]), "Rating should be in the result");
        }
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForFindAndDeleteByMovieId")
    @DisplayName("Test finding an Rating by Movie Id")
    void testFindByMovieId(String movieId,
                           int expectedSize,
                           int[] expectedRatingIndexes) {
        Rating[] ratings = getRatingsForSpecializedTests();
        ratingRepository.saveAll(Arrays.asList(ratings));

        Pageable pageable = PageRequest.of(0, 10);
        Page<Rating> foundRatings = ratingRepository.findByMovieId(movieId, pageable);
        List<Rating> foundContent = foundRatings.getContent();

        assertNotNull(foundRatings, "The result should not be null");
        assertEquals(expectedSize, foundContent.size(),
                String.format("The result should contain %d ratings with movieId '%s'", expectedSize, movieId));
        for (int index : expectedRatingIndexes) {
            assertTrue(foundContent.contains(ratings[index]), "Rating should be in the result");
        }
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForFindByUserIdAndMovieId")
    @DisplayName("Test finding an Rating by User Id and Movie Id")
    void testFindByUserIdAndMovieId(String userId,
                                    String movieId,
                                    boolean shouldExist,
                                    int objectPosition) {
        Rating[] ratings = getRatingsForSpecializedTests();
        ratingRepository.saveAll(Arrays.asList(ratings));

        Optional<Rating> foundRatingOpt = ratingRepository.findByUserIdAndMovieId(userId, movieId);

        assertNotNull(foundRatingOpt, "The rating should not be null");
        if (shouldExist) {
            assertTrue(foundRatingOpt.isPresent(), "The rating should exist");
            Rating foundRating = foundRatingOpt.get();
            assertEquals(ratings[objectPosition].getId(), foundRating.getId(), "The IDs should match");
            assertEquals(ratings[objectPosition].getUserRating(), foundRating.getUserRating(), "The user rating should match");
            assertEquals(ratings[objectPosition].getMovieId(), foundRating.getMovieId(), "The movie ID should match");
            assertEquals(ratings[objectPosition].getUserId(), foundRating.getUserId(), "The user ID should match");
        } else {
            assertTrue(foundRatingOpt.isEmpty(), "The rating should not exist");
        }
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForFindAndDeleteByUserId")
    @DisplayName("Test deleting Ratings by User Id")
    void testDeleteByUserId(String userId,
                            int expectedRemovedSize,
                            int[] expectedRemovedRatingIndexes) {
        Rating[] ratings = getRatingsForSpecializedTests();
        ratingRepository.saveAll(Arrays.asList(ratings));

        ratingRepository.deleteByUserId(userId);
        List<Rating> foundRatings = ratingRepository.findAll();

        assertNotNull(foundRatings, "The result should not be null");
        assertEquals(expectedRemovedSize, ratings.length - foundRatings.size() + 1,
                String.format("The result should contain %d ratings with userId '%s'", expectedRemovedSize, userId));
        for (int index : expectedRemovedRatingIndexes) {
            assertFalse(foundRatings.contains(ratings[index]), "Rating should not be in the result");
        }

        Pageable pageable = PageRequest.of(0, 10);
        Page<Rating> foundRatingsByUser = ratingRepository.findByUserId(userId, pageable);
        assertTrue(foundRatingsByUser.isEmpty(), "The result should be empty");
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForFindAndDeleteByMovieId")
    @DisplayName("Test deleting Ratings by Movie Id")
    void testDeleteByMovieId(String movieId,
                             int expectedRemovedSize,
                             int[] expectedRemovedRatingIndexes) {
        Rating[] ratings = getRatingsForSpecializedTests();
        ratingRepository.saveAll(Arrays.asList(ratings));

        ratingRepository.deleteByMovieId(movieId);
        List<Rating> foundRatings = ratingRepository.findAll();

        assertNotNull(foundRatings, "The result should not be null");
        assertEquals(expectedRemovedSize, ratings.length - foundRatings.size() + 1,
                String.format("The result should contain %d ratings with movieId '%s'", expectedRemovedSize, movieId));
        for (int index : expectedRemovedRatingIndexes) {
            assertFalse(foundRatings.contains(ratings[index]), "Rating should not be in the result");
        }

        Pageable pageable = PageRequest.of(0, 10);
        Page<Rating> foundRatingsByMovie = ratingRepository.findByMovieId(movieId, pageable);
        assertTrue(foundRatingsByMovie.isEmpty(), "The result should be empty");
    }

    /**
     * Provides arguments for testing the testCreateAndUpdate method in {@link RatingRepository}.
     * The arguments are:
     * <ul>
     *     <li>userId - string with user identifier</li>
     *     <li>movieId - string with movie identifier</li>
     *     <li>shouldThrowException - whether an exception should be thrown due to duplicate userId and movieId</li>
     * </ul>
     *
     * @return a stream of arguments for parameterized tests
     */
    private static Stream<Arguments> provideArgumentsForCreateAndUpdateTests() {
        return Stream.of(
                Arguments.of("Unique", "Unique", false),
                Arguments.of(rating.getUserId(), "Unique", false),
                Arguments.of("Unique", rating.getMovieId(), false),
                Arguments.of(rating.getUserId(), rating.getMovieId(), true)
        );
    }

    /**
     * Provides arguments for testing the testFindByUserId and testDeleteByUserId methods in {@link RatingRepository}.
     * The arguments are:
     * <ul>
     *     <li>userId - string with user identifier</li>
     *     <li>expectedSize - the expected number of ratings matching the user identifier</li>
     *     <li>expectedRatingIndexes - the indexes of expected ratings in the test data</li>
     * </ul>
     *
     * @return a stream of arguments for parameterized tests
     */
    private static Stream<Arguments> provideArgumentsForFindAndDeleteByUserId() {
        return Stream.of(
                Arguments.of("1", 2, new int[]{0, 1}),
                Arguments.of("2", 1, new int[]{2})
        );
    }

    /**
     * Provides arguments for testing the testFindByMovieId and testDeleteByMovieId methods in {@link RatingRepository}.
     * The arguments are:
     * <ul>
     *     <li>movieId - string with movie identifier</li>
     *     <li>expectedSize - the expected number of ratings matching the movie identifier</li>
     *     <li>expectedRatingIndexes - the indexes of expected ratings in the test data</li>
     * </ul>
     *
     * @return a stream of arguments for parameterized tests
     */
    private static Stream<Arguments> provideArgumentsForFindAndDeleteByMovieId() {
        return Stream.of(
                Arguments.of("1", 1, new int[]{0}),
                Arguments.of("2", 2, new int[]{1, 3})
        );
    }

    /**
     * Provides arguments for testing the testFindByUserIdAndMovieId method in {@link RatingRepository}.
     * The arguments are:
     * <ul>
     *     <li>userId - string with user identifier</li>
     *     <li>movieId - string with movie identifier</li>
     *     <li>isPresent - whether the rating with the given identifiers is present in the test data</li>
     *     <li>expectedRatingIndex - the index of the expected rating in the test data</li>
     * </ul>
     *
     * @return a stream of arguments for parameterized tests
     */
    private static Stream<Arguments> provideArgumentsForFindByUserIdAndMovieId() {
        return Stream.of(
                Arguments.of("1", "1", true, 0),
                Arguments.of("1", "2", true, 1),
                Arguments.of("2", "2", false, 0),
                Arguments.of("2", "3", true, 2)
        );
    }

    private Rating initRating() {
        return new Rating(1, "test", "test");
    }

    private Rating[] getRatingsForGeneralCrudTests() {
        Rating unique1 = new Rating(1, "Unique", "Unique");
        Rating unique2 = new Rating(1, rating.getUserId(), "Unique");
        Rating unique3 = new Rating(1, "Unique", rating.getMovieId());

        return new Rating[]{unique1, unique2, unique3};
    }

    private Rating[] getRatingsForSpecializedTests() {
        Rating rating1 = new Rating(1, "1", "1");
        Rating rating2 = new Rating(1, "1", "2");
        Rating rating3 = new Rating(1, "2", "3");
        Rating rating4 = new Rating(1, "3", "2");

        return new Rating[]{rating1, rating2, rating3, rating4};
    }

}