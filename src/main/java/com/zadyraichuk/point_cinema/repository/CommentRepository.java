package com.zadyraichuk.point_cinema.repository;

import com.zadyraichuk.point_cinema.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for performing CRUD operations and custom queries on the {@link Comment} collection.
 *
 * @see MongoRepository
 * @see Comment
 * @author Rostyslav Zadyraichuk
 */
@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {

    /**
     * Retrieves a paginated list of comments associated with a specific movie.
     *
     * @param id the unique identifier of the movie
     * @param pageable the pagination information
     * @return a paginated list of comments related to the specified movie
     */
    Page<Comment> findByMovieId(String id, Pageable pageable);

    /**
     * Retrieves a paginated list of comments associated with a specific user.
     *
     * @param id the unique identifier of the user
     * @param pageable the pagination information
     * @return a paginated list of comments related to the specified user
     */
    Page<Comment> findByUserId(String id, Pageable pageable);

    /**
     * Deletes all comments associated with a specific user.
     *
     * @param id the unique identifier of the user
     */
    void deleteByUserId(String id);

    /**
     * Deletes all comments associated with a specific movie.
     *
     * @param id the unique identifier of the movie
     */
    void deleteByMovieId(String id);

}
