package com.zadyraichuk.point_cinema.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Represents an Actor entity stored in the "actor" collection of MongoDB.
 * This class is mapped to the "actor" collection in MongoDB.
 *
 * <p>
 * This class uses Lombok annotations to reduce boilerplate code:
 * <ul>
 * <li>{@code @Getter} generates getters for all fields.</li>
 * <li>{@code @Setter} generates a setter for the {@code id} field.</li>
 * <li>{@code @RequiredArgsConstructor} generates a constructor for {@code firstName}, {@code lastName} and
 * {@code pictureId}, which are final fields.</li>
 * <li>{@code @Indexed} and {@code @CompoundIndex} define fields are indexed in database for quicker search.
 * Key {@code unique = true} means database supports uniqueness of marked fields additionally.</li>
 * </ul>
 * </p>
 *
 * @author Rostyslav Zadyraichuk
 * @version 0.2
 */
@Document(collection = "actor")
@RequiredArgsConstructor
@Getter
@CompoundIndex(def = "{'first_name': 1, 'last_name': 1}", unique = true)
public class Actor {

    /**
     * Unique identifier for the actor.
     * It is automatically indexed and must be unique.
     */
    @Id
    @Setter
    private String id;

    /**
     * First name of the actor.
     * This field is mapped to the "first_name" key in the MongoDB collection.
     * This field is used in {@code @CompoundIndex} that ensure uniqueness of actor.
     */
    @Field(name = "first_name")
    private final String firstName;

    /**
     * Last name of the actor.
     * This field is mapped to the "last_name" key in the MongoDB collection.
     * This field is used in {@code @CompoundIndex} that ensure uniqueness of actor.
     */
    @Field(name = "last_name")
    private final String lastName;

    /**
     * Reference to the actor's picture entity.
     */
    @Field(name = "picture_id")
    private final String pictureId;

}
