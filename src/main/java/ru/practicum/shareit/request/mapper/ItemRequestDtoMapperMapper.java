package ru.practicum.shareit.request.mapper;

import ru.practicum.shareit.common.dto.BaseDtoMapper;
import ru.practicum.shareit.request.dto.ItemRequestTo;
import ru.practicum.shareit.request.entity.ItemRequest;

import java.util.List;
import java.util.stream.Collectors;

public class ItemRequestDtoMapperMapper implements BaseDtoMapper<ItemRequest, ItemRequestTo> {
    @Override
    public ItemRequestTo toDto(ItemRequest type) {
        return null;
    }
}