package com.zadyraichuk.point_cinema.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Represents a movie seance (screening) with details about timing, pricing, and other attributes.
 * This class is mapped to the "seance" collection in the database.
 *
 * <p>
 * This class uses Lombok annotations to reduce boilerplate code:
 * <ul>
 * <li>{@code @Getter} generates getters for all fields.</li>
 * <li>{@code @Setter} generates a setter for the {@code id} field.</li>
 * <li>{@code @AllArgsConstructor} generates a constructor for all fields.</li>
 * <li>{@code @Builder} implements builder pattern.</li>
 * </ul>
 * </p>
 *
 * @author Rostyslav Zadyraichuk
 * @version 0.1
 */
@Document(collection = "seance")
@AllArgsConstructor
@Getter
@Builder
//todo add @Builder.Defaults if needed (and update tests)
public class Seance {

    /**
     * Unique identifier for the seance.
     */
    @Id
    @Indexed(unique = true)
    @Setter
    private String id;

    /**
     * The starting time of the seance.
     */
    @Field(name = "start_seance")
    private final LocalDateTime startSeance;

    /**
     * The ending time of the seance.
     */
    @Field(name = "end_seance")
    private final LocalDateTime endSeance;

    /**
     * The starting date range from which the seance is available.
     */
    @Field(name = "date_from")
    private final LocalDate dateFrom;

    /**
     * The ending date range until which the seance is available.
     */
    @Field(name = "date_to")
    private final LocalDate dateTo;

    /**
     * The price of a ticket for the seance.
     */
    @Field(name = "ticket_price")
    private final Double ticketPrice;

    /**
     * The identifier of the hall where the seance takes place.
     */
    @Field(name = "hall_id")
    private final String hallId;

    /**
     * The identifier of the movie being screened in the seance.
     */
    @Field(name = "movie_id")
    private final String movieId;

    /**
     * The language of the seance.
     */
    @Field(name = "seance_language")
    private final Lang seanceLang;

    /**
     * The list of days on which the seance is scheduled.
     */
    @Singular("day")
    private final List<Day> days;

}
