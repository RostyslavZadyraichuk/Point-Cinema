package com.zadyraichuk.point_cinema.entity;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Comment entity class tests")
class CommentTest {

    private static String id;
    private static LocalDateTime dateTime;
    private static String text;
    private static String userId;
    private static String movieId;
    private static String ratingId;

    private Comment comment;

    @BeforeAll
    static void beforeAll() {
        CommentTest.id = "1";
        CommentTest.dateTime = LocalDateTime.of(1, 1, 1, 1, 1);
        CommentTest.text = "This is a comment";
        CommentTest.userId = "1";
        CommentTest.movieId = "1";
        CommentTest.ratingId = "1";
    }

    @BeforeEach
    void setUp() {
        comment = initComment();
    }

    @Test
    @DisplayName("Test all-args constructor initializes fields correctly")
    void testAllArgsConstructor() {
        String idLocal = "100";
        LocalDateTime dateTimeLocal = LocalDateTime.of(100, 1, 1, 1, 1);
        String textLocal = "100";
        String userIdLocal = "100";
        String movieIdLocal = "100";
        String ratingIdLocal = "100";

        comment = new Comment(
                idLocal,
                dateTimeLocal,
                textLocal,
                userIdLocal,
                movieIdLocal,
                ratingIdLocal
        );

        assertEquals(idLocal, comment.getId(), "Id does not match the expected value");
        assertEquals(dateTimeLocal, comment.getDateTime(), "DateTime does not match the expected value");
        assertEquals(textLocal, comment.getText(), "Text does not match the expected value");
        assertEquals(userIdLocal, comment.getUserId(), "User id does not match the expected value");
        assertEquals(movieIdLocal, comment.getMovieId(), "Movie id does not match the expected value");
        assertEquals(ratingIdLocal, comment.getRatingId(), "Rating id does not match the expected value");
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "10", "100"})
    @DisplayName("Test setId method with multiple values")
    void testSetId(String id) {
        comment.setId(id);

        assertEquals(id, comment.getId(), "Id does not match the value set");
    }

    @Test
    @DisplayName("Test getter methods for all fields")
    void testGetterMethods() {
        assertEquals(CommentTest.id, comment.getId(), "Getter for id returned an unexpected value");
        assertEquals(CommentTest.dateTime, comment.getDateTime(), "Getter for DateTime returned an unexpected value");
        assertEquals(CommentTest.text, comment.getText(), "Getter for text returned an unexpected value");
        assertEquals(CommentTest.userId, comment.getUserId(), "Getter for userId returned an unexpected value");
        assertEquals(CommentTest.movieId, comment.getMovieId(), "Getter for movieId returned an unexpected value");
        assertEquals(CommentTest.ratingId, comment.getRatingId(), "Getter for ratingId returned an unexpected value");
    }

    private Comment initComment() {
        return Comment.builder()
                .id(CommentTest.id)
                .dateTime(CommentTest.dateTime)
                .text(CommentTest.text)
                .userId(CommentTest.userId)
                .movieId(CommentTest.movieId)
                .ratingId(CommentTest.ratingId)
                .build();
    }

    @Nested
    @DisplayName("CommentBuilder nested class tests")
    class CommentBuilderTest {

        @Test
        @DisplayName("Test builder initializes fields correctly")
        void testBuilderInitialization() {
            String idLocal = "1000";
            LocalDateTime dateTimeLocal = LocalDateTime.of(1000, 1, 1, 1, 1);
            String textLocal = "1000";
            String userIdLocal = "1000";
            String movieIdLocal = "1000";
            String ratingIdLocal = "1000";

            comment = Comment.builder()
                    .id(idLocal)
                    .dateTime(dateTimeLocal)
                    .text(textLocal)
                    .userId(userIdLocal)
                    .movieId(movieIdLocal)
                    .ratingId(ratingIdLocal)
                    .build();

            assertEquals(idLocal, comment.getId(), "Id does not match the expected value");
            assertEquals(dateTimeLocal, comment.getDateTime(), "DateTime does not match the expected value");
            assertEquals(textLocal, comment.getText(), "Text does not match the expected value");
            assertEquals(userIdLocal, comment.getUserId(), "User id does not match the expected value");
            assertEquals(movieIdLocal, comment.getMovieId(), "Movie id does not match the expected value");
            assertEquals(ratingIdLocal, comment.getRatingId(), "Rating id does not match the expected value");
        }

        @Test
        @DisplayName("Test builder defaults to fields if no values are added")
        void testBuilderDefaultFields() {
            comment = Comment.builder().build();
            LocalDateTime dateTimeExpected = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);

            assertEquals(dateTimeExpected, comment.getDateTime(), "DateTime does not match the expected value");
        }

    }

}