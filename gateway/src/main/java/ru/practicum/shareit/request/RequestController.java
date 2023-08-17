package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.RequestDto;
import ru.practicum.shareit.request.service.RequestService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import static ru.practicum.shareit.common.util.Constatns.HEADER_USER_ID;

@Controller
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
@Slf4j
public class RequestController {
    private final RequestService requestService;

    @PostMapping
    private ResponseEntity<Object> create(@RequestBody @Valid RequestDto request,
                                          @RequestHeader(HEADER_USER_ID) Long userId) {
        return requestService.create(request, userId);
    }

    @GetMapping
    private ResponseEntity<Object> getAllById(@RequestHeader(HEADER_USER_ID) Long userId) {
        return requestService.getAllById(userId);
    }

    @GetMapping("/all")
    private ResponseEntity<Object> getAll(@RequestParam(value = "from", defaultValue = "0") @PositiveOrZero Integer from,
                                    @RequestParam(value = "size", defaultValue = "10") @Positive Integer size,
                                    @RequestHeader(HEADER_USER_ID) long userId) {
        return requestService.getAll(from, size, userId);
    }

    @GetMapping("/{requestId}")
    private ResponseEntity<Object>  getById(@PathVariable("requestId") Long requestId,
                               @RequestHeader(HEADER_USER_ID) Long userId) {
        log.info("Get request for user with id {}", userId);
        return requestService.getById(requestId, userId);
    }
}
