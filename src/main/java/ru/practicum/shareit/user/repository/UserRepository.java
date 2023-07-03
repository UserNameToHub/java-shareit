package ru.practicum.shareit.user.repository;

import ru.practicum.shareit.common.repository.CrudRepository;
import ru.practicum.shareit.user.entity.User;

public interface UserRepository<ID> extends CrudRepository<User, ID> {
    boolean isUnique(String str);
}