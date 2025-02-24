package com.zadyraichuk.point_cinema.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * Base class for all data transfer objects with identifier.
 *
 * <p>
 * This class uses Lombok annotations to reduce boilerplate code:
 * <ul>
 * <li>{@code @Getter} generates getters for all fields.</li>
 * <li>{@code @AllArgsConstructor} generates a constructor for all fields.</li>
 * <li>{@code @EqualsAndHashCode} overrides equals and hashCode methods.</li>
 * <li>{@code @SuperBuilder} implements builder pattern with inheritance support.</li>
 * </ul>
 * </p>
 *
 * @author Rostyslav Zadyraichuk
 */
@Getter
@EqualsAndHashCode
@AllArgsConstructor
@SuperBuilder(builderMethodName = "")
public abstract class IdentifiedDTO {

    /**
     * The id of the picture.
     * Identifier cannot be null, empty or blank.
     */
    @NotBlank(message = "Id cannot be null, empty or blank")
    private final String id;

}
