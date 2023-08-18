package ru.practicum.shareit.request;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.practicum.shareit.common.client.BaseClient;
import ru.practicum.shareit.request.dto.RequestDto;

import java.util.Map;

public class RequestClient extends BaseClient {
    public RequestClient(RestTemplate restTemplate) {
        super(restTemplate);
    }

    public ResponseEntity<Object> create(RequestDto request, Long userId) {
        return post("", userId, request);
    }

    public ResponseEntity<Object> getAll(Integer from, Integer size, Long userId) {
        Map<String, Object> parameters = Map.of("from", from, "size", size);
        return get("/all?from={from}&size={size}", userId, parameters);
    }

    public ResponseEntity<Object> getAllById(Long userId) {
        return get("", userId);
    }

    public ResponseEntity<Object> getById(Long requestId, Long userId) {
        return get("/" + requestId, userId);
    }
}
