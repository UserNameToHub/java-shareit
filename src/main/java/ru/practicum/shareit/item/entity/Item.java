package ru.practicum.shareit.item.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.practicum.shareit.feedback.entity.Feedback;
import ru.practicum.shareit.request.entity.ItemRequest;
import ru.practicum.shareit.user.entity.User;

import java.util.Set;

/**
 * TODO Sprint add-controllers.
 */

@Data
@Builder
@EqualsAndHashCode(of = "id")
public class Item {
    private Long id;
    private User owner;
    private String name;
    private String description;
    private Boolean available;
    private ItemRequest request;
    private Set<Feedback> feedbacks;
}