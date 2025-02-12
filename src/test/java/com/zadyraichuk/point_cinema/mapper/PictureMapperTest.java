package com.zadyraichuk.point_cinema.mapper;

import com.zadyraichuk.point_cinema.dto.general.PictureDTO;
import com.zadyraichuk.point_cinema.dto.identified.IdentifiedPictureDTO;
import com.zadyraichuk.point_cinema.entity.Picture;
import org.bson.types.Binary;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@DisplayName("Picture mapper class tests")
class PictureMapperTest {

    @Autowired
    private PictureMapper mapper;

    private static Picture entity;
    private static PictureDTO dto;
    private static IdentifiedPictureDTO identifiedDto;

    @BeforeAll
    static void setUp() {
         String id = "test";
         String format = "test";
         byte[] pictureData = new byte[]{1, 2, 3};
         Binary image = new Binary(pictureData);

         entity = new Picture(image, format);
         dto = new PictureDTO(pictureData, format);
         identifiedDto = new IdentifiedPictureDTO(id, pictureData, format);
    }

    @DisplayName("Test converting Picture to PictureDTO")
    @Test
    void fromEntityToDTOTest() {
        PictureDTO dtoLocal = mapper.toDTO(entity);
        Binary dtoLocalImage = new Binary(dtoLocal.getPictureData());

        assertNull(entity.getId(), "Id are not null");
        assertEquals(entity.getImage(), dtoLocalImage, "Images are not equal");
        assertEquals(entity.getFormat(), dtoLocal.getFormat(), "Formats are not equal");
    }

    @DisplayName("Test converting Picture to IdentifiedPictureDTO")
    @Test
    void fromEntityToIdentifiedDTOTest() {
        IdentifiedPictureDTO dtoLocal = mapper.toIdentifiedDTO(entity);
        Binary dtoLocalImage = new Binary(dtoLocal.getPictureData());

        assertEquals(entity.getId(), dtoLocal.getId(), "Ids are not equal");
        assertEquals(entity.getImage(), dtoLocalImage, "Images are not equal");
        assertEquals(entity.getFormat(), dtoLocal.getFormat(), "Formats are not equal");
    }

    @DisplayName("Test converting PictureDTO to Picture")
    @Test
    void fromDTOtoEntityTest() {
        Picture entityLocal = mapper.toEntity(dto);
        byte[] entityLocalImage = mapper.toBytes(entityLocal.getImage());

        assertNull(entityLocal.getId(), "Id are not null");
        assertArrayEquals(dto.getPictureData(), entityLocalImage, "Images are not equal");
        assertEquals(dto.getFormat(), entityLocal.getFormat(), "Formats are not equal");
    }

    @DisplayName("Test converting IdentifiedPictureDTO to Picture")
    @Test
    void fromIdentifiedDTOtoEntityTest() {
        Picture entityLocal = mapper.toEntity(identifiedDto);
        byte[] entityLocalImage = mapper.toBytes(entityLocal.getImage());

        assertEquals(identifiedDto.getId(), entityLocal.getId(), "Ids are not equal");
        assertArrayEquals(identifiedDto.getPictureData(), entityLocalImage, "Images are not equal");
        assertEquals(identifiedDto.getFormat(), entityLocal.getFormat(), "Formats are not equal");
    }

}
