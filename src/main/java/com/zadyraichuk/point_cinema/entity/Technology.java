package com.zadyraichuk.point_cinema.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Represents the available technologies for viewing experiences in a cinema hall.
 * Each technology type corresponds to a specific format set.
 *
 * <p>
 * This class uses Lombok annotations to reduce boilerplate code:
 * <ul>
 * <li>{@code @Getter} generates getter for field {@code type}.</li>
 * <li>{@code @RequiredArgsConstructor} generates a constructor for {@code type}, which are final fields.</li>
 * </ul>
 * </p>
 *
 * @author Rostyslav Zadyraichuk
 */
@RequiredArgsConstructor
@Getter
public enum Technology {

    TECHNOLOGY_2D("2D"),
    TECHNOLOGY_3D("3D"),
    TECHNOLOGY_4D("4D"),
    TECHNOLOGY_RM("RM"),
    TECHNOLOGY_RM_PLUS("RM+");

    /**
     * The string representation of the technology type.
     */
    private final String type;

}
