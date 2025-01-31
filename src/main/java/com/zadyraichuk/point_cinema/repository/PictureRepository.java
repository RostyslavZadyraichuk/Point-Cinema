package com.zadyraichuk.point_cinema.repository;

import com.zadyraichuk.point_cinema.entity.Picture;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for performing CRUD operations on the {@link Picture} collection.
 *
 * @see MongoRepository
 * @see Picture
 * @author Rostyslav Zadyraichuk
 */
@Repository
public interface PictureRepository extends MongoRepository<Picture, String> {
}