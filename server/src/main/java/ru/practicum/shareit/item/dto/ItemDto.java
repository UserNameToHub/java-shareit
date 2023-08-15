package ru.practicum.shareit.item.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import ru.practicum.shareit.common.validationGroup.Create;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
public class ItemDto {
    private Long id;

    @NotBlank(message = "Данное поле не может быть пустым.", groups = Create.class)
    private String name;

    @NotBlank(message = "Данное поле не может быть пустым.", groups = Create.class)
    private String description;

    @NotNull(message = "Данное поле не может быть пустым.", groups = Create.class)
    private Boolean available;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long requestId;

    private ItemBookingGettingDto lastBooking;
    private ItemBookingGettingDto nextBooking;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<CommentDto> comments;
}
