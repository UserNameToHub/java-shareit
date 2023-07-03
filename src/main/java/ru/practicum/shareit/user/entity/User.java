package ru.practicum.shareit.user.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.practicum.shareit.item.entity.Item;

import java.util.Set;

/**
 * TODO Sprint add-controllers.
 */
@Data
@Builder
@EqualsAndHashCode(of = "id")
public class User {
    private Long id;
    private String email;
    private String name;
    private final Set<Item> items;
}