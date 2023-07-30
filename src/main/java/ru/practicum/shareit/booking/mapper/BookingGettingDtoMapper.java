package ru.practicum.shareit.booking.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.dto.BookingGettingTo;
import ru.practicum.shareit.booking.entity.Booking;
import ru.practicum.shareit.common.dto.BaseDtoMapper;

@Component
@RequiredArgsConstructor
public class BookingGettingDtoMapper implements BaseDtoMapper<Booking, BookingGettingTo> {
    private final BookingUserDtoMapper userDtoMapper;
    private final BookingItemDtoMapper itemDtoMapper;

    @Override
    public BookingGettingTo toDto(Booking type) {
        return BookingGettingTo.builder()
                .id(type.getId())
                .start(type.getStartDate())
                .end(type.getEndDate())
                .status(type.getStatus())
                .booker(userDtoMapper.toDto(type.getBooker()))
                .item(itemDtoMapper.toDto(type.getItem()))
                .build();
    }
}
