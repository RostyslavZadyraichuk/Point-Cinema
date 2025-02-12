package com.zadyraichuk.point_cinema.dto.general;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

/**
 * Represents general picture data transfer object without identifier.
 * This class is used to create picture requests.
 *
 * <p>
 * This class uses Lombok annotations to reduce boilerplate code:
 * <ul>
 * <li>{@code @Getter} generates getters for all fields.</li>
 * <li>{@code @AllArgsConstructor} generates a constructor for all fields.</li>
 * </ul>
 * </p>
 *
 * @author Rostyslav Zadyraichuk
 */
@Getter
@AllArgsConstructor
public class PictureDTO {

    /**
     * The binary data of the picture.
     * This field is required and cannot be null or empty.
     */
    @NotEmpty(message = "Picture data cannot be empty")
    private final byte[] pictureData;

    /**
     * The format of the picture (e.g., "jpg", "png").
     * This field is required and cannot be null, empty or blank.
     */
    //TODO change String into enum here and in entity
    @NotBlank(message = "Format cannot be empty")
    private final String format;

}

