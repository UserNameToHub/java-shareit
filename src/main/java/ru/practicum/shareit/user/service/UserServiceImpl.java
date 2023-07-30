package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.common.exception.NotFoundException;
import ru.practicum.shareit.common.exception.NotUniqueException;
import ru.practicum.shareit.user.dto.UserTo;
import ru.practicum.shareit.user.entity.User;
import ru.practicum.shareit.user.mapper.UserDtoMapper;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService<Long> {
    private final UserRepository repository;
    private final UserDtoMapper mapper;

    @Override
    public List<UserTo> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete(Long idType) {
        try {
            repository.deleteById(idType);
        } catch (DataIntegrityViolationException e) {
            throw new NotFoundException(String.format("Операция удаления не выполнена, т.к. пользователь с id %d не найден.",
                    idType));
        }
    }

    @Override
    @Transactional
    public UserTo update(UserTo type, Long idType) {
        User user = repository.findById(idType).orElseThrow(() ->
                new NotFoundException(String.format("Пользователь с id %d не найден.", idType)));

        try {
            updateField(type, user);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e.getMessage());
        }

        try {
            repository.saveAndFlush(user);
        } catch (DataIntegrityViolationException e) {
            e.getMessage();
        }

        return mapper.toDto(user);
    }

    @Override
    @Transactional
    public UserTo create(UserTo type) {
        try {
            return mapper.toDto(repository.save(mapper.toEntity(type)));
        } catch (DataIntegrityViolationException e) {
            throw new NotUniqueException(String.format("Пользователь с %s уже зарегестрирован.", type.getEmail()));
        }
    }

    @Override
    public UserTo findById(Long idType) {
        return mapper.toDto(repository.findById(idType).orElseThrow(() ->
                new NotFoundException(String.format("Пользователь с id %d не найден.", idType))));
    }
}