package ru.practicum.shareit.request.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.user.entity.User;

import java.time.LocalDateTime;

/**
 * TODO Sprint add-item-requests.
 */

@Data
@RequiredArgsConstructor
public class ItemRequest {
    private final Long id;
    private User requestor;
    private String description;
    private LocalDateTime created;
}
