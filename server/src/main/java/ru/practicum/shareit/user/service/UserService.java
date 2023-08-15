package ru.practicum.shareit.user.service;

import ru.practicum.shareit.common.service.BaseService;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

public interface UserService<ID> extends BaseService<UserDto, ID> {
    UserDto findById(Long idType);

    List<UserDto> findAll();

    void delete(ID idType);

    UserDto update(UserDto type, Long idType);

    UserDto create(UserDto type);
}