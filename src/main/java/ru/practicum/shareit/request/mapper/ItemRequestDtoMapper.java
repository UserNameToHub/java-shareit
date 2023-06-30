package ru.practicum.shareit.request.mapper;

import ru.practicum.shareit.common.dto.BaseTo;
import ru.practicum.shareit.request.dto.ItemRequestTo;
import ru.practicum.shareit.request.entity.ItemRequest;

public class ItemRequestDtoMapper implements BaseTo<ItemRequest, ItemRequestTo> {
    @Override
    public ItemRequestTo toTo(ItemRequest type) {
        return null;
    }

    @Override
    public ItemRequest toData(ItemRequestTo type) {
        return null;
    }
}