package com.zadyraichuk.point_cinema.repository;

import com.zadyraichuk.point_cinema.entity.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository interface for performing CRUD operations and custom queries on the {@link Ticket} collection.
 *
 * @see MongoRepository
 * @see Ticket
 * @author Rostyslav Zadyraichuk
 */
@Repository
public interface TicketRepository extends MongoRepository<Ticket, String> {

    /**
     * Finds a list of tickets by the specified date and seance ID.
     *
     * @param date the date to search for tickets.
     * @param seanceId the ID of the seance to search for tickets.
     * @return a list of tickets that match the specified date and seance ID.
     */
    List<Ticket> findByDateAndSeanceId(LocalDate date, String seanceId);

    /**
     * Finds a list of tickets by the specified user ID.
     *
     * @param userId the ID of the user to search for tickets.
     * @return a list of tickets that match the specified user ID.
     */
    List<Ticket> findByUserId(String userId);

    /**
     * Deletes all tickets that are dated before the specified date.
     *
     * @param date the date before which to delete tickets.
     */
    void deleteByDateBefore(LocalDate date);

}
