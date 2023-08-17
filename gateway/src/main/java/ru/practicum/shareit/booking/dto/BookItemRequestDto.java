package ru.practicum.shareit.booking.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookItemRequestDto {
	private long itemId;

	@NotNull
//	@JsonFormat(pattern = DATE_TIME_PATTERN)
	@FutureOrPresent
	private LocalDateTime start;

	@NotNull
//	@JsonFormat(pattern = DATE_TIME_PATTERN)
	@Future
	private LocalDateTime end;
}
