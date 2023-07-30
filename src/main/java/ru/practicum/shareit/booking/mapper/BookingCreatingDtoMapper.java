package ru.practicum.shareit.booking.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.dto.BookingCreatingTo;
import ru.practicum.shareit.booking.entity.Booking;
import ru.practicum.shareit.booking.enumeration.Status;
import ru.practicum.shareit.common.dto.BaseDtoMapper;
import ru.practicum.shareit.item.entity.Item;
import ru.practicum.shareit.user.entity.User;

@Component
public class BookingCreatingDtoMapper implements BaseDtoMapper<Booking, BookingCreatingTo> {
    @Override
    public BookingCreatingTo toDto(Booking type) {
        return null;
    }

    public Booking toEntity(BookingCreatingTo type, User user, Item item) {
        return Booking.builder()
                .item(item)
                .booker(user)
                .status(Status.WAITING)
                .startDate(type.getStart())
                .endDate(type.getEnd())
                .build();
    }
}