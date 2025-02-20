package com.zadyraichuk.point_cinema.dto.request;

import com.zadyraichuk.point_cinema.entity.Technology;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Represents a new hall data transfer object without identifier.
 * This class is used for create hall requests.
 *
 * <p>
 * This class uses Lombok annotations to reduce boilerplate code:
 * <ul>
 * <li>{@code @Getter} generates getters for all fields.</li>
 * <li>{@code @AllArgsConstructor} generates a constructor for all fields.</li>
 * <li>{@code @EqualsAndHashCode} overrides equals and hashCode methods.}</li>
 * </ul>
 * </p>
 *
 * @author Rostyslav Zadyraichuk
 */
@Getter
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class NewHallDTO {

    /**
     * The hall number within the cinema.
     */
    @EqualsAndHashCode.Include
    @NotNull(message = "Hall number cannot be null")
    @Positive(message = "Hall number must be positive")
    private final Integer number;

    /**
     * The number of rows in the hall.
     */
    @Min(value = 1, message = "Number of rows must be at least 1")
    @Max(value = 50, message = "Number of rows cannot exceed 50")
    private final Integer rows;

    /**
     * The number of columns in the hall (seats per row).
     */
    @Min(value = 1, message = "Number of rows must be at least 1")
    @Max(value = 50, message = "Number of rows cannot exceed 50")
    private final Integer columns;

    /**
     * The technology available in the hall (e.g., 2D, 3D, 4D).
     */
    @NotNull(message = "Technology cannot be null")
    private final Technology technology;

    /**
     * Identifier of the cinema which this hall belongs to.
     */
    @EqualsAndHashCode.Include
    @NotBlank(message = "Cinema ID cannot be null, empty or blank")
    private final String cinemaId;

}

