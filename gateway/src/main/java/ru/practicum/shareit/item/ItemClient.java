package ru.practicum.shareit.item;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.practicum.shareit.common.client.BaseClient;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.Map;

public class ItemClient extends BaseClient {
    public ItemClient(RestTemplate restTemplate) {
        super(restTemplate);
    }

    public ResponseEntity<Object> getAllById(Long ownerId) {
        return get("", ownerId);
    }

    public ResponseEntity<Object> getById(Long id, Long ownerId) {
        return get("/" + id, ownerId);
    }

    public ResponseEntity<Object> getByText(String text, Long ownerId) {
        Map<String, Object> parameters = Map.of("text", text);
        return get("/search?text={text}", ownerId, parameters);
    }

    public ResponseEntity<Object> create(ItemDto itemDto, Long ownerId) {
        return post("", ownerId, itemDto);
    }

    public ResponseEntity<Object> edit(ItemDto itemDto, Long id, Long ownerId) {
        return patch("/" + id, ownerId, itemDto);
    }

    public ResponseEntity<Object> delete(Long id, Long ownerId) {
        return delete("/" + id, ownerId);
    }

    public ResponseEntity<Object> createComment(CommentDto commentDto, Long itemId, Long ownerId) {
        return post("/" + itemId + "/comment", ownerId, commentDto);
    }
}
