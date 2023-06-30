package ru.practicum.shareit.common.service;

public interface BaseService<T, ID> {
    T findById(ID idType);
}
