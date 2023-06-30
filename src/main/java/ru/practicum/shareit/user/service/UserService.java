package ru.practicum.shareit.user.service;

import ru.practicum.shareit.common.service.BaseService;
import ru.practicum.shareit.user.dto.UserTo;

import java.util.List;

public interface UserService<ID> extends BaseService<UserTo, ID> {
    List<UserTo> findAll();

    void delete(ID idType);

    UserTo update(UserTo type, Long idType);

    UserTo create(UserTo type);
}
