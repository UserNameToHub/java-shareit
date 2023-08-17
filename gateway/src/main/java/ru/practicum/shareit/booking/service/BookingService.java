package ru.practicum.shareit.booking.service;

import org.springframework.http.ResponseEntity;
import ru.practicum.shareit.booking.dto.BookItemRequestDto;
import ru.practicum.shareit.booking.dto.BookingState;

public interface BookingService {
    public ResponseEntity<Object> getBookings(long userId, BookingState state, Integer from, Integer size);

    public ResponseEntity<Object> getBookingsByOwner(long userId, BookingState state, Integer from, Integer size);

    public ResponseEntity<Object> editStatus(Long bookingId, Boolean approved, Long userId);

    public ResponseEntity<Object> bookItem(long userId, BookItemRequestDto requestDto);

    public ResponseEntity<Object> getBooking(long userId, Long bookingId);
}
