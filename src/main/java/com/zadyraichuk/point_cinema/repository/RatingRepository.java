package com.zadyraichuk.point_cinema.repository;

import com.zadyraichuk.point_cinema.entity.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for performing CRUD operations and custom queries on the {@link Rating} collection.
 *
 * @see MongoRepository
 * @see Rating
 * @author Rostyslav Zadyraichuk
 */
@Repository
public interface RatingRepository extends MongoRepository<Rating, String> {

    /**
     * Retrieves a page of ratings associated with a specific movie.
     *
     * @param id       the unique identifier of the movie
     * @param pageable the pagination information
     * @return a page of ratings related to the specified movie
     */
    Page<Rating> findByMovieId(String id, Pageable pageable);

    /**
     * Retrieves a page of ratings associated with a specific user.
     *
     * @param id       the unique identifier of the user
     * @param pageable the pagination information
     * @return a page of ratings related to the specified user
     */
    Page<Rating> findByUserId(String id, Pageable pageable);

    /**
     * Retrieves a rating associated with a specific user and a specific movie.
     *
     * @param userId  the unique identifier of the user
     * @param movieId the unique identifier of the movie
     * @return a rating related to the specified user and movie
     */
    Optional<Rating> findByUserIdAndMovieId(String userId, String movieId);

    /**
     * Deletes all ratings associated with a specific movie.
     *
     * @param movieId the unique identifier of the movie
     */
    void deleteByMovieId(String movieId);

    /**
     * Deletes all ratings associated with a specific user.
     *
     * @param userId the unique identifier of the user
     */
    void deleteByUserId(String userId);

}
