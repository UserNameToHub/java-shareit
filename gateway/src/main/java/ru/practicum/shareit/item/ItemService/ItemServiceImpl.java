package ru.practicum.shareit.item.ItemService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.ItemClient;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService{
    private final ItemClient itemClient;

    public ResponseEntity<Object> getAllById(Long ownerId) {
        log.info("Get all items for user with id {}.", ownerId);
        return itemClient.getAllById(ownerId);
    }

    public ResponseEntity<Object> getById(Long id, Long ownerId) {
        log.info("Get item with id {}.", id);
        return itemClient.getById(id, ownerId);
    }

    public ResponseEntity<Object> getByText(String text, Long ownerId) {
        log.info("Get all items who contains text {}.", text);
        return itemClient.getByText(text, ownerId);
    }

    public ResponseEntity<Object> create(ItemDto itemDto, Long ownerId) {
        log.info("Creating item with name {}, description {}.", itemDto.getName(), itemDto.getDescription());
        return itemClient.create(itemDto, ownerId);
    }

    public ResponseEntity<Object> edit(ItemDto itemDto, Long id, Long ownerId) {
        log.info("Editing items.");
        return itemClient.edit(itemDto, id, ownerId);
    }

    public ResponseEntity<Object> delete(Long id, Long ownerId) {
        log.info("Deleting item with id {}", id);
        return itemClient.delete(id, ownerId);
    }

    public ResponseEntity<Object> createComment(CommentDto commentDto, Long itemId, Long ownerId) {
        return itemClient.createComment(commentDto, itemId, ownerId);
    }
}
