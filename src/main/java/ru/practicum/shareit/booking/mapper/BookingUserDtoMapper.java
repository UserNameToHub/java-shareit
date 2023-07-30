package ru.practicum.shareit.booking.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.dto.BookingUserGettingTo;
import ru.practicum.shareit.common.dto.BaseDtoMapper;
import ru.practicum.shareit.user.entity.User;

@Component
public class BookingUserDtoMapper implements BaseDtoMapper<User, BookingUserGettingTo> {
    @Override
    public BookingUserGettingTo toDto(User type) {
        return BookingUserGettingTo.builder()
                .id(type.getId())
                .build();
    }
}
