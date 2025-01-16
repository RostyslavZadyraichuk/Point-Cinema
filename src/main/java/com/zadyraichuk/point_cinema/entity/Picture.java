package com.zadyraichuk.point_cinema.entity;

import lombok.*;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "pictures")
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public class Picture {

    @Id
    @Indexed(unique = true)
    @Setter
    private String id;

    private final Binary picture;

    private final String format;

}