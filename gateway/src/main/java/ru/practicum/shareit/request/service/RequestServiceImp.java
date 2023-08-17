package ru.practicum.shareit.request.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.request.RequestClient;
import ru.practicum.shareit.request.dto.RequestDto;

import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class RequestServiceImp implements RequestService {
    private final RequestClient requestClient;

    public ResponseEntity<Object> create(RequestDto request, Long userId) {
        log.info("Creating request");
        return requestClient.create(request, userId);
    }

    public ResponseEntity<Object> getAll(Integer from, Integer size, Long userId) {
        Map<String, Object> parameters = Map.of("from", from, "size", size);
        log.info("Get all requests");
        return requestClient.getAll(from, size, userId);
    }

    public ResponseEntity<Object> getAllById(Long userId) {
        log.info("Get all requests for user with id {}", userId);
        return requestClient.getAllById(userId);
    }

    public ResponseEntity<Object> getById(Long requestId, Long userId) {
        log.info("Get all requests");
        return requestClient.getById(requestId, userId);
    }
}
