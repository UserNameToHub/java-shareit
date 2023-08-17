package ru.practicum.shareit.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;

/**
 * TODO Sprint add-controllers.
 */

@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @GetMapping
    public List<UserDto> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public UserDto getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public UserDto create(@RequestBody UserDto user) {
        return service.create(user);
    }

    @PatchMapping("/{id}")
    public UserDto edit(@RequestBody UserDto user, @PathVariable Long id) {
        return service.update(user, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        service.delete(id);
//        return String.format("Пользователь c id %d был успешно удален.", id);
            return ResponseEntity.status(200).build();
    }
}