package ru.practicum.shareit.booking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingCreatingDto;
import ru.practicum.shareit.booking.dto.BookingGettingDto;
import ru.practicum.shareit.booking.enumeration.State;
import ru.practicum.shareit.booking.enumeration.UserStatus;
import ru.practicum.shareit.booking.service.BookingService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import java.util.List;

import static ru.practicum.shareit.util.Constants.HEADER_USER_ID;

/**
 * TODO Sprint add-bookings.
 */
@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService service;

    @PostMapping
    public BookingGettingDto create(@Valid @RequestBody BookingCreatingDto booking,
                                    @NotNull @RequestHeader(HEADER_USER_ID) Long userId) {
        booking.setBookerId(userId);
        return service.create(booking);
    }

    @GetMapping("/{bookingId}")
    public BookingGettingDto getById(@RequestHeader(HEADER_USER_ID) Long userId,
                                     @PathVariable("bookingId") Long bookingId) {
        return service.findById(bookingId, userId);
    }

    @PatchMapping("/{bookingId}")
    public BookingGettingDto editStatus(@PathVariable Long bookingId,
                                        @RequestParam("approved") Boolean approved,
                                        @RequestHeader(HEADER_USER_ID) Long userId) {
        return service.updateStatus(bookingId, approved, userId);
    }

    @GetMapping
    public List<BookingGettingDto> getAllByBooker(@RequestParam(name = "state", defaultValue = "ALL") State state,
                                                  @RequestHeader(HEADER_USER_ID) Long userid,
                                                  @RequestParam(value = "from", defaultValue = "0") @PositiveOrZero Integer from,
                                                  @RequestParam(value = "size", defaultValue = "20") @Positive Integer size) {
        return service.findAll(userid, state, UserStatus.BOOKER, from, size);
    }

    @GetMapping("/owner")
    public List<BookingGettingDto> getAllByOwner(@RequestParam(name = "state", defaultValue = "ALL") State state,
                                                 @RequestHeader(HEADER_USER_ID) Long userid,
                                                 @RequestParam(value = "from", defaultValue = "0") @PositiveOrZero Integer from,
                                                 @RequestParam(value = "size", defaultValue = "20") @Positive Integer size) {
        return service.findAll(userid, state, UserStatus.OWNER, from, size);
    }
}