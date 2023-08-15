package ru.practicum.shareit.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.common.client.BaseClient;
import ru.practicum.shareit.user.dto.UserDto;

@Component
public class UserClient extends BaseClient {
    private static final String API_PREFIX = "/users";

    @Autowired
    public UserClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                .build());
    }

    ResponseEntity<Object> getAll() {
        return getAll();
    }

    ResponseEntity<Object> getById(Long id) {
        return get("/" + id);
    }

    ResponseEntity<Object> create(UserDto user) {
        return post("", user);
    }

    ResponseEntity<Object> edit(UserDto user, Long userId) {
        return patch("/" + userId, user);
    }

    ResponseEntity<Object> delete(Long userId) {
        return delete("/" + userId);
    }
}
