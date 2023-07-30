package ru.practicum.shareit.item.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.common.validationGroup.Create;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * TODO Sprint add-controllers.
 */

@Data
@Builder
public class ItemTo {
    private Long id;

    @NotBlank(message = "Данное поле не может быть пустым.", groups = Create.class)
    private String name;

    @NotBlank(message = "Данное поле не может быть пустым.", groups = Create.class)
    private String description;

    @NotNull(message = "Данное поле не может быть пустым.", groups = Create.class)
    private Boolean available;

    private ItemBookingGettingTo lastBooking;
    private ItemBookingGettingTo nextBooking;
}