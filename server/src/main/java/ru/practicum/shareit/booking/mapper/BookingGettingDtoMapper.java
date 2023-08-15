package ru.practicum.shareit.booking.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.dto.BookingGettingDto;
import ru.practicum.shareit.booking.entity.Booking;
import ru.practicum.shareit.common.dto.BaseDtoMapper;
import ru.practicum.shareit.item.mapper.ItemDtoMapper;
import ru.practicum.shareit.user.mapper.UserDtoMapper;

@Component
@RequiredArgsConstructor
public class BookingGettingDtoMapper implements BaseDtoMapper<Booking, BookingGettingDto> {
    private final UserDtoMapper userDtoMapper;
    private final ItemDtoMapper itemDtoMapper;

    @Override
    public BookingGettingDto toDto(Booking type) {
        return BookingGettingDto.builder()
                .id(type.getId())
                .start(type.getStartDate())
                .end(type.getEndDate())
                .status(type.getStatus())
                .booker(userDtoMapper.toDto(type.getBooker()))
                .item(itemDtoMapper.toDto(type.getItem()))
                .build();
    }
}
