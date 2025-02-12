package com.zadyraichuk.point_cinema.mapper;

import com.zadyraichuk.point_cinema.dto.general.ActorDTO;
import com.zadyraichuk.point_cinema.dto.general.PictureDTO;
import com.zadyraichuk.point_cinema.dto.identified.IdentifiedActorDTO;
import com.zadyraichuk.point_cinema.dto.identified.IdentifiedPictureDTO;
import com.zadyraichuk.point_cinema.entity.Actor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@ActiveProfiles("test")
@DisplayName("Actor mapper class tests")
@ExtendWith(MockitoExtension.class)
class ActorMapperTest {

    @Autowired
    private ActorMapper mapper;

    private static PictureDTO pictureDTO;
    private static IdentifiedPictureDTO identifiedPictureDTO;

    private static Actor entity;
    private static ActorDTO dto;
    private static IdentifiedActorDTO identifiedDto;

    @BeforeAll
    static void setUp() {
        String id = "test";
        String firstName = "test";
        String lastName = "test";
        String pictureId = "test";

        pictureDTO = Mockito.mock(PictureDTO.class);
        identifiedPictureDTO = Mockito.mock(IdentifiedPictureDTO.class);
        Mockito.when(identifiedPictureDTO.getId()).thenReturn(pictureId);

        entity = new Actor(firstName, lastName, pictureId);
        dto = new ActorDTO(firstName, lastName, pictureDTO);
        identifiedDto = new IdentifiedActorDTO(id, firstName, lastName, identifiedPictureDTO);
    }

    @DisplayName("Test converting Actor to ActorDTO")
    @Test
    void fromEntityToDTOTest() {
        ActorDTO dtoLocal = mapper.toDTO(entity, pictureDTO);

        assertNull(entity.getId(), "Id are not null");
        assertEquals(entity.getFirstName(), dtoLocal.getFirstName(), "First names are not equal");
        assertEquals(entity.getLastName(), dtoLocal.getLastName(), "Last names are not equal");
    }

    @DisplayName("Test converting Actor to IdentifiedActorDTO")
    @Test
    void fromEntityToIdentifiedDTOTest() {
        IdentifiedActorDTO dtoLocal = mapper.toIdentifiedDTO(entity, identifiedPictureDTO);

        assertEquals(entity.getId(), dtoLocal.getId(), "Ids are not equal");
        assertEquals(entity.getFirstName(), dtoLocal.getFirstName(), "First names are not equal");
        assertEquals(entity.getLastName(), dtoLocal.getLastName(), "Last names are not equal");
        assertEquals(entity.getPictureId(), ((IdentifiedPictureDTO) dtoLocal.getPicture()).getId(),
                "Picture IDs are not equal");
    }

    @DisplayName("Test converting ActorDTO to Actor")
    @Test
    void fromDTOtoEntityTest() {
        Actor entityLocal = mapper.toEntity(dto);

        assertNull(entityLocal.getId(), "Id are not null");
        assertEquals(dto.getFirstName(), entityLocal.getFirstName(), "First names are not equal");
        assertEquals(dto.getLastName(), entityLocal.getLastName(), "Last names are not equal");
        assertNull(entityLocal.getPictureId(), "Picture Id are not null");
    }

    @DisplayName("Test converting IdentifiedActorDTO to Actor")
    @Test
    void fromIdentifiedDTOtoEntityTest() {
        Actor entityLocal = mapper.toEntity(identifiedDto);

        assertEquals(identifiedDto.getId(), entityLocal.getId(), "Ids are not equal");
        assertEquals(identifiedDto.getFirstName(), entityLocal.getFirstName(), "First names are not equal");
        assertEquals(identifiedDto.getLastName(), entityLocal.getLastName(), "Last names are not equal");
        assertEquals(((IdentifiedPictureDTO) identifiedDto.getPicture()).getId(), entityLocal.getPictureId(),
                "Picture Ids are not equal");
    }

}
