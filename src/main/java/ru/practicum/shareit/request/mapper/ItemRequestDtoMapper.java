package ru.practicum.shareit.request.mapper;

import ru.practicum.shareit.common.dto.BaseDto;
import ru.practicum.shareit.request.dto.ItemRequestTo;
import ru.practicum.shareit.request.entity.ItemRequest;

public class ItemRequestDtoMapper implements BaseDto<ItemRequest, ItemRequestTo> {
    @Override
    public ItemRequestTo toDto(ItemRequest type) {
        return null;
    }

    @Override
    public ItemRequest toEntity(ItemRequestTo type) {
        return null;
    }
}