package ru.practicum.shareit.common.dto;

public interface BaseTo<T1, T2> {
    T2 toTo(T1 type);
    T1 toData(T2 type);
}
