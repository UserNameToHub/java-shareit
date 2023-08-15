package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.RequestDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import static ru.practicum.shareit.common.util.Constatns.HEADER_USER_ID;

@Controller
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
@Slf4j
public class RequestController {
    private final RequestClient requestClient;

    @PostMapping
    private ResponseEntity<Object> create(@RequestBody @Valid RequestDto request,
                                          @RequestHeader(HEADER_USER_ID) Long userId) {
        log.info("Creating request");
        return requestClient.create(request, userId);
    }

    @GetMapping
    private ResponseEntity<Object> getAllById(@RequestHeader(HEADER_USER_ID) Long userId) {
        log.info("Get all requests for user with id {}", userId);
        return requestClient.getAllById(userId);
    }

    @GetMapping("/all")
    private ResponseEntity<Object> getAll(@RequestParam(value = "from", defaultValue = "0") @PositiveOrZero Integer from,
                                    @RequestParam(value = "size", defaultValue = "10") @Positive Integer size,
                                    @RequestHeader(HEADER_USER_ID) long userId) {
        log.info("Get all requests");
        return requestClient.getAll(from, size, userId);
    }

    @GetMapping("/{requestId}")
    private ResponseEntity<Object>  getById(@PathVariable("requestId") Long requestId,
                               @RequestHeader(HEADER_USER_ID) Long userId) {
        log.info("Get request for user with id {}", userId);
        return requestClient.getById(requestId, userId);
    }
}
