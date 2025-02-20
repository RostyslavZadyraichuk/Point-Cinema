package com.zadyraichuk.point_cinema.dto.response;

import com.zadyraichuk.point_cinema.dto.IdentifiedDTO;
import com.zadyraichuk.point_cinema.entity.Technology;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * Represents identified hall data transfer object.
 * This class is used for update/read/delete hall requests.
 *
 * <p>
 * This class uses Lombok annotations to reduce boilerplate code:
 * <ul>
 * <li>{@code @Getter} generates getters for all fields.</li>
 * <li>{@code @SuperBuilder} implements builder pattern with inheritance support.</li>
 * <li>{@code @EqualsAndHashCode} overrides equals and hashCode methods.</li>
 * </ul>
 * </p>
 *
 * @author Rostyslav Zadyraichuk
 */
@Getter
@SuperBuilder
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class HallDetailsDTO extends IdentifiedDTO {

    /**
     * The hall number within the cinema.
     */
    private final Integer number;

    /**
     * The number of rows in the hall.
     */
    private final Integer rows;

    /**
     * The number of columns in the hall (seats per row).
     */
    private final Integer columns;

    /**
     * The technology available in the hall (e.g., 2D, 3D, 4D).
     */
    private final Technology technology;

    /**
     * Identifier of the cinema which this hall belongs to.
     */
    private final String cinemaId;

}
