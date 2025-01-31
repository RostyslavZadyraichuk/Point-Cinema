package com.zadyraichuk.point_cinema.repository;

import com.zadyraichuk.point_cinema.entity.Hall;
import com.zadyraichuk.point_cinema.entity.Technology;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for performing CRUD operations and custom queries on the {@link Hall} collection.
 *
 * @see MongoRepository
 * @see Hall
 * @author Rostyslav Zadyraichuk
 */
@Repository
public interface HallRepository extends MongoRepository<Hall, String> {

    /**
     * Finds a list of {@link Hall} entities associated with the given cinema ID.
     *
     * @param cinemaId the ID of the cinema whose halls are to be retrieved.
     * @return a list of halls associated with the provided cinema ID.
     */
    List<Hall> findByCinemaId(String cinemaId);

    /**
     * Finds a list of {@link Hall} entities associated with the given cinema ID and technology.
     *
     * @param cinemaId the ID of the cinema whose halls are to be retrieved.
     * @param technology the technology type of the halls to be retrieved.
     * @return a list of halls associated with the provided cinema ID and technology.
     */
    List<Hall> findByCinemaIdAndTechnology(String cinemaId, Technology technology);

    /**
     * Finds a list of {@link Hall} entities associated with the given cinema ID and a list of technologies.
     *
     * @param cinemaId the ID of the cinema whose halls are to be retrieved.
     * @param technologies a list of technology types to filter the halls.
     * @return a list of halls associated with the provided cinema ID and any of the specified technologies.
     */
    List<Hall> findByCinemaIdAndTechnologyIn(String cinemaId, List<Technology> technologies);

}
