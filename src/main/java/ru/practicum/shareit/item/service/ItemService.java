package ru.practicum.shareit.item.service;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.common.service.BaseService;
import ru.practicum.shareit.item.dto.ItemTo;

import java.util.List;

@Service
public interface ItemService<ID> extends BaseService<ItemTo, ID> {
    List<ItemTo> findAllById(ID idOwner);
    List<ItemTo> findByText(String text);
    void delete(ID idType, ID idOwner);
    ItemTo update(ItemTo type, ID itemId, ID ownerId);
    ItemTo create(ItemTo type, ID ownerId);
}

