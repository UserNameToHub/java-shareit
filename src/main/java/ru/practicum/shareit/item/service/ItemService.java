package ru.practicum.shareit.item.service;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.common.service.BaseService;
import ru.practicum.shareit.item.dto.CommentTo;
import ru.practicum.shareit.item.dto.ItemBookingGettingTo;
import ru.practicum.shareit.item.dto.ItemGettingTo;
import ru.practicum.shareit.item.dto.ItemTo;

import java.util.List;

public interface ItemService<ID> extends BaseService<ItemTo, ID> {
    ItemGettingTo findById(Long idType, Long userId);
    List<ItemGettingTo> findAllById(ID idOwner);

    List<ItemTo> findByText(String text);

    List<ItemTo> findByText(String text, Long owner);

    void delete(ID idType, ID idOwner);

    ItemTo update(ItemTo type, ID itemId, ID ownerId);

    ItemTo create(ItemTo type, ID ownerId);

    CommentTo createComment(CommentTo comment, Long itemId, Long userId);
}
