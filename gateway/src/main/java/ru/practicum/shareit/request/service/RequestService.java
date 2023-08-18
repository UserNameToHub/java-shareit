package ru.practicum.shareit.request.service;

import org.springframework.http.ResponseEntity;
import ru.practicum.shareit.request.dto.RequestDto;

public interface RequestService {
    public ResponseEntity<Object> create(RequestDto request, Long userId);

    public ResponseEntity<Object> getAll(Integer from, Integer size, Long userId);

    public ResponseEntity<Object> getAllById(Long userId);

    public ResponseEntity<Object> getById(Long requestId, Long userId);
}
