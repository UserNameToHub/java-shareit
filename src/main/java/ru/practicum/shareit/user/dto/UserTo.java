package ru.practicum.shareit.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.practicum.shareit.common.validationGroup.Create;
import ru.practicum.shareit.common.validationGroup.Update;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@EqualsAndHashCode(of = "id")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserTo {
    private Long id;

    @NotBlank(message = "Данное поле не может быть пустым.", groups = Create.class)
    @Email(message = "Данное поле должно содержать корректный email.", groups = {Update.class, Create.class})
    private String email;

    @NotBlank(message = "Данное поле не может быть пустым.", groups = Create.class)
    private String name;
}