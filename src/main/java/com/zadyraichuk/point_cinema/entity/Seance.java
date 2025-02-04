package com.zadyraichuk.point_cinema.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.time.LocalTime;
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
 * <li>{@code @Indexed} and {@code @CompoundIndex} define fields are indexed in database for quicker search.
 * Key {@code unique = true} means database supports uniqueness of marked fields additionally.</li>
 * <li>{@code @EqualsAndHashCode} overrides equals and hashCode methods.</li>
 * </ul>
 * </p>
 *
 * @author Rostyslav Zadyraichuk
 */
@Document(collection = "seance")
@AllArgsConstructor
@Getter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Seance {

    /**
     * Unique identifier for the seance.
     */
    @Id
    @Setter
    @EqualsAndHashCode.Include
    private String id;

    /**
     * The starting time of the seance.
     */
    @Field(name = "start_seance")
    private final LocalTime startSeance;

    /**
     * The ending time of the seance.
     */
    @Field(name = "end_seance")
    private final LocalTime endSeance;

    /**
     * The starting date range from which the seance is available.
     */
    @Field(name = "date_from")
    @Indexed
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
    @Indexed
    private final String movieId;

    /**
     * The language of the seance.
     * Default seance language is set as Ukrainian.
     */
    @Field(name = "seance_language")
    @Builder.Default
    private final Language seanceLanguage = Language.UA;

    /**
     * The list of days on which the seance is scheduled.
     */
    @Singular("day")
    private final List<Day> days;

}
