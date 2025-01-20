package com.zadyraichuk.point_cinema.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Represents a rating given by a user to a movie.
 * This class is mapped to the "rating" collection in the database.
 *
 * <p>
 * This class uses Lombok annotations to reduce boilerplate code:
 * <ul>
 * <li>{@code @Getter} generates getters for all fields.</li>
 * <li>{@code @Setter} generates a setter for the {@code id} field.</li>
 * <li>{@code @RequiredArgsConstructor} generates a constructor for {@code userRating}, {@code userId} and
 * {@code movieId}, which are final fields.</li>
 * <li>{@code @Indexed} and {@code @CompoundIndex} define fields are indexed in database for quicker search.
 * Key {@code unique = true} means database supports uniqueness of marked fields additionally.</li>
 * </ul>
 * </p>
 *
 * @author Rostyslav Zadyraichuk
 * @version 0.2
 */
@Document(collection = "rating")
@RequiredArgsConstructor
@Getter
@CompoundIndex(def = "{'movieId': 1, 'userId': 1}", unique = true)
public class Rating {

    /**
     * The unique identifier of the rating.
     * This field is indexed and must be unique.
     */
    @Id
    @Setter
    private String id;

    /**
     * The rating value provided by the user for a specific movie.
     * Stored in the database with the field name "user_rating".
     */
    @Field(name = "user_rating")
    private final Integer userRating;

    /**
     * The ID of the user who gave the rating.
     * Stored in the database with the field name "user_id".
     */
    @Field(name = "user_id")
    private final String userId;

    /**
     * The ID of the movie that the user rated.
     * Stored in the database with the field name "movie_id".
     */
    @Field(name = "movie_id")
    private final String movieId;

}
