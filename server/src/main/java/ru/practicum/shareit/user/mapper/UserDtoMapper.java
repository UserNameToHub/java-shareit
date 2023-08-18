package ru.practicum.shareit.user.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.common.dto.BaseDtoMapper;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.entity.User;

@Component
public class UserDtoMapper implements BaseDtoMapper<User, UserDto> {
    @Override
    public UserDto toDto(User type) {
        return UserDto.builder()
                .id(type.getId())
                .name(type.getName())
                .email(type.getEmail())
                .build();
    }

    public User toEntity(UserDto type) {
        return User.builder()
                .email(type.getEmail())
                .name(type.getName())
                .build();
    }
}