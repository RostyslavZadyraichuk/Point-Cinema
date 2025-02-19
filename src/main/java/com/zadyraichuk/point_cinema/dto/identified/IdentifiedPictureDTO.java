package com.zadyraichuk.point_cinema.dto.identified;

import com.zadyraichuk.point_cinema.dto.general.PictureDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

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
public class IdentifiedPictureDTO extends PictureDTO {

    /**
     * The id of the picture.
     */
    @EqualsAndHashCode.Include
    @NotBlank(message = "Id cannot be null, empty or blank")
    private final String id;

    /**
     * Constructs a new {@code IdentifiedPictureDTO} with the given parameters.
     *
     * @param id          the id of the picture
     * @param pictureData the picture data
     * @param format      the picture format
     */
    public IdentifiedPictureDTO(String id, byte[] pictureData, String format) {
        super(pictureData, format);
        this.id = id;
    }

}
