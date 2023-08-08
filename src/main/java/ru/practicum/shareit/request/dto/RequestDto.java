package ru.practicum.shareit.request.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.shareit.util.Constants.DATE_TIME_PATTERN;

/**
 * TODO Sprint add-item-requests.
 */

@Data
@Builder
public class RequestDto {
    private long id;

    private UserDto user;

    @NotBlank
    private String description;

    @JsonFormat(pattern = DATE_TIME_PATTERN)
    private LocalDateTime created;

    private List<ItemDto> items;
}