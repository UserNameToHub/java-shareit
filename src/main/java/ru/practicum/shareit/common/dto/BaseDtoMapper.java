package ru.practicum.shareit.common.dto;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public interface BaseDtoMapper<T1, T2> {
    T2 toDto(T1 type);

//    T1 toEntity(T2 type);

    default List<T2> toDtoList(List<T1> entityList) {
        return entityList.stream().map(this::toDto).collect(Collectors.toList());
    }
}