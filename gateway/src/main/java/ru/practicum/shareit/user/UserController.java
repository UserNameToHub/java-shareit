package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.common.validation.validationGroup.Create;
import ru.practicum.shareit.common.validation.validationGroup.Update;

@Controller
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Slf4j
@ControllerAdvice(value = "GatewayErrorHandler")
public class UserController {
    private final UserClient userClient;

    @GetMapping
    public ResponseEntity<Object> getAll() {
        log.info("Get all users.");
        return userClient.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        log.info("Get user with id {}", id);
        return userClient.getById(id);
    }

    @PostMapping
    public ResponseEntity<Object> create(@Validated(Create.class) @RequestBody UserDto user) {
        log.info("Creating user with name {}, email {}", user.getName(), user.getEmail());
        return userClient.create(user);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> edit(@Validated(Update.class) @RequestBody UserDto user, @PathVariable Long id) {
        log.info("Editing user.");
        return userClient.edit(user, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        log.info("Deleting user with id {}", id);
        userClient.delete(id);
    }
}