package com.zadyraichuk.point_cinema.repository;

import com.zadyraichuk.point_cinema.entity.Genre;
import com.zadyraichuk.point_cinema.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository interface for performing CRUD operations and custom queries on the {@link Movie} collection.
 *
 * @author Rostyslav Zadyraichuk
 */
@Repository
public interface MovieRepository extends MongoRepository<Movie, String> {

    /**
     * Finds a page of {@link Movie} entities whose full name contains the given string, ignoring case.
     *
     * <p>
     * This query uses a case-insensitive search to match movies whose full name includes the specified substring.
     * </p>
     *
     * @param name     the string to match against the movie's full name, ignoring case.
     * @param pageable the pagination information.
     * @return a page of movies whose full name contain the specified string, ignoring case.
     */
    @Query("""
                {
                    $or : [
                        {"name" : {$regex: ?0, $options: 'i'}},
                        {"surname" : {$regex: ?0, $options: 'i'}}
                    ]
                }
            """)
    Page<Movie> findByName(String name, Pageable pageable);

    /**
     * Finds a page of {@link Movie} entities whose genres contain all the given genres.
     *
     * <p>
     * This query retrieves movies that have all the specified genres.
     * Movie must contain all genres from list.
     * </p>
     *
     * @param genres   the genres to match against the movie's genres.
     * @param pageable the pagination information.
     * @return a page of movies whose genres contain all the specified genres.
     */
    @Query("{'genres' : {$all: ?0}}")
    Page<Movie> findByGenresContains(List<Genre> genres, Pageable pageable);

    /**
     * Finds a page of {@link Movie} entities whose release date is after or equal to the specified date.
     *
     * <p>
     * This query retrieves movies that have a release date later than or equal to the specified date.
     * </p>
     *
     * @param date     the date to match against the movie's release date.
     * @param pageable the pagination information.
     * @return a page of movies whose release date is after or equal to the specified date.
     */
    Page<Movie> findByReleaseDateGreaterThanEqual(LocalDate date, Pageable pageable);

    /**
     * Finds a page of {@link Movie} entities whose release date is before or equal to the specified date.
     *
     * <p>
     * This query retrieves movies that have a release date earlier than or equal to the specified date.
     * </p>
     *
     * @param date     the date to match against the movie's release date.
     * @param pageable the pagination information.
     * @return a page of movies whose release date is before or equal to the specified date.
     */
    Page<Movie> findByReleaseDateLessThanEqual(LocalDate date, Pageable pageable);

    /**
     * Finds a page of {@link Movie} entities whose release date is between the specified range of dates.
     *
     * <p>
     * This query retrieves movies that have a release date between the specified dates, inclusive.
     * Date bounds are inclusive.
     * </p>
     *
     * @param from     the start date of the range to match against the movie's release date.
     * @param to       the end date of the range to match against the movie's release date.
     * @param pageable the pagination information.
     * @return a page of movies whose release date is between the specified range of dates.
     */
    @Query("{'release_date' : {$gte: ?0, $lte: ?1}}")
    Page<Movie> findByReleaseDateBetween(LocalDate from, LocalDate to, Pageable pageable);

    /**
     * Finds a page of {@link Movie} entities whose genres contain the given list of genres and whose release date is
     * after or equal to the specified date.
     *
     * <p>
     * This query retrieves movies that belong to the specified genres and have a release date
     * later than or equal to the specified date.
     * Movie must contain all genres from list.
     * </p>
     *
     * @param genres   the list of genres to match against the movie's genres.
     * @param date     the date to match the movie's release date against, returning movies released after or on this date
     * @param pageable the pagination information.
     * @return a page of movies whose genres contain the specified list of genres and whose release date is after or equal to the specified date.
     */
    @Query("{'genres' : {$all: ?0}, 'release_date' : {$gte: ?1}}")
    Page<Movie> findByGenresContainsAndReleaseDateGreaterThanEqual(List<Genre> genres,
                                                                   LocalDate date,
                                                                   Pageable pageable);

    /**
     * Finds a page of {@link Movie} entities whose genres contain the given list of genres and whose release date is
     * before or equal to the specified date.
     *
     * <p>
     * This query retrieves movies that belong to the specified genres and have a release date
     * earlier than or equal to the specified date.
     * Movie must contain all genres from list.
     * </p>
     *
     * @param genres   the list of genres to match against the movie's genres.
     * @param date     the date to match the movie's release date against, returning movies released before or on this date
     * @param pageable the pagination information.
     * @return a page of movies whose genres contain the specified list of genres and whose release date is before or equal to the specified date.
     */
    @Query("{'genres' : {$all: ?0}, 'release_date' : {$lte: ?1}}")
    Page<Movie> findByGenresContainsAndReleaseDateLessThanEqual(List<Genre> genres,
                                                                LocalDate date,
                                                                Pageable pageable);

    /**
     * Finds a page of {@link Movie} entities whose genres contain the given list of genres and whose release date is
     * between the specified range of dates (inclusive).
     *
     * <p>
     * This query uses a case-insensitive search to match movies whose genres include the specified list of genres and
     * whose release date is between the specified range of dates.
     * Movie must contain all genres from list.
     * Date bounds are inclusive.
     * </p>
     *
     * @param genres   the list of genres to match against the movie's genres.
     * @param from     the start date of the range to match against the movie's release date.
     * @param to       the end date of the range to match against the movie's release date.
     * @param pageable the pagination information.
     * @return a page of movies whose genres contain the specified list of genres and whose release date is between the
     * specified range of dates.
     */
    @Query("{'genres' : {$all: ?0}, 'release_date' : {$gte: ?1, $lte: ?2}}")
    Page<Movie> findByGenresContainsAndReleaseDateBetween(List<Genre> genres,
                                                          LocalDate from,
                                                          LocalDate to,
                                                          Pageable pageable);

    /**
     * Finds a page of {@link Movie} entities whose actor ids contain the given id.
     *
     * @param id       the id to match against the movie's actor ids.
     * @param pageable the pagination information.
     * @return a page of movies whose actor ids contain the given id.
     */
    Page<Movie> findByActorIdsContains(String id, Pageable pageable);

}
