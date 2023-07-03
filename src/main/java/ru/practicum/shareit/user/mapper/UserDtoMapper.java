package ru.practicum.shareit.user.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.common.dto.BaseDto;
import ru.practicum.shareit.user.dto.UserTo;
import ru.practicum.shareit.user.entity.User;

@Component
public class UserDtoMapper implements BaseDto<User, UserTo> {
    @Override
    public UserTo toDto(User type) {
        return UserTo.builder()
                .id(type.getId())
                .name(type.getName())
                .email(type.getEmail())
                .build();
    }

    @Override
    public User toEntity(UserTo type) {
        return User.builder()
                .email(type.getEmail())
                .name(type.getName())
                .build();
    }
}