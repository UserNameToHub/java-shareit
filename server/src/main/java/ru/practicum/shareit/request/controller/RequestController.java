package ru.practicum.shareit.request.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.service.RequestService;
import ru.practicum.shareit.request.dto.RequestDto;

import java.util.List;

import static ru.practicum.shareit.util.Constants.HEADER_USER_ID;

/**
 * TODO Sprint add-item-requests.
 */

@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
public class RequestController {
    private final RequestService service;

    @PostMapping
    private RequestDto create(@RequestBody RequestDto request,
                              @RequestHeader(HEADER_USER_ID) Long userId) {
        return service.create(request, userId);
    }

    @GetMapping
    private List<RequestDto> getAllById(@RequestHeader(HEADER_USER_ID) Long userId) {
        return service.findIllById(userId);
    }

    @GetMapping("/all")
    private List<RequestDto> getAll(@RequestParam(value = "from", defaultValue = "0") Integer from,
                                    @RequestParam(value = "size", defaultValue = "1") Integer size,
                                    @RequestHeader(HEADER_USER_ID) long userId) {
        return service.getAll(from, size, userId);
    }

    @GetMapping("/{requestId}")
    private RequestDto getById(@PathVariable("requestId") Long requestId,
                               @RequestHeader(HEADER_USER_ID) Long userId) {
        return service.findById(requestId, userId);
    }
}
