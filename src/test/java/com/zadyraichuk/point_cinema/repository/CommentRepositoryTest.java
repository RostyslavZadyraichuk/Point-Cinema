package com.zadyraichuk.point_cinema.repository;

import com.zadyraichuk.point_cinema.entity.Cinema;
import com.zadyraichuk.point_cinema.entity.Comment;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Comment repository tests")
@DataMongoTest
@ActiveProfiles("test")
class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    private Comment comment;

    @BeforeEach
    void setUp() {
        comment = initComment();
        comment = commentRepository.save(comment);
    }

    @AfterEach
    void afterEach() {
        commentRepository.deleteAll();
    }

    @Test
    @DisplayName("Test creating a Comment")
    void testCreate() {
        Comment commentLocal = new Comment(null, LocalDateTime.now(), "Created", "Created", "Created", "Created");
        assertNull(commentLocal.getId(), "The comment has just created should have no ID");

        Comment savedComment = commentRepository.save(commentLocal);
        assertNotNull(savedComment.getId(), "The saved comment should have a generated ID");
        assertEquals(commentLocal.getDateTime(), savedComment.getDateTime(), "The comment date and time should match");
        assertEquals(commentLocal.getText(), savedComment.getText(), "The comment text should match");
        assertEquals(commentLocal.getMovieId(), savedComment.getMovieId(), "The comment movie ID should match");
        assertEquals(commentLocal.getUserId(), savedComment.getUserId(), "The comment user ID should match");
        assertEquals(commentLocal.getRatingId(), savedComment.getRatingId(), "The comment rating ID should match");
    }

    @Test
    @DisplayName("Test finding a Comment by ID")
    void testFindById() {
        Optional<Comment> foundCommentOpt = commentRepository.findById(comment.getId());
        assertTrue(foundCommentOpt.isPresent(), "The comment should be found by ID");

        Comment foundComment = foundCommentOpt.get();
        assertEquals(comment.getId(), foundComment.getId(), "The IDs should match");
        assertEquals(comment.getDateTime().truncatedTo(ChronoUnit.SECONDS),
                foundComment.getDateTime().truncatedTo(ChronoUnit.SECONDS),
                "The comment date and time should match");
        assertEquals(comment.getText(), foundComment.getText(), "The comment text should match");
        assertEquals(comment.getMovieId(), foundComment.getMovieId(), "The comment movie ID should match");
        assertEquals(comment.getUserId(), foundComment.getUserId(), "The comment user ID should match");
        assertEquals(comment.getRatingId(), foundComment.getRatingId(), "The comment rating ID should match");
    }

    @Test
    @DisplayName("Test finding all Comments")
    void testFindAll() {
        Comment[] comments = getCommentsForGeneralCrudTests();
        commentRepository.saveAll(Arrays.asList(comments));
        int expectedSize = comments.length + 1;

        List<Comment> foundComments = commentRepository.findAll();
        assertNotNull(foundComments, "The found list should not be null");
        assertFalse(foundComments.isEmpty(), "The found list should not be empty");
        assertEquals(expectedSize, foundComments.size(), "The size of the list should match the number of comments saved");

        for (Comment c : comments) {
            assertTrue(foundComments.contains(c), "The found comments should contain all saved comments");
        }
    }

    @Test
    @DisplayName("Test updating a Comment")
    void testUpdate() {
        Comment updatedComment = new Comment(comment.getId(),
                LocalDateTime.now(),
                "UpdatedText",
                "UpdatedUser",
                "UpdatedMovie",
                "UpdatedRating");
        comment = commentRepository.save(updatedComment);

        assertEquals(updatedComment.getId(), comment.getId(), "The ID should remain the same after update");
        assertEquals(updatedComment.getDateTime().truncatedTo(ChronoUnit.SECONDS),
                comment.getDateTime().truncatedTo(ChronoUnit.SECONDS),
                "The date and time should be updated");
        assertEquals(updatedComment.getText(), comment.getText(), "The text should be updated");
        assertEquals(updatedComment.getMovieId(), comment.getMovieId(), "The movie ID should be updated");
        assertEquals(updatedComment.getUserId(), comment.getUserId(), "The user ID should be updated");
        assertEquals(updatedComment.getRatingId(), comment.getRatingId(), "The rating ID should be updated");
    }

    @Test
    @DisplayName("Test save all Comments")
    void testSaveAll() {
        Comment[] comments = getCommentsForGeneralCrudTests();

        List<Comment> savedComments = commentRepository.saveAll(Arrays.asList(comments));

        assertNotNull(savedComments, "The saved comments list should not be null");
        assertEquals(comments.length, savedComments.size(), "The size of the saved comments should match the input list size");
        for (Comment c : savedComments) {
            assertNotNull(c.getId(), "Each saved comment should have a generated ID");
        }

        List<Comment> foundComments = commentRepository.findAll();
        int expectedSize = comments.length + 1;
        assertEquals(expectedSize, foundComments.size(), "The number of comments found should match the saved comments");
        assertTrue(foundComments.containsAll(savedComments), "The found comments should match the saved comments");
    }

    @Test
    @DisplayName("Test deleting a Comment")
    void testDelete() {
        commentRepository.delete(comment);

        Optional<Comment> deletedCommentOpt = commentRepository.findById(comment.getId());
        assertFalse(deletedCommentOpt.isPresent(), "The comment should be deleted and not found by ID");
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "2"})
    @DisplayName("Test deleting all comments by user ID")
    void testDeleteByUserId(String userId) {
        Comment[] comments = getCommentsForSpecializedTests();
        commentRepository.saveAll(Arrays.asList(comments));

        List<Comment> foundComments = commentRepository.findByUserId(userId);
        assertFalse(foundComments.isEmpty(), "The comments should exist before deleting");

        commentRepository.deleteByUserId(userId);
        foundComments = commentRepository.findByUserId(userId);
        assertTrue(foundComments.isEmpty(), "The comments should not exist after deleting");
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "2"})
    @DisplayName("Test deleting all comments by movie ID")
    void testDeleteByMovieId(String movieId) {
        Comment[] comments = getCommentsForSpecializedTests();
        commentRepository.saveAll(Arrays.asList(comments));

        List<Comment> foundComments = commentRepository.findByMovieId(movieId);
        assertFalse(foundComments.isEmpty(), "The comments should exist before deleting");

        commentRepository.deleteByMovieId(movieId);
        foundComments = commentRepository.findByMovieId(movieId);
        assertTrue(foundComments.isEmpty(), "The comments should not exist after deleting");
    }

    @Test
    @DisplayName("Test deleting all Comments")
    void testDeleteAll() {
        Comment[] comments = getCommentsForGeneralCrudTests();
        commentRepository.saveAll(Arrays.asList(comments));

        List<Comment> foundComments = commentRepository.findAll();
        assertNotNull(foundComments, "The comments should exist before deleting");
        assertFalse(foundComments.isEmpty(), "The comments should exist before deleting");

        commentRepository.deleteAll();
        foundComments = commentRepository.findAll();
        assertNotNull(foundComments, "The comments should be deleted and comments list should not be null");
        assertTrue(foundComments.isEmpty(), "The comments should be deleted and comments list should be empty");
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', textBlock = """
                1 | 1 | 0
                2 | 2 | 1,3
                3 | 1 | 2
            """)
    @DisplayName("Test finding a Comment by Movie ID")
    void testFindByMovieId(String movieId,
                           int expectedSize,
                           String expectedCommentsIndexesArray) {
        Comment[] comments = getCommentsForSpecializedTests();
        commentRepository.saveAll(Arrays.asList(comments));

        List<Comment> foundComments = commentRepository.findByMovieId(movieId);
        Page<Comment> foundCommentsPage = commentRepository.findByMovieId(movieId, Pageable.ofSize(10).first());
        int[] expectedCommentsIndexes = Arrays.stream(expectedCommentsIndexesArray.split(","))
                .mapToInt(Integer::parseInt)
                .toArray();

        assertNotNull(foundComments, "The result should not be null");
        assertNotNull(foundCommentsPage, "The result should not be null");
        assertEquals(expectedSize, foundComments.size(),
                String.format("The result should contain %d comments with movieId '%s'", expectedSize, movieId));
        assertEquals(expectedSize, foundCommentsPage.getTotalElements(),
                String.format("The result should contain %d comments with movieId '%s'", expectedSize, movieId));
        assertEquals(1, foundCommentsPage.getTotalPages(), "The result should contain 1 page");

        for (int i : expectedCommentsIndexes) {
            assertTrue(foundComments.contains(comments[i]), "Comment should be in the result");
            assertTrue(foundCommentsPage.getContent().contains(comments[i]), "Comment should be in the result");
        }
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', textBlock = """
                1 | 3 | 0,2,3
                2 | 1 | 1
            """)
    @DisplayName("Test finding a Comment by User ID")
    void testFindByUserId(String userId,
                          int expectedSize,
                          String expectedCommentsIndexesArray) {
        Comment[] comments = getCommentsForSpecializedTests();
        commentRepository.saveAll(Arrays.asList(comments));

        List<Comment> foundComments = commentRepository.findByUserId(userId);
        Page<Comment> foundCommentsPage = commentRepository.findByUserId(userId, Pageable.ofSize(10).first());
        int[] expectedCommentsIndexes = Arrays.stream(expectedCommentsIndexesArray.split(","))
                .mapToInt(Integer::parseInt)
                .toArray();

        assertNotNull(foundComments, "The result should not be null");
        assertNotNull(foundCommentsPage, "The result should not be null");
        assertEquals(expectedSize, foundComments.size(),
                String.format("The result should contain %d comments with userId '%s'", expectedSize, userId));
        assertEquals(expectedSize, foundCommentsPage.getTotalElements(),
                String.format("The result should contain %d comments with userId '%s'", expectedSize, userId));
        assertEquals(1, foundCommentsPage.getTotalPages(), "The result should contain 1 page");

        for (int i : expectedCommentsIndexes) {
            assertTrue(foundComments.contains(comments[i]), "Comment should be in the result");
            assertTrue(foundCommentsPage.getContent().contains(comments[i]), "Comment should be in the result");
        }
    }

    private Comment initComment() {
        return Comment.builder()
                .dateTime(LocalDateTime.now())
                .text("test")
                .movieId("test")
                .userId("test")
                .ratingId("test")
                .build();
    }

    private Comment[] getCommentsForGeneralCrudTests() {
        Comment unique1 = new Comment(null, LocalDateTime.now(), "1", "1", "1", "1");
        Comment unique2 = new Comment(null, LocalDateTime.now(), "2", "2", "2", "2");
        Comment unique3 = new Comment(null, LocalDateTime.now(), "3", "3", "3", "3");

        return new Comment[]{unique1, unique2, unique3};
    }

    private Comment[] getCommentsForSpecializedTests() {
        Comment comment1 = new Comment(null, LocalDateTime.now(), "1", "1", "1", "1");
        Comment comment2 = new Comment(null, LocalDateTime.now(), "2", "2", "2", "2");
        Comment comment3 = new Comment(null, LocalDateTime.now(), "3", "1", "3", "3");
        Comment comment4 = new Comment(null, LocalDateTime.now(), "4", "1", "2", "4");

        return new Comment[]{comment1, comment2, comment3, comment4};
    }

}