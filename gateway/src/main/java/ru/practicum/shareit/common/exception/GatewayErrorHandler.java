package ru.practicum.shareit.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@ControllerAdvice
public class GatewayErrorHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<?> handleException(MethodArgumentNotValidException ex, WebRequest request) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("errorMessage", ex.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<?> handleException(IllegalArgumentException ex, WebRequest request) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", ex.getMessage()));
    }
}
