package com.zadyraichuk.point_cinema.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Represents a comment made by a user about a movie.
 * This class is mapped to the "comment" collection in the database.
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
@Document(collection = "comment")
@AllArgsConstructor
@Getter
@Builder
public class Comment {

    /**
     * The unique identifier of the comment.
     * This field is indexed and must be unique.
     */
    @Id
    @Indexed(unique = true)
    @Setter
    private String id;

    /**
     * The date and time when the comment was created.
     * Defaults to the current date and time truncated to minutes (e.g. yyyy-MM-hhTHH:mm:00.0).
     */
    @Builder.Default
    private LocalDateTime dateTime = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);

    /**
     * The text content of the comment.
     */
    private String text;

    /**
     * The ID of the user who made the comment.
     * Stored in the database with the field name "user_id".
     */
    @Field(name = "user_id")
    private String userId;

    /**
     * The ID of the movie that the comment is associated with.
     * Stored in the database with the field name "movie_id".
     */
    @Field(name = "movie_id")
    private String movieId;

    /**
     * The ID of the rating associated with the comment.
     * Stored in the database with the field name "movie_rating_id".
     */
    @Field(name = "movie_rating_id")
    private String ratingId;

}
