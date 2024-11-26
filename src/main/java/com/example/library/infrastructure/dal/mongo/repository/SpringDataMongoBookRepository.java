package com.example.library.infrastructure.dal.mongo.repository;

import com.example.library.infrastructure.dal.mongo.entity.MongoBookEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SpringDataMongoBookRepository extends MongoRepository<MongoBookEntity, UUID> {

    List<MongoBookEntity> findByBorrowedById(UUID userId);
}
