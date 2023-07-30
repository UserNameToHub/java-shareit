package ru.practicum.shareit.item.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.common.dto.BaseDtoMapper;
import ru.practicum.shareit.item.dto.ItemTo;
import ru.practicum.shareit.item.entity.Item;

@Component
public class ItemDtoMapper implements BaseDtoMapper<Item, ItemTo> {
    @Override
    public ItemTo toDto(Item type) {
        return ItemTo.builder()
                .id(type.getId())
                .name(type.getName())
                .description(type.getDescription())
                .available(type.getAvailable())
                .build();
    }

    public Item toEntity(ItemTo type) {
        return Item.builder()
                .id(type.getId())
                .name(type.getName())
                .description(type.getDescription())
                .available(type.getAvailable())
                .build();
    }
}