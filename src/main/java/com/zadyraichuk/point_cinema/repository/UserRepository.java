package com.zadyraichuk.point_cinema.repository;

import com.zadyraichuk.point_cinema.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for performing CRUD operations and custom queries on the {@link User} collection.
 *
 * <p>
 * This repository provides methods to interact with the "user" collection in the MongoDB database.
 * It extends the {@link MongoRepository} interface to inherit basic CRUD operations and offers
 * additional custom queries defined using the {@code @Query} annotation.
 * </p>
 *
 * @author Rostyslav Zadyraichuk
 * @see MongoRepository
 * @see User
 */
@Repository
public interface UserRepository extends MongoRepository<User, String> {

    /**
     * Finds the first {@link User} entity whose username, email, or phone matches the given value.
     *
     * @param usernameOrEmailOrPhone the value to match against the user's username, email, or phone.
     * @return an {@link Optional} containing the first matching user, if found.
     */
    @Query("""
                {
                    "$or": [
                        {"username": ?0},
                        {"email": ?0},
                        {"phone": ?0}
                    ]
                }
            """)
    Optional<User> findByUsernameOrEmailOrPhone(String usernameOrEmailOrPhone);

}