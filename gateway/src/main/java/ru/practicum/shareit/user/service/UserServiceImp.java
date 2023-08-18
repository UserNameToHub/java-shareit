package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.UserClient;
import ru.practicum.shareit.user.dto.UserDto;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImp implements UserService {
    private final UserClient userClient;

    @Override
    public ResponseEntity<Object> getAll() {
        log.info("Get all users.");
        return userClient.getAll();
    }

    public ResponseEntity<Object> getById(Long id) {
        log.info("Get user with id {}", id);
        return userClient.getById(id);
    }

    public ResponseEntity<Object> create(UserDto user) {
        log.info("Creating user with name {}, email {}", user.getName(), user.getEmail());
        return userClient.create(user);
    }

    public ResponseEntity<Object> edit(UserDto user, Long userId) {
        log.info("Edit user with id {}", user);
        return userClient.edit(user, userId);
    }

    public ResponseEntity<Object> delete(Long userId) {
        log.info("Deleting user with id {}", userId);
        return userClient.delete(userId);
    }
}
