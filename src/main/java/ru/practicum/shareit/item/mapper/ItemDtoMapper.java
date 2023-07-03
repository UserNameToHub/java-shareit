package ru.practicum.shareit.item.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.common.dto.BaseDto;
import ru.practicum.shareit.item.dto.ItemTo;
import ru.practicum.shareit.item.entity.Item;

@Component
public class ItemDtoMapper implements BaseDto<Item, ItemTo> {
    @Override
    public ItemTo toDto(Item type) {
        return ItemTo.builder()
                .id(type.getId())
                .name(type.getName())
                .description(type.getDescription())
                .available(type.getAvailable())
                .build();
    }

    @Override
    public Item toEntity(ItemTo type) {
        return Item.builder()
                .id(type.getId())
                .request(null)
                .feedbacks(null)
                .owner(null)
                .name(type.getName())
                .description(type.getDescription())
                .available(type.getAvailable())
                .build();
    }
}