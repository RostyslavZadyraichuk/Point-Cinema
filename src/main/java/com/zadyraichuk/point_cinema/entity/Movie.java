package com.zadyraichuk.point_cinema.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
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
 * <li>{@code @Indexed} and {@code @CompoundIndex} define fields are indexed in database for quicker search.
 * Key {@code unique = true} means database supports uniqueness of marked fields additionally.</li>
 * <li>{@code @EqualsAndHashCode} overrides equals and hashCode methods.</li>
 * </ul>
 * </p>
 *
 * @author Rostyslav Zadyraichuk
 */
@Document(collection = "movie")
@AllArgsConstructor
@Getter
@Builder
@CompoundIndex(def = "{'name': 1, 'surname': 1}", unique = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Movie {

    /**
     * Unique identifier for the movie.
     */
    @Id
    @Setter
    @EqualsAndHashCode.Include
    private String id;

    /**
     * The first name associated with the movie.
     */
    private final String name;

    /**
     * The surname associated with the movie (e.g. additional name or part of series).
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
    @Indexed
    private final LocalDate releaseDate;

    /**
     * The user rating for the movie (default is 0).
     */
    @Field(name = "users_rating")
    @Builder.Default
    private final Double usersRating = 0.0;

    /**
     * The MPAA rating of the movie (e.g., G, PG, R).
     */
    @Field(name = "mpaa_rating")
    private final MPAA mpaaRating;

    /**
     * The IMDb rating of the movie (default is 0).
     */
    @Field(name = "imdb_rating")
    @Builder.Default
    private final Double imdbRating = 0.0;

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
     * A set of identifiers for actors associated with the movie.
     */
    @Field(name = "actor_ids")
    @Singular("actorId")
    @Indexed
    private final Set<String> actorIds;

    /**
     * A set of genres associated with the movie.
     */
    @Singular("genre")
    @Indexed
    private final Set<Genre> genres;

}
