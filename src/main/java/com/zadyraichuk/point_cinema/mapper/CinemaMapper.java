package com.zadyraichuk.point_cinema.mapper;

import com.zadyraichuk.point_cinema.dto.general.CinemaDTO;
import com.zadyraichuk.point_cinema.dto.identified.IdentifiedCinemaDTO;
import com.zadyraichuk.point_cinema.entity.Cinema;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Maps between entity class {@link Cinema} and dto classes {@link CinemaDTO} and {@link IdentifiedCinemaDTO}.
 *
 * @author Rostyslav Zadyraichuk
 */
@Mapper(componentModel = "spring")
public interface CinemaMapper {

    /**
     * Converts an {@link CinemaDTO} data transfer object to a {@link Cinema} entity.
     *
     * @param dto the data transfer object to convert
     * @return the converted entity object
     */
    @Mapping(target = "id", ignore = true)
    Cinema toEntity(CinemaDTO dto);

    /**
     * Converts an {@link IdentifiedCinemaDTO} data transfer object to a {@link Cinema} entity.
     *
     * @param dto the data transfer object to convert
     * @return the converted entity object
     */
    Cinema toEntity(IdentifiedCinemaDTO dto);

    /**
     * Converts a {@link Cinema} entity to an {@link CinemaDTO} data transfer object.
     *
     * @param entity the entity to convert
     * @return the converted data transfer object
     */
    CinemaDTO toDTO(Cinema entity);

    /**
     * Converts a {@link Cinema} entity to an {@link IdentifiedCinemaDTO} data transfer object.
     *
     * @param entity the entity to convert
     * @return the converted data transfer object
     */
    IdentifiedCinemaDTO toIdentifiedDTO(Cinema entity);

}
