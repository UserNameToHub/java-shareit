package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.common.client.BaseClient;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.Map;

@Service
public class ItemClient extends BaseClient {
    private static final String API_PREFIX = "/items";

    @Autowired
    public ItemClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
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
