package ru.practicum.shareit.item.ItemService;

import org.springframework.http.ResponseEntity;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

public interface ItemService {
    public ResponseEntity<Object> getAllById(Long ownerId);

    public ResponseEntity<Object> getById(Long id, Long ownerId);

    public ResponseEntity<Object> getByText(String text, Long ownerId);

    public ResponseEntity<Object> create(ItemDto itemDto, Long ownerId);

    public ResponseEntity<Object> edit(ItemDto itemDto, Long id, Long ownerId);

    public ResponseEntity<Object> delete(Long id, Long ownerId);

    public ResponseEntity<Object> createComment(CommentDto commentDto, Long itemId, Long ownerId);
}
