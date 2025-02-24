package com.zadyraichuk.point_cinema.dto.response;

import com.zadyraichuk.point_cinema.dto.IdentifiedDTO;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Represents a picture data transfer object with identifier.
 * This class is used for obtain, update and delete picture requests.
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
public class PictureDetailsDTO extends IdentifiedDTO {

    /**
     * The binary data of the picture.
     */
    private final byte[] pictureData;

    /**
     * The format of the picture (e.g., "jpg", "png").
     */
    private final String format;

    public PictureDetailsDTO(String id, byte[] pictureData, String format) {
        super(id);
        this.pictureData = pictureData;
        this.format = format;
    }

}
