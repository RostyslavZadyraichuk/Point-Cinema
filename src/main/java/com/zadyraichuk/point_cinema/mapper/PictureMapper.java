package com.zadyraichuk.point_cinema.mapper;

import com.zadyraichuk.point_cinema.dto.general.PictureDTO;
import com.zadyraichuk.point_cinema.dto.identified.IdentifiedPictureDTO;
import com.zadyraichuk.point_cinema.entity.Picture;
import org.bson.types.Binary;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Maps between entity class {@link Picture} and dto classes {@link PictureDTO} and {@link IdentifiedPictureDTO}.
 *
 * @author Rostyslav Zadyraichuk
 */
@Mapper(componentModel = "spring")
public interface PictureMapper {

    /**
     * Converts an {@link PictureDTO} data transfer object to a {@link Picture} entity.
     *
     * @param dto the data transfer object to convert
     * @return the converted entity object
     */
    @Mapping(target = "image", source = "pictureData", qualifiedByName = "toBinary")
    @Mapping(target = "id", ignore = true)
    Picture toEntity(PictureDTO dto);

    /**
     * Converts an {@link IdentifiedPictureDTO} data transfer object to a {@link Picture} entity.
     *
     * @param dto the data transfer object to convert
     * @return the converted entity object
     */
    @Mapping(target = "image", source = "pictureData", qualifiedByName = "toBinary")
    Picture toEntity(IdentifiedPictureDTO dto);

    /**
     * Converts a {@link Picture} entity to an {@link PictureDTO} data transfer object.
     *
     * @param entity the entity to convert
     * @return the converted data transfer object
     */
    @Mapping(target = "pictureData", source = "image", qualifiedByName = "toBytes")
    PictureDTO toDTO(Picture entity);

    /**
     * Converts a {@link Picture} entity to an {@link IdentifiedPictureDTO} data transfer object.
     *
     * @param entity the entity to convert
     * @return the converted data transfer object
     */
    @Mapping(target = "pictureData", source = "image", qualifiedByName = "toBytes")
    IdentifiedPictureDTO toIdentifiedDTO(Picture entity);

    /**
     * Converts a {@link Binary} image to a byte array.
     *
     * @param image the image to convert
     * @return the converted byte array
     */
    @Named("toBytes")
    default byte[] toBytes(Binary image) {
        return image.getData();
    }

    /**
     * Converts a byte array to a {@link Binary} image.
     *
     * @param pictureData the byte array to convert
     * @return the converted image
     */
    @Named("toBinary")
    default Binary toBinary(byte[] pictureData) {
        return new Binary(pictureData);
    }

}
