package ru.practicum.shareit.item.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.common.validationGroup.Create;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.item.service.ItemServiceImpl;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

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
    public List<ItemDto> getAllById(@RequestHeader(HEADER_USER_ID) Long ownerId,
                                    @RequestParam(value = "from", defaultValue = "0") @PositiveOrZero Integer from,
                                    @RequestParam(value = "size", defaultValue = "20") @Positive Integer size) {
        return service.findAllById(ownerId, List.of(from, size));
    }

    @GetMapping("/{id}")
    public ItemDto getById(@PathVariable Long id,
                           @RequestHeader(HEADER_USER_ID) Long ownerId) {
        return service.findById(id, ownerId);
    }

    @GetMapping("/search")
    public List<ItemDto> getByText(@NotBlank @RequestParam("text") String textRequest,
                                   @RequestHeader(HEADER_USER_ID) Long ownerId,
                                   @RequestParam(value = "from", defaultValue = "0") @PositiveOrZero Integer from,
                                   @RequestParam(value = "size", defaultValue = "20") @Positive Integer size) {
        return service.findByText(textRequest, ownerId, List.of(from, size));
    }

    @PostMapping
    public ItemDto create(@Validated(Create.class) @RequestBody ItemDto itemDto,
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
    public String delete(@Valid @PathVariable Long id,
                         @RequestHeader(HEADER_USER_ID) Long ownerId) {
        service.delete(id, ownerId);
        return String.format("Вещь с id %d был успешно удалена.", id);
    }

    @PostMapping("/{itemId}/comment")
    public CommentDto createComment(@Valid @RequestBody CommentDto comment,
                                    @PathVariable("itemId") Long itemId,
                                    @RequestHeader(HEADER_USER_ID) Long userId) {
        return service.createComment(comment, itemId, userId);
    }
}