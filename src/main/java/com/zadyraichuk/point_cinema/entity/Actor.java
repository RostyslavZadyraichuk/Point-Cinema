package com.zadyraichuk.point_cinema.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Represents an Actor entity stored in the "actor" collection of MongoDB.
 */
@Document(collection = "actor")
@RequiredArgsConstructor
@Getter
public class Actor {

    /**
     * Unique identifier for the actor.
     * It is automatically indexed and must be unique.
     */
    @Id
    @Indexed(unique = true)
    @Setter
    private String id;

    /**
     * First name of the actor.
     * This field is mapped to the "first_name" key in the MongoDB collection.
     */
    @Field(name = "first_name")
    private final String firstName;

    /**
     * Last name of the actor.
     * This field is mapped to the "last_name" key in the MongoDB collection.
     */
    @Field(name = "last_name")
    private final String lastName;

    /**
     * Reference to the actor's picture entity.
     */
    @Field(name = "picture_id")
    private final String pictureId;

}
