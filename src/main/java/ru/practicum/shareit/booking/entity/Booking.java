package ru.practicum.shareit.booking.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.practicum.shareit.item.entity.Item;
import ru.practicum.shareit.user.entity.User;

import java.time.LocalDateTime;

/**
 * TODO Sprint add-bookings.
 */

@Data
@EqualsAndHashCode(of = "id")
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