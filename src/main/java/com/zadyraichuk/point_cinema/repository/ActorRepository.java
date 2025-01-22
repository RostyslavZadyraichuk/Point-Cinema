package com.zadyraichuk.point_cinema.repository;

import com.zadyraichuk.point_cinema.entity.Actor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
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
     * Finds a list of {@link Actor} entities whose first name contains the given string
     * (ignoring case) and whose last name also contains the given string (ignoring case).
     *
     * <p>
     * This query uses a case-insensitive search to match actors whose first name
     * and last name includes the specified substring.
     * </p>
     *
     * @param firstName the string to match against the actor's first name, ignoring case.
     * @param lastName the string to match against the actor's last name, ignoring case.
     * @return a list of actors whose first name and last name contain the specified strings, ignoring case.
     */
    @Query("""
            {
                'first_name': {$regex: ?0, $options: 'i'},
                'last_name': {$regex: ?1, $options: 'i'}
            }
        """)
    List<Actor> findByFirstNameAndLastName(String firstName, String lastName);

    /**
     * Finds a list of {@link Actor} entities where either the first name or the last name
     * contains the given string, ignoring case.
     *
     * <p>
     * This query uses a case-insensitive search to match actors whose first name
     * or last name includes the specified substring.
     * </p>
     *
     * @param firstNameOrLastName the string to match against the actor's first name or last name, ignoring case.
     * @return a list of actors whose first name or last name contain the specified string, ignoring case.
     */
    @Query("""
            {
                $or: [
                    {'first_name': {$regex: ?0, $options: 'i'}},
                    {'last_name': {$regex: ?0, $options: 'i'}}
                ]
            }
        """)
    List<Actor> findByFirstNameOrLastName(String firstNameOrLastName);

}
