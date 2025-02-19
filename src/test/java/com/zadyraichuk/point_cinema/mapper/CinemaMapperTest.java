package com.zadyraichuk.point_cinema.mapper;

import com.zadyraichuk.point_cinema.dto.general.CinemaDTO;
import com.zadyraichuk.point_cinema.dto.identified.IdentifiedCinemaDTO;
import com.zadyraichuk.point_cinema.entity.Cinema;
import com.zadyraichuk.point_cinema.entity.Country;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@ActiveProfiles("test")
@DisplayName("Cinema mapper class tests")
class CinemaMapperTest {

    @Autowired
    private CinemaMapper mapper;

    private static Cinema entity;
    private static CinemaDTO dto;
    private static IdentifiedCinemaDTO identifiedDto;

    @BeforeAll
    static void setupClass() {
        String id = "test";
        String name = "test";
        Country country = Country.UKRAINE;
        String city = "test";
        String street = "test";

        entity = new Cinema(name, country, city, street);
        dto = new CinemaDTO(name, country, city, street);
        identifiedDto = new IdentifiedCinemaDTO(id, name, country, city, street);
    }

    @DisplayName("Test converting Cinema to CinemaDTO")
    @Test
    void fromEntityToDTOTest() {
        CinemaDTO dtoLocal = mapper.toDTO(entity);

        assertNull(entity.getId(), "Id are not null");
        assertEquals(entity.getName(), dtoLocal.getName(), "Names are not equal");
        assertEquals(entity.getCountry(), dtoLocal.getCountry(), "Countries are not equal");
        assertEquals(entity.getCity(), dtoLocal.getCity(), "Cities are not equal");
        assertEquals(entity.getStreet(), dtoLocal.getStreet(), "Streets are not equal");
    }

    @DisplayName("Test converting Cinema to IdentifiedCinemaDTO")
    @Test
    void fromEntityToIdentifiedDTOTest() {
        IdentifiedCinemaDTO dtoLocal = mapper.toIdentifiedDTO(entity);

        assertEquals(entity.getId(), dtoLocal.getId(), "Ids are not equal");
        assertEquals(entity.getName(), dtoLocal.getName(), "Names are not equal");
        assertEquals(entity.getCountry(), dtoLocal.getCountry(), "Countries are not equal");
        assertEquals(entity.getCity(), dtoLocal.getCity(), "Cities are not equal");
        assertEquals(entity.getStreet(), dtoLocal.getStreet(), "Streets are not equal");
    }

    @DisplayName("Test converting CinemaDTO to Cinema")
    @Test
    void fromDTOtoEntityTest() {
        Cinema entityLocal = mapper.toEntity(dto);

        assertNull(entityLocal.getId(), "Id are not null");
        assertEquals(dto.getName(), entityLocal.getName(), "Names are not equal");
        assertEquals(dto.getCountry(), entityLocal.getCountry(), "Countries are not equal");
        assertEquals(dto.getCity(), entityLocal.getCity(), "Cities are not equal");
        assertEquals(dto.getStreet(), entityLocal.getStreet(), "Streets are not equal");
    }

    @DisplayName("Test converting IdentifiedCinemaDTO to Cinema")
    @Test
    void fromIdentifiedDTOtoEntityTest() {
        Cinema entityLocal = mapper.toEntity(identifiedDto);

        assertEquals(identifiedDto.getId(), entityLocal.getId(), "Ids are not equal");
        assertEquals(identifiedDto.getName(), entityLocal.getName(), "Names are not equal");
        assertEquals(identifiedDto.getCountry(), entityLocal.getCountry(), "Countries are not equal");
        assertEquals(identifiedDto.getCity(), entityLocal.getCity(), "Cities are not equal");
        assertEquals(identifiedDto.getStreet(), entityLocal.getStreet(), "Streets are not equal");
    }

}
