package ru.practicum.shareit.booking.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.item.entity.Item;
import ru.practicum.shareit.user.entity.User;

import java.time.LocalDateTime;

/**
 * TODO Sprint add-bookings.
 */

@Data
@RequiredArgsConstructor
public class Booking {
    private Long id;
    private Item item;
    private User booker;
    private Status status;
    private LocalDateTime start;
    private LocalDateTime end;

    enum Status {
        WAITING,
        APPROVED,
        REJECTED,
        CANCELED;
    }
}
