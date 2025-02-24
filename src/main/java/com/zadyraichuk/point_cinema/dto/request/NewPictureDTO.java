package com.zadyraichuk.point_cinema.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Represents general picture data transfer object without identifier.
 * This class is used for create picture requests.
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
public class NewPictureDTO {

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
    @NotBlank(message = "Format cannot be empty")
    private final String format;

}

