package com.zadyraichuk.point_cinema.entity;

import lombok.*;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents a picture entity stored in the MongoDB database.
 * This class is mapped to the "pictures" collection in MongoDB.
 *
 * <p>
 * This class uses Lombok annotations to reduce boilerplate code:
 * <ul>
 * <li>{@code @Getter} generates getters for all fields.</li>
 * <li>{@code @Setter} generates a setter for the {@code id} field.</li>
 * <li>{@code @RequiredArgsConstructor} generates a constructor for {@code picture} and {@code format}, which are final fields.</li>
 * </ul>
 * </p>
 *
 * @author Rostyslav Zadyraichuk
 */
@Document(collection = "pictures")
@RequiredArgsConstructor
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Picture {

    /**
     * The unique identifier for the picture. String is used here because of similarity to MongoDB identifier values.
     * It's also good choice to use {@code UUID} class.
     *
     * <p>
     * This field is annotated with {@code @Id} to indicate it is the primary key in MongoDB.
     * The {@code @Indexed(unique = true)} annotation enforces a unique index for this field in the "pictures" collection.
     * </p>
     */
    @Id
    @Setter
    @EqualsAndHashCode.Include
    private String id;

    /**
     * The binary content of the picture.
     *
     * <p>
     * This field is final and is required for creating a {@code Picture} object.
     * </p>
     */
    private final Binary image;

    /**
     * The format of the picture (e.g., JPEG, PNG).
     *
     * <p>
     * This field is final and is required for creating a {@code Picture} object.
     * </p>
     */
    private final String format;

}