package com.example.library.infrastructure.dal.mongo.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Document(collation = "users")
public class MongoUserEntity {

    @Id
    private UUID id;
    private String username;
}
