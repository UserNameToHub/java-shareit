package ru.practicum.shareit.item.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
public class ItemGettingTo {
    private Long id;
    private String name;
    private String description;
    private Boolean available;
    private ItemBookingGettingTo lastBooking;
    private ItemBookingGettingTo nextBooking;
    private List<CommentGettingTo> comments;
}
