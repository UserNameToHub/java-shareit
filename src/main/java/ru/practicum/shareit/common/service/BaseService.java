package ru.practicum.shareit.common.service;

import java.util.List;

public interface BaseService<T, ID> {
    T findById(ID idType);
}
