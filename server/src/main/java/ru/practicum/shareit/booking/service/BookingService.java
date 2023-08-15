package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.dto.BookingCreatingDto;
import ru.practicum.shareit.booking.dto.BookingGettingDto;
import ru.practicum.shareit.booking.enumeration.State;
import ru.practicum.shareit.booking.enumeration.UserStatus;
import ru.practicum.shareit.common.service.BaseService;

import java.util.List;

public interface BookingService extends BaseService<BookingGettingDto, Long> {
    BookingGettingDto create(BookingCreatingDto booking);

    BookingGettingDto findById(Long bookingId, Long userId);

    BookingGettingDto updateStatus(Long bookingId, Boolean boolStatus, Long ownerId);

    List<BookingGettingDto> findAll(Long userId, State state, UserStatus userStatus, Integer from, Integer size);
}
