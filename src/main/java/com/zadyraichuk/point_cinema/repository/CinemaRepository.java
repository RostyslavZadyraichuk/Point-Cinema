package com.zadyraichuk.point_cinema.repository;

import com.zadyraichuk.point_cinema.entity.Cinema;
import com.zadyraichuk.point_cinema.entity.Country;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for performing CRUD operations and custom queries on the {@link Cinema} collection.
 *
 * @author Rostyslav Zadyraichuk
 */
@Repository
public interface CinemaRepository extends MongoRepository<Cinema, String> {

    /**
     * Finds a list of {@link Cinema} entities by the given country and a regular expression
     * for the city name.
     *
     * <p>
     * This method is case-insensitive when matching against the city name.
     * </p>
     *
     * @param country the country to filter cinemas.
     * @param city the regular expression to match against the cinema's city.
     * @return a list of cinemas located in the specified country and whose city matches the provided regex.
     */
    List<Cinema> findAllByCountryAndCityContainingIgnoreCase(Country country, String city);

}
