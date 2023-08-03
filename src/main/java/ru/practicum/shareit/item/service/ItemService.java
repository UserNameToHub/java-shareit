package ru.practicum.shareit.item.service;

import ru.practicum.shareit.common.service.BaseService;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

public interface ItemService<ID> extends BaseService<ItemDto, ID> {
    ItemDto findById(Long idType, Long userId);

    List<ItemDto> findAllById(ID idOwner);

    List<ItemDto> findByText(String text);

    List<ItemDto> findByText(String text, Long owner);

    void delete(ID idType, ID idOwner);

    ItemDto update(ItemDto type, ID itemId, ID ownerId);

    ItemDto create(ItemDto type, ID ownerId);

    CommentDto createComment(CommentDto comment, Long itemId, Long userId);
}