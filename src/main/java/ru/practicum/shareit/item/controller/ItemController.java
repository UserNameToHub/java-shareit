package ru.practicum.shareit.item.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.common.validationGroup.Create;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.item.service.ItemServiceImpl;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import static ru.practicum.shareit.util.Constants.HEADER_USER_ID;

import java.util.List;

/**
 * TODO Sprint add-controllers.
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {
    private final ItemServiceImpl service;

    @GetMapping
    public List<ItemGettingTo> getAllById(@RequestHeader(HEADER_USER_ID) Long ownerId) {
        return service.findAllById(ownerId);
    }

    @GetMapping("/{id}")
    public ItemGettingTo getById(@PathVariable Long id,
                                 @RequestHeader(HEADER_USER_ID) Long ownerId) {
        return service.findById(id, ownerId);
    }

    @GetMapping("/search")
    public List<ItemTo> getByText(@NotBlank @RequestParam("text") String textRequest,
                                  @RequestHeader(HEADER_USER_ID) Long ownerId) {
        return service.findByText(textRequest, ownerId);
    }

    @PostMapping
    public ItemTo create(@Validated(Create.class) @RequestBody ItemTo itemTo,
                         @RequestHeader(HEADER_USER_ID) Long ownerId) {
        return service.create(itemTo, ownerId);
    }

    @PatchMapping("/{id}")
    public ItemTo edit(@RequestBody ItemTo itemTo,
                       @PathVariable Long id,
                       @RequestHeader(HEADER_USER_ID) Long ownerId) {
        return service.update(itemTo, id, ownerId);
    }

    @DeleteMapping("/{id}")
    public String delete(@Valid @PathVariable Long id,
                         @RequestHeader(HEADER_USER_ID) Long ownerId) {
        service.delete(id, ownerId);
        return String.format("Вещь с id %d был успешно удалена.", id);
    }

    @PostMapping("/{itemId}/comment")
    public CommentTo createComment(@Valid @RequestBody CommentTo comment,
                                   @PathVariable("itemId") Long itemId,
                                   @RequestHeader(HEADER_USER_ID) Long userId) {
        return service.createComment(comment, itemId, userId);
    }
}