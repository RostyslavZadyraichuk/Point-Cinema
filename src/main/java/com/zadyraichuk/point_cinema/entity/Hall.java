package com.zadyraichuk.point_cinema.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Represents a hall within a cinema.
 * A hall contains information about its number, seating arrangement, technology, and associated cinema.
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
@Document(collection = "hall")
@AllArgsConstructor
@Getter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@CompoundIndex(def = "{'number': 1, 'cinemaId': 1}", unique = true)
public class Hall {

    /**
     * Unique identifier for the hall.
     */
    @Id
    @Setter
    @EqualsAndHashCode.Include
    private String id;

    /**
     * The hall number within the cinema.
     */
    private final Integer number;

    /**
     * The number of rows in the hall.
     */
    private final Integer rows;

    /**
     * The number of columns in the hall (seats per row).
     */
    private final Integer columns;

    /**
     * The technology available in the hall (e.g., 2D, 3D, 4D).
     */
    private final Technology technology;

    /**
     * Identifier of the cinema which this hall belongs to.
     */
    @Field(name = "cinema_id")
    private final String cinemaId;

}