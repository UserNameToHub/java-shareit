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
import ru.practicum.shareit.user.service.UserService;

@Controller
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Slf4j
@ControllerAdvice(value = "GatewayErrorHandler")
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<Object> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        return userService.getById(id);
    }

    @PostMapping
    public ResponseEntity<Object> create(@Validated(Create.class) @RequestBody UserDto user) {
        return userService.create(user);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> edit(@Validated(Update.class) @RequestBody UserDto user, @PathVariable Long id) {
        log.info("Editing user.");
        return userService.edit(user, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object>  delete(@PathVariable Long id) {
        return userService.delete(id);
    }
}