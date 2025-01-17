package com.zadyraichuk.point_cinema.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.util.*;

/**
 * Represents a movie entity with details about its name, description, ratings, associated actors, and more.
 * This class is mapped to the "movie" collection in the database.
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
@Document(collection = "movie")
@AllArgsConstructor
@Getter
@Builder
public class Movie {

    /**
     * Unique identifier for the movie.
     */
    @Id
    @Indexed(unique = true)
    @Setter
    private String id;

    /**
     * The full name of the movie.
     */
    @Field(name = "full_name")
    private final String fullName;

    /**
     * The first name associated with the movie (e.g., director or creator's first name).
     */
    private final String name;

    /**
     * The surname associated with the movie (e.g., director or creator's last name).
     */
    private final String surname;

    /**
     * A brief description of the movie.
     */
    private final String description;

    /**
     * The director of the movie.
     */
    @Field(name = "directed_by")
    private final String directedBy;

    /**
     * The duration of the movie in minutes.
     */
    private final Integer duration;

    /**
     * The release date of the movie.
     */
    @Field(name = "release_date")
    private final LocalDate releaseDate;

    /**
     * The user rating for the movie (default is 0).
     */
    @Field(name = "users_rating")
    private final Double usersRating;

    /**
     * The MPAA rating of the movie (e.g., G, PG, R).
     */
    @Field(name = "mpaa_rating")
    private final MPAA mpaaRating;

    /**
     * The IMDb rating of the movie (default is 0).
     */
    @Field(name = "imdb_rating")
    private final Double imdbRating;

    /**
     * The country where the movie was produced.
     */
    @Field(name = "movie_country")
    private final String movieCountry;

    /**
     * The identifier of the wide picture associated with the movie.
     */
    @Field(name = "movie_picture_id")
    private final String widePictureId;

    /**
     * The identifier of the poster picture associated with the movie.
     */
    @Field(name = "movie_poster_id")
    private final String posterPictureId;

    /**
     * A list of identifiers for gallery pictures associated with the movie.
     */
    @Field(name = "movie_gallery_ids")
    @Singular("galleryPictureId")
    private final List<String> galleryPictureIds;

    /**
     * A list of identifiers for actors associated with the movie.
     */
    @Field(name = "actor_ids")
    @Singular("actorId")
    private final List<String> actorIds;

    /**
     * A list of genres associated with the movie.
     */
    @Singular("genre")
    private final List<Genre> genres;

}
