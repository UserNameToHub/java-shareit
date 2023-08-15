package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.common.validation.validationGroup.Create;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import static ru.practicum.shareit.common.util.Constatns.HEADER_USER_ID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/items")
@Slf4j
public class ItemController {
    private final ItemClient itemClient;

    @GetMapping
    public ResponseEntity<Object> getAllById(@RequestHeader(HEADER_USER_ID) Long ownerId) {
        log.info("Get all items for user with id {}.", ownerId);
        return itemClient.getAllById(ownerId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id,
                                          @RequestHeader(HEADER_USER_ID) Long ownerId) {
        log.info("Get item with id {}.", id);
        return itemClient.getById(id, ownerId);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> getByText(@NotBlank @RequestParam("text") String textRequest,
                                            @RequestHeader(HEADER_USER_ID) Long ownerId) {
        log.info("Get all items who contains text {}.", textRequest);
        return itemClient.getByText(textRequest, ownerId);
    }

    @PostMapping
    public ResponseEntity<Object> create(@Validated(Create.class) @RequestBody ItemDto itemDto,
                                         @RequestHeader(HEADER_USER_ID) Long ownerId) {
        log.info("Creating item with name {}, description {}.", itemDto.getName(), itemDto.getDescription());
        return itemClient.create(itemDto, ownerId);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> edit(@RequestBody ItemDto itemDto,
                                       @PathVariable Long id,
                                       @RequestHeader(HEADER_USER_ID) Long ownerId) {
        log.info("Editing items.");
        return itemClient.edit(itemDto, id, ownerId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@Valid @PathVariable Long id,
                                         @RequestHeader(HEADER_USER_ID) Long ownerId) {
        log.info("Deleting item with id {}", id);
        return itemClient.delete(id, ownerId);
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> createComment(@Valid @RequestBody CommentDto comment,
                                                @PathVariable("itemId") Long itemId,
                                                @RequestHeader(HEADER_USER_ID) Long userId) {
        log.info("Creating comment for item with id {}", itemId);
        return itemClient.createComment(comment, itemId, userId);
    }
}