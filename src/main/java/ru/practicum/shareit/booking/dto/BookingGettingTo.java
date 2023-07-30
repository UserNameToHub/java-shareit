package ru.practicum.shareit.booking.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.booking.enumeration.Status;

import java.time.LocalDateTime;

@Data
@Builder
public class BookingGettingTo {
    private Long id;
    private LocalDateTime start;
    private LocalDateTime end;
    private BookingUserGettingTo booker;
    private BookingItemGettingTo item;
    private Status status;
}
