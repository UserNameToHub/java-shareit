package ru.practicum.shareit.booking.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookingItemGettingTo {
    public Long id;
    public String name;
}
