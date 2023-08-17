package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.BookingClient;
import ru.practicum.shareit.booking.dto.BookItemRequestDto;
import ru.practicum.shareit.booking.dto.BookingState;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookingServiceImp implements BookingService {
    private final BookingClient bookingClient;

    @Override
    public ResponseEntity<Object> getBookings(long userId, BookingState state, Integer from, Integer size) {
        log.info("Get booking with state {}, userId={}, from={}, size={}", state, userId, from, size);
        return bookingClient.getBookings(userId, state, from, size);
    }

    @Override
    public ResponseEntity<Object> getBookingsByOwner(long userId, BookingState state, Integer from, Integer size) {
        log.info("Get booking with state {}, userId={}, from={}, size={}", state, userId, from, size);
        return bookingClient.getBookingsByOwner(userId, state, from, size);
    }

    @Override
    public ResponseEntity<Object> editStatus(Long bookingId, Boolean approved, Long userId) {
        log.info("Edit booking with id {}", bookingId);
        return bookingClient.editStatus(bookingId, approved, userId);
    }

    @Override
    public ResponseEntity<Object> bookItem(long userId, BookItemRequestDto requestDto) {
        log.info("Creating booking {}, userId={}", requestDto, userId);
        return bookingClient.bookItem(userId, requestDto);
    }

    @Override
    public ResponseEntity<Object> getBooking(long userId, Long bookingId) {
        log.info("Get booking with id {}", bookingId);
        return bookingClient.getBooking(userId, bookingId);
    }
}
