package com.zadyraichuk.point_cinema.dto.identified;

import com.zadyraichuk.point_cinema.dto.general.CinemaDTO;
import com.zadyraichuk.point_cinema.entity.Country;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Represents a cinema data transfer object with identifier.
 * This class is used for obtain, update and delete cinema requests.
 *
 * <p>
 * This class uses Lombok annotations to reduce boilerplate code:
 * <ul>
 * <li>{@code @Getter} generates getters for all fields.</li>
 * <li>{@code @EqualsAndHashCode} overrides equals and hashCode methods.</li>
 * </ul>
 * </p>
 *
 * @author Rostyslav Zadyraichuk
 */
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class IdentifiedCinemaDTO extends CinemaDTO {

    /**
     * The identifier of the cinema.
     */
    @EqualsAndHashCode.Include
    @NotBlank(message = "Id cannot be null, empty or blank")
    private final String id;

    /**
     * Creates a new instance of {@link IdentifiedCinemaDTO} with provided parameters.
     *
     * @param id      the identifier of the cinema
     * @param name    the name of the cinema
     * @param country the country of the cinema
     * @param city    the city of the cinema
     * @param street  the street of the cinema
     */
    public IdentifiedCinemaDTO(String id,
                               String name,
                               Country country,
                               String city,
                               String street) {
        super(name, country, city, street);
        this.id = id;
    }

}
