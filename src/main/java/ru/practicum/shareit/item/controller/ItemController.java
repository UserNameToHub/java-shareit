package ru.practicum.shareit.item.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemTo;
import ru.practicum.shareit.item.service.ItemServiceImpl;

import javax.validation.Valid;

import static ru.practicum.shareit.util.Constants.HEADER_USER_ID;

import java.util.List;

/**
 * TODO Sprint add-controllers.
 */

@RequiredArgsConstructor
@RestController
@RequestMapping("/items")
public class ItemController {
    private final ItemServiceImpl service;

    @GetMapping
    public List<ItemTo> getAllById(@RequestHeader(HEADER_USER_ID) Long ownerId) {
        return service.findAllById(ownerId);
    }

    @GetMapping("/{id}")
    public ItemTo getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping("/search")
    public List<ItemTo> getByText(@RequestParam("text") String textRequest) {
        return service.findByText(textRequest);
    }

    @PostMapping
    public ItemTo create(@Valid @RequestBody ItemTo itemTo, @RequestHeader(HEADER_USER_ID) Long ownerId) {
        return service.create(itemTo, ownerId);
    }

    @PatchMapping("/{id}")
    public ItemTo edit(@RequestBody ItemTo itemTo, @PathVariable Long id, @RequestHeader(HEADER_USER_ID) Long ownerId) {
        return service.update(itemTo, id, ownerId);
    }

    @DeleteMapping("/{id}")
    public String delete(@Valid @PathVariable Long id, @RequestHeader(HEADER_USER_ID) Long ownerId) {
        service.delete(id, ownerId);
        return String.format("Вещь с id %d был успешно удалена.", id);
    }
}