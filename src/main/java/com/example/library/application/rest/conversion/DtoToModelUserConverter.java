package com.example.library.application.rest.conversion;

import com.example.library.application.rest.dto.UserDto;
import com.example.library.domain.model.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DtoToModelUserConverter implements Converter<UserDto, User> {

    @Override
    public User convert(UserDto source) {
        User user = new User();
        user.setId(source.getId());
        user.setUsername(source.getUsername());
        return user;
    }
}
