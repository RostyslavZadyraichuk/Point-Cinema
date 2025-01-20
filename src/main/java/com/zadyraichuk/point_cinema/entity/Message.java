package com.zadyraichuk.point_cinema.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents a message entity stored in a MongoDB collection.
 * Each message has a unique identifier and a text content.
 *
 * <p>
 * This class uses Lombok annotations to reduce boilerplate code:
 * <ul>
 * <li>{@code @Getter} generates getters for all fields.</li>
 * <li>{@code @Setter} generates a setter for the {@code id} field.</li>
 * <li>{@code @RequiredArgsConstructor} generates a constructor for {@code text}, which is final field.</li>
 * </ul>
 * </p>
 *
 * @author Rostyslav Zadyraichuk
 * @version 0.2
 */
@Document(collection = "message")
@RequiredArgsConstructor
@Getter
public class Message {

    /**
     * Unique identifier for the message.
     * This field is automatically generated and indexed.
     */
    @Id
    @Setter
    private String id;

    /**
     * The text content of the message.
     * This field is required and immutable once set.
     */
    private final String text;

}