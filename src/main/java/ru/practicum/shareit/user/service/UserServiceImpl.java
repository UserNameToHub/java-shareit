package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.common.exception.NotFoundException;
import ru.practicum.shareit.common.exception.NotUniqueException;
import ru.practicum.shareit.item.entity.Item;
import ru.practicum.shareit.item.repository.ItemRepositoryImpl;
import ru.practicum.shareit.user.dto.UserTo;
import ru.practicum.shareit.user.mapper.UserDtoMapper;
import ru.practicum.shareit.user.entity.User;
import ru.practicum.shareit.user.repository.UserRepositoryImpl;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService<Long> {
    private final UserRepositoryImpl userRepository;
    private final ItemRepositoryImpl itemRepository;
    private final UserDtoMapper mapper;

    private static Long id = 1l;

    @Override
    public List<UserTo> findAll() {
        log.info("Запрос на получение всех пользователей.");
        return userRepository.findAll().stream()
                .map(user -> mapper.toTo(user))
                .collect(Collectors.toList());
    }

    @Override
    public UserTo findById(Long idType) {
        log.info("Запрос на получение пользователя с id {}.", idType);
        return mapper.toTo(userRepository.findById(idType).orElseThrow(() ->
                new NotFoundException(String.format("Пользователь с id %d не найден", idType))));
    }

    @Override
    public void delete(Long idType) {
        log.info("Запрос на удаление пользователя с id {}.", idType);
        if (!userRepository.existsById(idType)) {
            throw new NotFoundException(String.format("Пользователь с id %d не найден", idType));
        }

        List<Item> allItems = itemRepository.findAllById(idType);
        allItems.stream()
                .map(item -> item.getId())
                .forEach(itemRepository::delete);
        userRepository.delete(idType);
    }

    @Override
    public UserTo update(UserTo type, Long idType) {
        log.info("Запрос на обновление пользователя с id {}.", idType);
        if (!userRepository.existsById(idType)) {
            throw new NotFoundException(String.format("Пользователь с id %d не найден", idType));
        }

        if (!Objects.isNull(type.getEmail()) &&
                !userRepository.findById(idType).get().getEmail().equals(type.getEmail())) {
            if (!userRepository.isUnique(type.getEmail())) {
                throw new NotUniqueException(String.format("Пользователь с email: %s уже существует.", type.getEmail()));
            }
        }

        User user = mapper.toData(type);
        user.setId(idType);
        return mapper.toTo(userRepository.update(user));
    }

    @Override
    public UserTo create(UserTo type) {
        log.info("Запрос на создание пользователя с id {}.", id);
        if (!userRepository.isUnique(type.getEmail())) {
            throw new NotUniqueException(String.format("Пользователь с email: %s уже существует.", type.getEmail()));
        }

        User user = mapper.toData(type);
        user.setId(id++);
        return mapper.toTo(userRepository.create(user));
    }
}