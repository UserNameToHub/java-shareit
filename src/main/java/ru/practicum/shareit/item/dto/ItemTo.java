package ru.practicum.shareit.item.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * TODO Sprint add-controllers.
 */

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemTo {
    private Long id;

    @NotBlank(message = "Данное поле не может быть пустым.")
    private String name;

    @NotBlank
    @Length(min = 1, max = 200)
    private String description;

    @NotNull(message = "Данное поле не может быть пустым.")
    private Boolean Available;
}