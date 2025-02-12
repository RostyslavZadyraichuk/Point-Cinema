package com.zadyraichuk.point_cinema.dto.identified;

import com.zadyraichuk.point_cinema.dto.general.ActorDTO;
import com.zadyraichuk.point_cinema.dto.general.PictureDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * Represents an actor data transfer object with identifier.
 * This class is used to obtain, update and delete actor requests.
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
public class IdentitfiedActorDTO extends ActorDTO {

    /**
     * Creates a new instance of {@link IdentitfiedActorDTO} with provided parameters.
     *
     * @param id        the identifier of the actor
     * @param firstName the first name of the actor
     * @param lastName  the last name of the actor
     * @param picture   the picture of the actor
     */
    public IdentitfiedActorDTO(String id,
                               String firstName,
                               String lastName,
                               PictureDTO picture) {
        super(firstName, lastName, picture);
        this.id = id;
    }

    /**
     * The identifier of the actor.
     */
    @EqualsAndHashCode.Include
    @NotBlank(message = "Id cannot be null, empty or blank")
    private final String id;

}
