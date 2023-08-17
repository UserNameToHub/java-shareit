package ru.practicum.shareit.user.service;

import org.springframework.http.ResponseEntity;
import ru.practicum.shareit.user.dto.UserDto;

public interface UserService {
    ResponseEntity<Object> getAll();

    ResponseEntity<Object> getById(Long id);

    ResponseEntity<Object> create(UserDto user);

    ResponseEntity<Object> edit(UserDto user, Long userId);

    ResponseEntity<Object> delete(Long userId);
}
