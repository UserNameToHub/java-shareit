package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.common.exception.NotFoundException;
import ru.practicum.shareit.common.exception.ReflectionException;
import ru.practicum.shareit.common.exception.UnavailableException;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.item.entity.Comment;
import ru.practicum.shareit.item.entity.Item;
import ru.practicum.shareit.item.mapper.CommentDtoMapper;
import ru.practicum.shareit.item.mapper.ItemDtoMapper;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.request.RequestRepository;
import ru.practicum.shareit.request.entity.ItemRequest;
import ru.practicum.shareit.user.entity.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static ru.practicum.shareit.util.Constants.ORDER_BY_ID_ASC;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemServiceImpl implements ItemService<Long> {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;
    private final CommentDtoMapper commentDtoMapper;
    private final ItemDtoMapper gettingDtoMapper;

    private final RequestRepository requestRepository;

    @Override
    public ItemDto findById(Long idType, Long userId) {
        Item item = itemRepository.findById(idType).orElseThrow(() ->
                new NotFoundException(String.format("Вещь с id %d не найдена.", idType)));

        return gettingDtoMapper.toDto(item, itemRepository.existsByIdAndOwnerId(idType, userId));
    }

    @Override
    public List<ItemDto> findAllById(Long idOwner, List<Integer> pageParam) {
        if (!userRepository.existsById(idOwner)) {
            throw new NotFoundException(String.format("Пользователь с id %d не найден.", idOwner));
        }

        return gettingDtoMapper.toDtoList(itemRepository.findAllByOwnerId(idOwner, PageRequest.of(pageParam.get(0),
                pageParam.get(1), ORDER_BY_ID_ASC)).toList());
    }

    @Override
    public List<ItemDto> findByText(String text, List<Integer> pageParam) {
        return gettingDtoMapper.toDtoList(itemRepository.findByNameOrDescriptionText(text, PageRequest.of(pageParam.get(0),
                pageParam.get(1), ORDER_BY_ID_ASC)).toList());
    }

    @Override
    public List<ItemDto> findByText(String text, Long owner, List<Integer> pageParam) {
        return gettingDtoMapper.toDtoList(itemRepository.findByNameOrDescriptionText(text, PageRequest.of(pageParam.get(0),
                pageParam.get(1), ORDER_BY_ID_ASC)).toList());
    }

    @Override
    @Transactional
    public void delete(Long idType, Long idOwner) {
        itemRepository.deleteByOwnerId(idType, idOwner);
    }

    @Override
    @Transactional
    public ItemDto update(ItemDto type, Long itemId, Long ownerId) {
        if (!userRepository.existsById(ownerId)) {
            throw new NotFoundException(String.format("Пользователь с id %d не найден.", ownerId));
        }

        Item item = itemRepository.findById(itemId).orElseThrow(() ->
                new NotFoundException(String.format("Вещь с id %d не найдена.", itemId)));

        if (!itemRepository.existsByIdAndOwnerId(itemId, ownerId)) {
            throw new NotFoundException(String.format(
                    "Пользователь с id %d не владеет вещью с id %d.", ownerId, itemId));
        }

        try {
            updateField(type, item);
        } catch (ReflectiveOperationException e) {
            throw new ReflectionException("Обновить данные не получилось.");
        }
        return gettingDtoMapper.toDto(itemRepository.saveAndFlush(item));
    }

    @Override
    @Transactional
    public ItemDto create(ItemDto type, Long ownerId) {
        User user = userRepository.findById(ownerId).orElseThrow(() ->
                new NotFoundException(String.format("Владелец с id %d не найден.", ownerId)));

        ItemRequest request = Objects.nonNull(type.getRequestId()) ? requestRepository.findById(type.getRequestId())
                        .orElseThrow(() -> new NotFoundException("")) : null;

        Item item = gettingDtoMapper.toEntity(type, request, user);

        return gettingDtoMapper.toDto(itemRepository.save(item));
    }

    @Override
    @Transactional
    public CommentDto createComment(CommentDto comment, Long itemId, Long userId) {
        boolean isBooker = bookingRepository.existsBookingsByIdAndAndBookerIdAndEndDateBefore(itemId,
                userId, LocalDateTime.now());

        if (!isBooker) {
            throw new UnavailableException(String.format("Пользователь с id %d не может оставить отзыв вещи с id %d.",
                    userId, itemId));
        }

        Item item = itemRepository.findById(itemId).orElseThrow(() ->
                new NotFoundException(String.format("Вещь с id %d не найдена.", itemId)));
        User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("Пользователь с id %d не найден.", userId)));
        LocalDateTime now = LocalDateTime.now();

        Comment newComment = commentDtoMapper.toEntity(comment, item, user, now);

        return commentDtoMapper.toDto(commentRepository.save(newComment));
    }
}