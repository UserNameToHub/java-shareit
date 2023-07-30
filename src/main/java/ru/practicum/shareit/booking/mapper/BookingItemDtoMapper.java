package ru.practicum.shareit.booking.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.dto.BookingItemGettingTo;
import ru.practicum.shareit.common.dto.BaseDtoMapper;
import ru.practicum.shareit.item.entity.Item;

@Component
public class BookingItemDtoMapper implements BaseDtoMapper<Item, BookingItemGettingTo> {
    @Override
    public BookingItemGettingTo toDto(Item type) {
        return BookingItemGettingTo.builder()
                .id(type.getId())
                .name(type.getName())
                .build();
    }
}
