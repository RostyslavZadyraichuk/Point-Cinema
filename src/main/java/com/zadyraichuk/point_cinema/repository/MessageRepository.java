package com.zadyraichuk.point_cinema.repository;

import com.zadyraichuk.point_cinema.entity.Message;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for performing CRUD operations and custom queries on the {@link Message} collection.
 *
 * @author Rostyslav Zadyraichuk
 */
@Repository
public interface MessageRepository extends MongoRepository<Message, String> {
}