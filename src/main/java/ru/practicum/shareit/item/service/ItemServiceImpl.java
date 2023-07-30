package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.common.exception.NotFoundException;
import ru.practicum.shareit.common.exception.UnavailableException;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.item.entity.Comment;
import ru.practicum.shareit.item.entity.Item;
import ru.practicum.shareit.item.mapper.CommentDtoMapper;
import ru.practicum.shareit.item.mapper.ItemDtoMapper;
import ru.practicum.shareit.item.mapper.ItemGettingDtoMapper;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.entity.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.shareit.util.Constants.ORDER_BY_ID_ASC;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemServiceImpl implements ItemService<Long> {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;
    private final ItemDtoMapper mapper;
    private final CommentDtoMapper commentDtoMapper;
    private final ItemGettingDtoMapper gettingDtoMapper;

    @Override
    public ItemGettingTo findById(Long idType, Long userId) {
        Item item = itemRepository.findById(idType).orElseThrow(() ->
                new NotFoundException(String.format("Вещь с id %d не найдена.", idType)));

        return gettingDtoMapper.toDto(item, itemRepository.existsByIdAndOwnerId(idType, userId));
    }

    @Override
    public List<ItemGettingTo> findAllById(Long idOwner) {
        if (!userRepository.existsById(idOwner)) {
            throw new NotFoundException(String.format("Пользователь с id %d не найден.)", idOwner));
        }

        List<Item> allByOwnerId = itemRepository.findAllByOwnerId(idOwner, ORDER_BY_ID_ASC);
        return gettingDtoMapper.toDtoList(allByOwnerId);
    }

    @Override
    public List<ItemTo> findByText(String text) {
        return mapper.toDtoList(itemRepository.findByNameOrDescriptionText(text));
    }

    @Override
    public List<ItemTo> findByText(String text, Long owner) {
        return mapper.toDtoList(itemRepository.findByNameOrDescriptionText(text));
    }

    @Override
    @Transactional
    public void delete(Long idType, Long idOwner) {
        boolean b = itemRepository.existsByIdAndOwnerId(idType, idOwner);
        itemRepository.deleteByOwnerId(idType, idOwner);
    }

    @Override
    @Transactional
    public ItemTo update(ItemTo type, Long itemId, Long ownerId) {
        userRepository.findById(ownerId).orElseThrow(() ->
                new NotFoundException(""));

        Item item = itemRepository.findById(itemId).orElseThrow(() ->
                new NotFoundException(""));

        if (!itemRepository.existsByIdAndOwnerId(itemId, ownerId)) {
            throw new NotFoundException(String.format(
                    "Пользователь с id %d не владеет вещью с id %d.", ownerId, itemId));
        }

        try {
            updateField(type, item);
        } catch (ReflectiveOperationException e) {
            // to do
        }

        return mapper.toDto(itemRepository.saveAndFlush(item));
    }

    @Override
    @Transactional
    public ItemTo create(ItemTo type, Long ownerId) {
        User user = userRepository.findById(ownerId).orElseThrow(() ->
                new NotFoundException(String.format("Владелец с id %d не найден.", ownerId)));
        Item item = mapper.toEntity(type);

        item.setOwner(user);

        return mapper.toDto(itemRepository.save(item));
    }

    @Override
    @Transactional
    public CommentTo createComment(CommentTo comment, Long itemId, Long userId) {
        boolean isBooker = bookingRepository.existsBookingsByIdAndAndBookerIdAndEndDateBefore(itemId,
                userId, LocalDateTime.now());

        if (!isBooker) {
            throw new UnavailableException(String.format("Пользователь с id %d не может оставить отзыв вещи с id %d.",
                    userId, itemId));
        }

        Item item = itemRepository.findById(itemId).orElseThrow(() ->
                new NotFoundException(String.format("Вещь с id %d не найдена.", itemId)));
        User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("Вещь с id %d не найдена.", itemId)));
        LocalDateTime now = LocalDateTime.now();

        Comment newComment = commentDtoMapper.toEntity(comment, item, user, now);

        return commentDtoMapper.toDto(commentRepository.save(newComment));
    }
}
