package com.example.library.infrastructure.dal.mongo.repository;

import com.example.library.infrastructure.dal.mongo.entity.MongoUserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SpringDataMongoUserRepository extends MongoRepository<MongoUserEntity, UUID> {
}
