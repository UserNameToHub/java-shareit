package ru.practicum.shareit.booking.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

import static ru.practicum.shareit.util.Constants.DATE_TIME_PATTERN;

/**
 * TODO Sprint add-bookings.
 */

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingCreatingDto {
    private Long bookerId;

    private Long itemId;

    @JsonFormat(pattern = DATE_TIME_PATTERN)
    private LocalDateTime start;

    @JsonFormat(pattern = DATE_TIME_PATTERN)
    private LocalDateTime end;
}