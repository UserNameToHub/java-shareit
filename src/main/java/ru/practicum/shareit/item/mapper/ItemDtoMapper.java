package ru.practicum.shareit.item.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.common.dto.BaseDtoMapper;
import ru.practicum.shareit.item.dto.ItemBookingGettingDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.entity.Item;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.request.entity.ItemRequest;
import ru.practicum.shareit.user.entity.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static ru.practicum.shareit.util.Constants.ORDER_BY_DATE_ASC;
import static ru.practicum.shareit.util.Constants.ORDER_BY_DATE_DESC;

@Component
@RequiredArgsConstructor
public class ItemDtoMapper implements BaseDtoMapper<Item, ItemDto> {
    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;
    private final CommentDtoMapper mapper;

    @Override
    public ItemDto toDto(Item type) {
        List<ItemBookingGettingDto> lastBooking = bookingRepository.findLastById(type.getId(), LocalDateTime.now(),
                PageRequest.of(0, 1, ORDER_BY_DATE_DESC));
        List<ItemBookingGettingDto> nextBooking = bookingRepository.findNextById(type.getId(), LocalDateTime.now(),
                PageRequest.of(0, 1, ORDER_BY_DATE_ASC));

        if (lastBooking.isEmpty() && nextBooking.isEmpty()) {
            return toDto(type, false);
        }

        return ItemDto.builder()
                .id(type.getId())
                .name(type.getName())
                .description(type.getDescription())
                .available(type.getAvailable())
                .lastBooking(lastBooking.size() > 0 ? lastBooking.get(0) : null)
                .nextBooking(nextBooking.size() > 0 ? nextBooking.get(0) : null)
                .comments(mapper.toDtoList(commentRepository.findAllByItem_Id(type.getId())))
                .requestId(Objects.nonNull(type.getRequest()) ? type.getRequest().getId() : null)
                .build();
    }

    public ItemDto toDto(Item type, boolean isOwner) {
        if (!isOwner) {
            return ItemDto.builder()
                    .id(type.getId())
                    .name(type.getName())
                    .description(type.getDescription())
                    .available(type.getAvailable())
                    .comments(mapper.toDtoList(commentRepository.findAllByItem_Id(type.getId())))
                    .requestId(Objects.nonNull(type.getRequest()) ? type.getRequest().getId() : null)
                    .build();
        }

        return toDto(type);
    }

    public Item toEntity(ItemDto type, ItemRequest request, User owner) {
        return Item.builder()
                .id(type.getId())
                .name(type.getName())
                .description(type.getDescription())
                .available(type.getAvailable())
                .request(request)
                .owner(owner)
                .build();
    }
}