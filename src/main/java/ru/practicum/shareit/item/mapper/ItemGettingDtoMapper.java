package ru.practicum.shareit.item.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.common.dto.BaseDtoMapper;
import ru.practicum.shareit.item.dto.ItemBookingGettingTo;
import ru.practicum.shareit.item.dto.ItemGettingTo;
import ru.practicum.shareit.item.entity.Item;
import ru.practicum.shareit.item.repository.CommentRepository;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ItemGettingDtoMapper implements BaseDtoMapper<Item, ItemGettingTo> {
    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;
    private final CommentGettingDtoMapper mapper;

    @Override
    public ItemGettingTo toDto(Item type) {
        List<ItemBookingGettingTo> lastBooking = bookingRepository.findLastById(type.getId(), LocalDateTime.now(),
                Pageable.ofSize(1));
        List<ItemBookingGettingTo> nextBooking = bookingRepository.findNextById(type.getId(), LocalDateTime.now(),
                Pageable.ofSize(1));

        if (lastBooking.isEmpty() || nextBooking.isEmpty()) {
            return toDto(type, false);
        }

        return ItemGettingTo.builder()
                .id(type.getId())
                .name(type.getName())
                .description(type.getDescription())
                .available(type.getAvailable())
                .lastBooking(lastBooking.get(0))
                .nextBooking(nextBooking.get(0))
                .comments(mapper.toDtoList(commentRepository.findAllByItem_Id(type.getId())))
                .build();
    }

    public ItemGettingTo toDto(Item type, boolean isOwner) {
        if (!isOwner) {
            return ItemGettingTo.builder()
                    .id(type.getId())
                    .name(type.getName())
                    .description(type.getDescription())
                    .available(type.getAvailable())
                    .comments(mapper.toDtoList(commentRepository.findAllByItem_Id(type.getId())))
                    .build();
        }

        return toDto(type);
    }
}