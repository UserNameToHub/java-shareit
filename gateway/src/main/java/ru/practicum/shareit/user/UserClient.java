package ru.practicum.shareit.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.practicum.shareit.common.client.BaseClient;
import ru.practicum.shareit.user.dto.UserDto;

public class UserClient extends BaseClient {
    public UserClient(RestTemplate restTemplate) {
        super(restTemplate);
    }

    public ResponseEntity<Object> getAll() {
        return get("");
    }

    public ResponseEntity<Object> getById(Long id) {
        return get("/" + id);
    }

    public ResponseEntity<Object> create(UserDto user) {
        return post("", user);
    }

    public ResponseEntity<Object> edit(UserDto user, Long userId) {
        return patch("/" + userId, user);
    }

    public ResponseEntity<Object> delete(Long userId) {
        return delete("/" + userId);
    }
}
