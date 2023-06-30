package ru.practicum.shareit.item.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.entity.Item;

import java.util.*;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepository<Long> {
    private final Map<Long, Item> repository = new HashMap<>();

    @Override
    public List<Item> findAll() {
        return repository.values().stream().collect(Collectors.toList());
    }

    @Override
    public List<Item> findAllById(Long idOwner) {

        return repository.values().stream()
                .filter(item -> item.getOwner().getId().compareTo(idOwner) != 0)
                .collect(Collectors.toList());
    }

    @Override
    public List<Item> findByText(String text) {
        return repository.values().stream()
                .filter(Item::getAvailable)
                .filter(item -> item.getName().toLowerCase().contains(text.toLowerCase()) ||
                        item.getDescription().toLowerCase().contains(text.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Item> findById(Long idType) {
        return repository.containsKey(idType) ? Optional.of(repository.get(idType)) : Optional.empty();
    }

    @Override
    public void delete(Long idType) {
        repository.remove(idType);
    }

    @Override
    public Item update(Item type) {
        Item updatingItem = repository.get(type.getId());

        if (Objects.nonNull(type.getId())) {
            updatingItem.setId(type.getId());
        }
        if (Objects.nonNull(type.getName())) {
            updatingItem.setName(type.getName());
        }
        if (Objects.nonNull(type.getDescription())) {
            updatingItem.setDescription(type.getDescription());
        }
        if (Objects.nonNull(type.getAvailable())) {
            updatingItem.setAvailable(type.getAvailable());
        }

        return updatingItem;
    }

    @Override
    public Item create(Item type) {
        repository.put(type.getId(), type);
        return type;
    }

    @Override
    public boolean existsById(Long idType) {
        return repository.containsKey(idType);
    }
}