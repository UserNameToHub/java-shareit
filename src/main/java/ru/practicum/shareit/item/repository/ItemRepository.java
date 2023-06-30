package ru.practicum.shareit.item.repository;

import ru.practicum.shareit.common.repository.CrudRepository;
import ru.practicum.shareit.item.entity.Item;

import java.util.List;

public interface ItemRepository<ID> extends CrudRepository<Item, ID> {
    List<Item> findAllById(ID idOwner);

    @Override
    List<Item> findAll();

    List<Item> findByText(String text);
}
