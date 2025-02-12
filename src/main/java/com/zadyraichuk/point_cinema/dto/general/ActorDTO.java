package com.zadyraichuk.point_cinema.dto.general;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * Represents an actor data transfer object without identifier.
 * This class is used to create actor requests.
 *
 * <p>
 * This class uses Lombok annotations to reduce boilerplate code:
 * <ul>
 * <li>{@code @Getter} generates getters for all fields.</li>
 * <li>{@code @AllArgsConstructor} generates a constructor for all fields.</li>
 * <li>{@code @EqualsAndHashCode} overrides equals and hashCode methods.}</li>
 * <li>{@code @With} is used to make defensive copying of the {@link PictureDTO} object.</li>
 * </ul>
 * </p>
 *
 * @author Rostyslav Zadyraichuk
 */
@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class ActorDTO {

    /**
     * The first name of the actor.
     */
    @NotBlank(message = "First name cannot be empty")
    private final String firstName;

    /**
     * The last name of the actor.
     */
    @NotBlank(message = "Last name cannot be empty")
    private final String lastName;

    /**
     * The picture of the actor.
     * Picture can be null - then default picture will be used.
     */
    @With
    @EqualsAndHashCode.Exclude
    private final PictureDTO picture;

}

