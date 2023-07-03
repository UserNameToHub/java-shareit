package ru.practicum.shareit.common.dto;

public interface BaseDto<T1, T2> {
    T2 toDto(T1 type);

    T1 toEntity(T2 type);
}