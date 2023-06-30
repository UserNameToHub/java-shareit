package ru.practicum.shareit.common.repository;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T, ID> {
    List<T> findAll();
    Optional<T> findById(ID idType);
    void delete(ID idType);
    T update(T type);
    T create(T type);
    boolean existsById(ID idType);
}
