package com.example.library.infrastructure.conversion;

import com.example.library.domain.model.User;
import com.example.library.infrastructure.dal.mongo.entity.MongoUserEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ModelToEntityUserConverter implements Converter<User, MongoUserEntity> {

    @Override
    public MongoUserEntity convert(User source) {
        MongoUserEntity userEntity = new MongoUserEntity();
        userEntity.setId(source.getId());
        userEntity.setUsername(source.getUsername());
        return userEntity;
    }
}
