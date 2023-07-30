package ru.practicum.shareit.booking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingCreatingTo;
import ru.practicum.shareit.booking.dto.BookingGettingTo;
import ru.practicum.shareit.booking.enumeration.State;
import ru.practicum.shareit.booking.enumeration.UserStatus;
import ru.practicum.shareit.booking.service.BookingServiceImpl;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import java.util.List;

import static ru.practicum.shareit.util.Constants.HEADER_USER_ID;

/**
 * TODO Sprint add-bookings.
 */
@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingServiceImpl service;

    @PostMapping
    public BookingGettingTo create(@Valid @RequestBody BookingCreatingTo booking,
                                   @NotNull @RequestHeader(HEADER_USER_ID) Long userId) {
        booking.setUserId(userId);
        return service.create(booking);
    }

    @GetMapping("/{bookingId}")
    public BookingGettingTo getById(@RequestHeader(HEADER_USER_ID) Long userId,
                                    @PathVariable("bookingId") Long bookingId) {
        return service.findById(bookingId, userId);
    }

    @PatchMapping("/{bookingId}")
    public BookingGettingTo editStatus(@PathVariable Long bookingId,
                                       @RequestParam("approved") Boolean approved,
                                       @RequestHeader(HEADER_USER_ID) Long userId) {
        return service.updateStatus(bookingId, approved, userId);
    }

    @GetMapping
    public List<BookingGettingTo> getAllByBooker(@RequestParam(name = "state", defaultValue = "ALL") State state,
                                                 @RequestHeader(HEADER_USER_ID) Long userid) {
        return service.findAll(userid, state, UserStatus.BOOKER);
    }

    @GetMapping("/owner")
    public List<BookingGettingTo> getAllByOwner(@RequestParam(name = "state", defaultValue = "ALL") State state,
                                                @RequestHeader(HEADER_USER_ID) Long userid) {
        return service.findAll(userid, state, UserStatus.OWNER);
    }
}