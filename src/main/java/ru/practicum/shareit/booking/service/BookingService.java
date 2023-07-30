package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.dto.BookingCreatingTo;
import ru.practicum.shareit.booking.dto.BookingGettingTo;
import ru.practicum.shareit.booking.enumeration.State;
import ru.practicum.shareit.booking.enumeration.UserStatus;
import ru.practicum.shareit.common.service.BaseService;

import java.util.List;

public interface BookingService extends BaseService<BookingGettingTo, Long> {
    BookingGettingTo create(BookingCreatingTo booking);
    BookingGettingTo findById(Long bookingId, Long userId);

    BookingGettingTo updateStatus(Long bookingId, Boolean boolStatus, Long ownerId);

    List<BookingGettingTo> findAll(Long userId, State state, UserStatus userStatus);
}
