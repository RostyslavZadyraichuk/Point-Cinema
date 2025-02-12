package com.zadyraichuk.point_cinema.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

/**
 * Represents a picture data transfer object.
 * This class is used to transfer picture data between client and server.
 *
 * <p>
 * This class uses Lombok annotations to reduce boilerplate code:
 * <ul>
 * <li>{@code @Getter} generates getters for all fields.</li>
 * <li>{@code @Setter} generates a setter for the {@code id} field.</li>
 * <li>{@code @AllArgsConstructor} generates a constructor for all fields.</li>
 * <li>{@code @Builder} implements builder pattern.</li>
 * <li>{@code @EqualsAndHashCode} overrides equals and hashCode methods.</li>
 * </ul>
 * </p>
 *
 * @author Rostyslav Zadyraichuk
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PictureDTO {

    /**
     * The id of the picture.
     * Must be present in the select/update/delete requests, and can be absent in the create request.
     */
    @EqualsAndHashCode.Include
    private String id;

    /**
     * The binary data of the picture.
     * This field is required and cannot be null or empty.
     */
    @NotEmpty(message = "Picture data cannot be empty")
    private byte[] pictureData;

    /**
     * The format of the picture (e.g., "jpg", "png").
     * This field is required and cannot be null, empty or blank.
     */
    //TODO change String into enum here and in entity
    @NotBlank(message = "Format cannot be empty")
    private String format;

}
