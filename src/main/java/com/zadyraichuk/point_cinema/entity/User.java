package com.zadyraichuk.point_cinema.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * Represents a user entity in the system.
 * This class is mapped to the "user" collection in the database and stores
 * personal information, authentication details, and user-related associations.
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
 * </ul>
 * </p>
 *
 * @author Rostyslav Zadyraichuk
 * @version 0.2
 */
@Document(collection = "user")
@AllArgsConstructor
@Getter
@Builder
public class User {

    /**
     * The unique identifier of the user.
     * This field is indexed and must be unique.
     */
    @Id
    @Setter
    private String id;

    /**
     * The first name of the user.
     * Stored in the database with the field name "first_name".
     */
    @Field(name = "first_name")
    private final String firstName;

    /**
     * The last name of the user.
     * Stored in the database with the field name "last_name".
     */
    @Field(name = "last_name")
    private final String lastName;

    /**
     * The hashed password of the user for authentication purposes.
     */
    private final String password;

    /**
     * The username of the user, used for authentication and identification.
     */
    @Indexed(unique = true)
    private final String username;

    /**
     * The email address of the user, used for communication and notifications.
     */
    @Indexed(unique = true)
    private final String email;

    /**
     * The phone number of the user for authentication purposes.
     */
    @Indexed(unique = true)
    private final String phone;

    /**
     * The role of the user within the system.
     * Defaults to {@code Role.USER}.
     */
    @Builder.Default
    private final Role role = Role.USER;

    /**
     * A list of ticket IDs associated with the user.
     * Represents the tickets the user has booked.
     * Stored in the database with the field name "ticket_ids".
     */
    @Field(name = "ticket_ids")
    @Singular("ticketId")
    private final List<String> ticketIds;

    /**
     * The ID of the user's profile picture.
     * Stored in the database with the field name "user_picture_id".
     */
    @Field(name = "user_picture_id")
    private final String pictureId;

    /**
     * A list of IDs of the user's favourite movies.
     * Stored in the database with the field name "favourite_movie_ids".
     */
    @Field(name = "favourite_movie_ids")
    @Singular("favouriteMovieId")
    private final List<String> favouriteMovieIds;

    /**
     * A list of IDs of movies the user has viewed.
     * Stored in the database with the field name "viewed_movie_ids".
     */
    @Field(name = "viewed_movie_ids")
    @Singular("viewedMovieId")
    private final List<String> viewedMovieIds;

    /**
     * A list of IDs of movies the user is waiting for.
     * Stored in the database with the field name "wait_movie_ids".
     */
    @Field(name = "wait_movie_ids")
    @Singular("waitMovieId")
    private final List<String> waitMovieIds;

    /**
     * A list of message IDs associated with the user.
     * Represents the messages the user has sent or received.
     * Stored in the database with the field name "message_ids".
     */
    @Field(name = "message_ids")
    @Singular("messageId")
    private final List<String> messageIds;

}
