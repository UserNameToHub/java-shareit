package ru.practicum.shareit.item.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.item.service.ItemService;


import static ru.practicum.shareit.util.Constants.HEADER_USER_ID;

import java.util.List;

/**
 * TODO Sprint add-controllers.
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {
    private final ItemService service;

    @GetMapping
    public List<ItemDto> getAllById(@RequestHeader(HEADER_USER_ID) Long ownerId,
                                    @RequestParam(value = "from", defaultValue = "0")  Integer from,
                                    @RequestParam(value = "size", defaultValue = "20") Integer size) {
        return service.findAllById(ownerId, List.of(from, size));
    }

    @GetMapping("/{id}")
    public ItemDto getById(@PathVariable Long id,
                           @RequestHeader(HEADER_USER_ID) Long ownerId) {
        return service.findById(id, ownerId);
    }

    @GetMapping("/search")
    public List<ItemDto> getByText(@RequestParam("text") String textRequest,
                                   @RequestHeader(HEADER_USER_ID) Long ownerId,
                                   @RequestParam(value = "from", defaultValue = "0") Integer from,
                                   @RequestParam(value = "size", defaultValue = "20") Integer size) {
        return service.findByText(textRequest, ownerId, List.of(from, size));
    }

    @PostMapping
    public ItemDto create(@RequestBody ItemDto itemDto,
                          @RequestHeader(HEADER_USER_ID) Long ownerId) {
        return service.create(itemDto, ownerId);
    }

    @PatchMapping("/{id}")
    public ItemDto edit(@RequestBody ItemDto itemDto,
                        @PathVariable Long id,
                        @RequestHeader(HEADER_USER_ID) Long ownerId) {
        return service.update(itemDto, id, ownerId);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id,
                         @RequestHeader(HEADER_USER_ID) Long ownerId) {
        service.delete(id, ownerId);
        return String.format("Вещь с id %d был успешно удалена.", id);
    }

    @PostMapping("/{itemId}/comment")
    public CommentDto createComment(@RequestBody CommentDto comment,
                                    @PathVariable("itemId") Long itemId,
                                    @RequestHeader(HEADER_USER_ID) Long userId) {
        return service.createComment(comment, itemId, userId);
    }
}