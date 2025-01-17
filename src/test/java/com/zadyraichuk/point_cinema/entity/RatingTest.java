package com.zadyraichuk.point_cinema.entity;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("Rating entity class test")
class RatingTest {

    private static String id;
    private static Integer userRating;
    private static String userId;
    private static String movieId;

    private Rating rating;

    @BeforeAll
    static void beforeAll() {
        RatingTest.id = "1";
        RatingTest.userRating = 1;
        RatingTest.userId = "1";
        RatingTest.movieId = "1";
    }

    @BeforeEach
    void setUp() {
        rating = initRating();
        rating.setId(RatingTest.id);
    }

    @Test
    @DisplayName("Test required-args constructor initializes fields correctly")
    void testRequiredArgsConstructor() {
        Integer userRatingLocal = 100;
        String userIdLocal = "100";
        String movieIdLocal = "100";

        rating = new Rating(userRatingLocal, userIdLocal, movieIdLocal);

        assertNull(rating.getId(), "Id should be null when using the required-args constructor");
        assertEquals(userRatingLocal, rating.getUserRating(), "User rating does not match the expected value");
        assertEquals(userIdLocal, rating.getUserId(), "User id does not match the expected value");
        assertEquals(movieIdLocal, rating.getMovieId(), "Movie id does not match the expected value");
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "10", "100"})
    @DisplayName("Test setId method with multiple values")
    void testSetId(String id) {
        rating.setId(id);

        assertEquals(id, rating.getId(), "setId method failed to set the expected Id");
    }

    @Test
    @DisplayName("Test getId returns null for newly created Actor")
    void testGetId_whenNewCreated() {
        rating = initRating();

        assertNull(rating.getId(), "Newly created Rating should have null Id");
    }

    @Test
    @DisplayName("Test getter methods for all fields")
    void testGetterMethods() {
        assertEquals(RatingTest.id, rating.getId(), "Getter for id returned an unexpected value");
        assertEquals(RatingTest.userRating, rating.getUserRating(), "Getter for userRating returned an unexpected value");
        assertEquals(RatingTest.userId, rating.getUserId(), "Getter for userId returned an unexpected value");
        assertEquals(RatingTest.movieId, rating.getMovieId(), "Getter for movieId returned an unexpected value");
    }

    private Rating initRating() {
        return new Rating(
                RatingTest.userRating,
                RatingTest.userId,
                RatingTest.movieId
        );
    }

}