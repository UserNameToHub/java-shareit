package ru.practicum.shareit.item.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Data
@Builder
public class ItemDto {
    private Long id;

    private String name;

    private String description;

    private Boolean available;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long requestId;

    private ItemBookingGettingDto lastBooking;
    private ItemBookingGettingDto nextBooking;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<CommentDto> comments;
}
