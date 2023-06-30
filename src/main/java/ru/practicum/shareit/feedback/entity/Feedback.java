package ru.practicum.shareit.feedback.entity;

import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.item.entity.Item;
import ru.practicum.shareit.user.entity.User;

@Data
@Builder
public class Feedback {
    private Long id;
    private User user;
    private Item item;
    private String text;
}
