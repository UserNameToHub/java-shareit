package ru.practicum.shareit.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.common.validationGroup.Create;
import ru.practicum.shareit.common.validationGroup.Update;
import ru.practicum.shareit.user.dto.UserTo;
import ru.practicum.shareit.user.service.UserServiceImpl;

import java.util.List;

/**
 * TODO Sprint add-controllers.
 */

@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl service;

    @GetMapping
    public List<UserTo> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public UserTo getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public UserTo create(@Validated(Create.class) @RequestBody UserTo user) {
        return service.create(user);
    }

    @PatchMapping("/{id}")
    public UserTo edit(@Validated(Update.class) @RequestBody UserTo user, @PathVariable Long id) {
        return service.update(user, id);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return String.format("Пользователь c id %d был успешно удален.", id);
    }
}