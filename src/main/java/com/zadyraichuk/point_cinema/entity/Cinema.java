package com.zadyraichuk.point_cinema.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents a cinema entity stored in the "cinema" collection in a database.
 * This class is mapped to the "cinema" collection in MongoDB.
 *
 * <p>
 * This class uses Lombok annotations to reduce boilerplate code:
 * <ul>
 * <li>{@code @Getter} generates getters for all fields.</li>
 * <li>{@code @Setter} generates a setter for the {@code id} field.</li>
 * <li>{@code @RequiredArgsConstructor} generates a constructor for {@code name}, {@code country}, {@code city} and
 * {@code street}, which are final fields.</li>
 * </ul>
 * </p>
 *
 * @author Rostyslav Zadyraichuk
 * @version 0.2
 */
@Document(collection = "cinema")
@RequiredArgsConstructor
@Getter
public class Cinema {

    /**
     * Unique identifier for the cinema.
     * Indexed to ensure uniqueness in the database.
     */
    @Id
    @Setter
    private String id;

    /**
     * The name of the cinema.
     * This is a mandatory field and cannot be null.
     */
    private final String name;

    /**
     * The country where the cinema is located.
     * This is a mandatory field and cannot be null.
     * Enumeration automatically converts into string data type.
     */
    private final Country country;

    /**
     * The city where the cinema is located.
     * This is a mandatory field and cannot be null.
     */
    private final String city;

    /**
     * The street address of the cinema.
     * This is a mandatory field and cannot be null.
     */
    private final String street;

}
