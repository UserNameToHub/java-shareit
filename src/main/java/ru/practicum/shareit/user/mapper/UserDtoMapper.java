package ru.practicum.shareit.user.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.common.dto.BaseTo;
import ru.practicum.shareit.user.dto.UserTo;
import ru.practicum.shareit.user.entity.User;

@Component
public class UserDtoMapper implements BaseTo<User, UserTo> {
    @Override
    public UserTo toTo(User type) {
        return UserTo.builder()
                .id(type.getId())
                .name(type.getName())
                .email(type.getEmail())
                .build();
    }

    @Override
    public User toData(UserTo type) {
        return User.builder()
                .email(type.getEmail())
                .name(type.getName())
                .build();
    }
}
