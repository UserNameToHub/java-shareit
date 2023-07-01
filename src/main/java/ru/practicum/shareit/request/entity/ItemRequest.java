package ru.practicum.shareit.request.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.user.entity.User;

import java.time.LocalDateTime;

/**
 * TODO Sprint add-item-requests.
 */

@Data
@EqualsAndHashCode(of = "id")
public class ItemRequest {
    private Long id;
    private User requestor;
    private String description;
    private LocalDateTime created;
}