package com.example.library.infrastructure.dal.mongo.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Document(collection = "books")
public class MongoBookEntity {

    @Id
    private UUID id;
    private String title;
    private String author;
    private String isbn;
    private MongoUserEntity borrowedBy;
}
