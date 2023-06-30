package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.common.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemTo;
import ru.practicum.shareit.item.entity.Item;
import ru.practicum.shareit.item.mapper.ItemDtoMapper;
import ru.practicum.shareit.item.repository.ItemRepositoryImpl;
import ru.practicum.shareit.user.entity.User;
import ru.practicum.shareit.user.repository.UserRepositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemServiceImpl implements ItemService<Long> {
    private final ItemRepositoryImpl repository;
    private final UserRepositoryImpl userRepository;
    private final ItemDtoMapper mapper;
    private Long id = 1L;

    @Override
    public List<ItemTo> findAllById(Long idOwner) {
        log.info("Запрос на получение всех вещей для пользователя с id {}", idOwner);
        if (!userRepository.existsById(idOwner)) {
            throw new NotFoundException(String.format("Пользователь с id %d не найден.", idOwner));
        }

        return repository.findAllById(idOwner).stream()
                .map(item -> mapper.toTo(item))
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemTo> findByText(String text) {
        log.info("Запрос на получение вещей, содержащих текст \" {} \" в названии или описании.", text);
        if (text.isEmpty()) {
            return new ArrayList<>();
        }
        return repository.findByText(text).stream()
                .map(item -> mapper.toTo(item))
                .collect(Collectors.toList());
    }

    @Override
    public ItemTo findById(Long idType) {
        log.info("Запрос на получение вещи по id {}.", idType);
        return mapper.toTo(repository.findById(idType).orElseThrow(() ->
                new NotFoundException(String.format("Вещь с id %d не найдена.", idType))));
    }

    @Override
    public void delete(Long idType, Long idOwner) {
        log.info("Запрос на удаление вещи с id {}, владелец которой имеет id {}.", idType, idOwner);
        if (userRepository.existsById(idOwner)) {
            throw new NotFoundException(String.format("Пользователь с id %d не найден.", idOwner));
        }

        if (repository.existsById(idType)) {
            throw new NotFoundException(String.format("Вещь с id %d не найдена.", idType));
        }

        checkOwner(idOwner, idType);

        repository.delete(idType);
    }

    @Override
    public ItemTo update(ItemTo type, Long itemId, Long ownerId) {
        log.info("Запрос на обновление вещи с id {}, владелец которой имеет id {}.", itemId, ownerId);
        if (!userRepository.existsById(ownerId)) {
            throw new NotFoundException(String.format("Пользователь с id %d не найден.", ownerId));
        }

        if (!repository.existsById(itemId)) {
            throw new NotFoundException(String.format("Вещь с id %d не найдена.", itemId));
        }

        checkOwner(ownerId, itemId);

        Item item = mapper.toData(type);
        item.setId(itemId);
        return mapper.toTo(repository.update(item));
    }

    @Override
    public ItemTo create(ItemTo type, Long ownerId) {
        log.info("Запрос на осздание вещи с id {}", ownerId);
        Item item = mapper.toData(type);
        item.setOwner(userRepository.findById(ownerId).orElseThrow(() ->
                new NotFoundException(String.format("Пользователь с id %d не найден.", ownerId))));
        item.setId(id++);
        return mapper.toTo(repository.create(item));
    }

    private void checkOwner(Long ownerId, Long itemId) {
        if (repository.findById(itemId).get().getOwner().getId().longValue() != itemId.longValue()) {
            throw new NotFoundException(String.format("Пользователь с id %d не является владельцем вещи с id %d.",
                    ownerId, itemId));
        }
    }
}