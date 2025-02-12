package com.zadyraichuk.point_cinema.mapper;

import com.zadyraichuk.point_cinema.dto.general.ActorDTO;
import com.zadyraichuk.point_cinema.dto.general.PictureDTO;
import com.zadyraichuk.point_cinema.dto.identified.IdentifiedActorDTO;
import com.zadyraichuk.point_cinema.dto.identified.IdentifiedPictureDTO;
import com.zadyraichuk.point_cinema.entity.Actor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Maps between entity class {@link Actor} and dto classes {@link ActorDTO} and {@link IdentifiedActorDTO}.
 *
 * @author Rostyslav Zadyraichuk
 */
@Mapper(componentModel = "spring")
public interface ActorMapper {

    /**
     * Converts an {@link ActorDTO} data transfer object to a {@link Actor} entity.
     *
     * @param dto the data transfer object to convert
     * @return the converted entity object
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pictureId", ignore = true)
    Actor toEntity(ActorDTO dto);

    /**
     * Converts an {@link IdentifiedActorDTO} data transfer object to a {@link Actor} entity.
     *
     * @param dto the data transfer object to convert
     * @return the converted entity object
     */
    @Mapping(target = "pictureId", source = "picture", qualifiedByName = "toPictureId")
    Actor toEntity(IdentifiedActorDTO dto);

    /**
     * Converts a {@link Actor} entity to an {@link ActorDTO} data transfer object.
     *
     * @param entity the entity to convert
     * @param picture the picture data transfer object
     * @return the converted data transfer object
     */
    @Mapping(target = "picture", source = "picture")
    @Mapping(target = "withPicture", ignore = true)
    ActorDTO toDTO(Actor entity, PictureDTO picture);

    /**
     * Converts a {@link Actor} entity to an {@link IdentifiedActorDTO} data transfer object.
     *
     * @param entity the entity to convert
     * @param picture the identified picture data transfer object
     * @return the converted data transfer object
     */
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "picture", source = "picture")
    @Mapping(target = "withPicture", ignore = true)
    IdentifiedActorDTO toIdentifiedDTO(Actor entity, IdentifiedPictureDTO picture);

    /**
     * Obtains an identifier of picture data transfer object.
     *
     * @param dto picture data transfer object
     * @return the identifier if exists or null if not
     */
    @Named("toPictureId")
    default String toPictureId(PictureDTO dto) {
        if (dto instanceof IdentifiedPictureDTO picture)
            return picture.getId();
        return null;
    }

}
