package com.zadyraichuk.point_cinema.dto.general;

import com.zadyraichuk.point_cinema.entity.Country;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Represents a cinema data transfer object without identifier.
 * This class is used for create cinema requests.
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
@EqualsAndHashCode
public class CinemaDTO {

    /**
     * The name of the cinema.
     */
    @NotBlank(message = "Name cannot be empty")
    @EqualsAndHashCode.Exclude
    private final String name;

    /**
     * The country where the cinema is located.
     */
    @NotNull(message = "Country cannot be null")
    private final Country country;

    /**
     * The city where the cinema is located.
     */
    @NotBlank(message = "City cannot be empty")
    private final String city;

    /**
     * The street address of the cinema.
     */
    @NotBlank(message = "Street cannot be empty")
    private final String street;

}

