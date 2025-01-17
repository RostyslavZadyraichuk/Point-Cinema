package com.zadyraichuk.point_cinema.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "message")
@RequiredArgsConstructor
@Getter
public class Message {

    @Id
    @Indexed(unique = true)
    @Setter
    private String id;

    private final String text;

}