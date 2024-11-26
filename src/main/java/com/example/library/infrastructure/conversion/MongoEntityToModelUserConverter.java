package com.example.library.infrastructure.conversion;

import com.example.library.domain.model.User;
import com.example.library.infrastructure.dal.mongo.entity.MongoUserEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MongoEntityToModelUserConverter implements Converter<MongoUserEntity, User> {

    @Override
    public User convert(MongoUserEntity source) {
        User user = new User();
        user.setId(source.getId());
        user.setUsername(source.getUsername());
        return user;
    }
}
