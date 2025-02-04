package com.zadyraichuk.point_cinema.repository;

import com.zadyraichuk.point_cinema.entity.Seance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

/**
 * Repository interface for performing CRUD operations and custom queries on the {@link Seance} collection.
 *
 * @author Rostyslav Zadyraichuk
 */
@Repository
public interface SeanceRepository extends MongoRepository<Seance, String> {

    /**
     * Retrieves a page of {@link Seance}s by a given hall ID.
     *
     * @param id       the ID of the hall
     * @param pageable the pagination information
     * @return a page of seances
     */
    Page<Seance> findByHallId(String id, Pageable pageable);

    /**
     * Retrieves a page of {@link Seance}s by a given movie ID.
     *
     * @param id       the ID of the movie
     * @param pageable the pagination information
     * @return a page of seances
     */
    Page<Seance> findByMovieId(String id, Pageable pageable);

    /**
     * Retrieves a page of {@link Seance}s that have a date within the specified seance date range.
     *
     * @param date     the date to check within the seance date range
     * @param pageable the pagination information
     * @return a page of seances that include the specified date within their date range
     */
    @Query("""
                {
                    $and: [
                        { "dateFrom": { $lte: ?0 } },
                        { "dateTo": { $gte: ?0 } }
                    ]
                }
            """)
    Page<Seance> findByDateIsInSeanceDatesRange(LocalDate date,
                                                Pageable pageable);

    /**
     * Deletes all seances whose end date is before the specified date.
     *
     * @param date the date
     */
    void deleteByDateToBefore(LocalDate date);

}
