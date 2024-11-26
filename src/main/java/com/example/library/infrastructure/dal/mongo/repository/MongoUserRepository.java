package com.example.library.infrastructure.dal.mongo.repository;

import com.example.library.domain.model.User;
import com.example.library.domain.ports.out.UserRepository;
import com.example.library.infrastructure.conversion.ModelToEntityUserConverter;
import com.example.library.infrastructure.conversion.MongoEntityToModelUserConverter;
import com.example.library.infrastructure.dal.mongo.entity.MongoUserEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@AllArgsConstructor
public class MongoUserRepository implements UserRepository {

    private final ModelToEntityUserConverter modelToEntityUserConverter;
    private final MongoEntityToModelUserConverter mongoEntityToModelUserConverter;
    private final SpringDataMongoUserRepository mongoUserRepository;

    @Override
    public User save(User user) {
        MongoUserEntity userEntity = modelToEntityUserConverter.convert(user);
        MongoUserEntity mongoUserEntity = mongoUserRepository.save(userEntity);
        log.info("Saved user: {}", mongoUserEntity);
        return mongoEntityToModelUserConverter.convert(mongoUserEntity);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return mongoUserRepository.findById(id)
                .map(mongoEntityToModelUserConverter::convert);
    }
}
