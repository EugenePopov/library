package com.example.library.application.rest.conversion;

import com.example.library.application.rest.dto.UserDto;
import com.example.library.domain.model.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ModelToDtoUserConverter implements Converter<User, UserDto> {

    @Override
    public UserDto convert(User source) {
        UserDto userDto = new UserDto();
        userDto.setId(source.getId());
        userDto.setUsername(source.getUsername());
        return userDto;
    }
}
