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
 * @version 0.1
 */
@RequiredArgsConstructor
@Getter
public enum Technology {

    _2D("2D"),
    _3D("3D"),
    _4D("4D"),
    _RM("RM"),
    _RM_PLUS("RM+");

    /**
     * The string representation of the technology type.
     */
    private final String type;

}
