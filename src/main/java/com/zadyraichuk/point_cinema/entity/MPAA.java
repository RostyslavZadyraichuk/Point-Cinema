package com.zadyraichuk.point_cinema.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Represents the Motion Picture Association of America (MPAA) rating for movies.
 *
 *
 * @author Rostyslav Zadyraichuk
 * @version 0.1
 */
@AllArgsConstructor
@Getter
public enum MPAA {

    /**
     * General Audiences - All ages admitted.
     */
    G("G"),

    /**
     * Parental Guidance Suggested - Some material may not be suitable for children.
     */
    PG("PG"),

    /**
     * Parents Strongly Cautioned - Some material may be inappropriate for children under 13.
     */
    PG_13("PG-13"),

    /**
     * Restricted - Under 17 requires accompanying parent or adult guardian.
     */
    R("R"),

    /**
     * No One 17 and Under Admitted.
     */
    NC_17("NC-17");

    /**
     * The MPAA rating as a string.
     */
    private final String rating;

}
