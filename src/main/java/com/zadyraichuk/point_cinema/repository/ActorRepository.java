package com.zadyraichuk.point_cinema.repository;

import com.zadyraichuk.point_cinema.entity.Actor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for performing CRUD operations and custom queries on the {@link Actor} collection.
 *
 * @author Rostyslav Zadyraichuk
 */
@Repository
public interface ActorRepository extends MongoRepository<Actor, String> {

    /**
     * Finds a list of {@link Actor} entities where either the first name or the last name
     * matches the given regular expressions.
     *
     * @param firstName the regular expression to match against the actor's first name.
     * @param lastName the regular expression to match against the actor's last name.
     * @return a list of actors whose first name or last name matches the provided regex.
     */
    List<Actor> findByFirstNameRegexOrLastNameRegex(String firstName, String lastName);

}
