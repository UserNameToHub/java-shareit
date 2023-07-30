package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.dto.BookingCreatingTo;
import ru.practicum.shareit.booking.dto.BookingGettingTo;
import ru.practicum.shareit.booking.entity.Booking;
import ru.practicum.shareit.booking.enumeration.State;
import ru.practicum.shareit.booking.enumeration.Status;
import ru.practicum.shareit.booking.enumeration.UserStatus;
import ru.practicum.shareit.booking.mapper.BookingCreatingDtoMapper;
import ru.practicum.shareit.booking.mapper.BookingGettingDtoMapper;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.common.exception.DateException;
import ru.practicum.shareit.common.exception.NotFoundException;
import ru.practicum.shareit.common.exception.UnavailableException;
import ru.practicum.shareit.item.entity.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.entity.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.shareit.util.Constants.ORDER_BY_DATE_DESC;
import static ru.practicum.shareit.util.Constants.ORDER_BY_ID_ASC;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional(readOnly = true)
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final BookingCreatingDtoMapper creatingDtoMapper;
    private final BookingGettingDtoMapper gettingDtoMapper;

    @Override
    public BookingGettingTo findById(Long bookingId, Long userId) {
        log.info("Запрос на получение бронирование с id {} для пользователя с id {}");
        return gettingDtoMapper.toDto(bookingRepository.findByIdAndUserId(bookingId, userId)
                .orElseThrow(() -> new NotFoundException(String.format(
                        "Бронирование с id %s не найдено или пользователь с id %s не найден.", bookingId, userId))));
    }

    @Override
    @Transactional
    public BookingGettingTo create(BookingCreatingTo booking) {
        log.info("Запрос на создание бронирования.");
        User user = userRepository.findById(booking.getUserId()).orElseThrow(() ->
                new NotFoundException(String.format("Пользователь с id %d не найден.", booking.getUserId())));
        Item item = itemRepository.findById(booking.getItemId()).orElseThrow(() ->
                new NotFoundException(String.format("Вещь с id %d не найдена.", booking.getItemId())));

        if (user.getId().equals(item.getOwner().getId())) {
            throw new NotFoundException(String.format("Пользователь с id %d является владельцем вещи.", user.getId()));
        }

        if (!item.getAvailable()) {
            throw new UnavailableException(String.format("Вещь с id %d недоступна для бронирования.", item.getId()));
        }

        if (booking.getEnd().isBefore(booking.getStart()) ||
                booking.getStart().isEqual(booking.getEnd())) {
            throw new DateException("Дата начала бронирования или дата окончания бронирования неверна.");
        }

        Booking newBooking = bookingRepository.save(creatingDtoMapper.toEntity(booking, user, item));

        return gettingDtoMapper.toDto(newBooking);
    }

    @Override
    public List<BookingGettingTo> findAll(Long userId, State state, UserStatus userStatus) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException(String.format("Пользователь с id %d не найден.", userId));
        }
        return gettingDtoMapper.toDtoList(execute(userId, state, userStatus));
    }

    @Override
    @Transactional
    public BookingGettingTo updateStatus(Long bookingId, Boolean boolStatus, Long ownerId) {
        log.info("Запрос на обновление бронирования с id {} владельца с id {}", bookingId, ownerId);
        Booking updatingBooking = bookingRepository.findById(bookingId).orElseThrow(() ->
                new NotFoundException(String.format("Бронирование с id %d не найдено.", bookingId)));

        if (!updatingBooking.getItem().getOwner().getId().equals(ownerId)) {
            throw new NotFoundException("Владелец с %id не найден.");
        }

        if (updatingBooking.getStatus().equals(Status.APPROVED)) {
            throw new UnavailableException("Бронирование с id уже подтверждено владельцем.");
        }

        Status newStatus = boolStatus ? Status.APPROVED : Status.REJECTED;
        updatingBooking.setStatus(newStatus);

        return gettingDtoMapper.toDto(bookingRepository.save(updatingBooking));
    }

    private List<Booking> execute(Long id, State state, UserStatus userStatus) {
        LocalDateTime now = LocalDateTime.now();

        if (userStatus.equals(UserStatus.BOOKER)) {
            switch (state) {
                case PAST:
                    return bookingRepository.findAllByBookerIdAndEndDateIsBefore(id, now, ORDER_BY_DATE_DESC);
                case FUTURE:
                    return bookingRepository.findAllByBookerIdAndStartDateIsAfter(id, now, ORDER_BY_DATE_DESC);
                case ALL:
                    return bookingRepository.findAllByBookerId(id, ORDER_BY_DATE_DESC);
                case CURRENT:
                    return bookingRepository.findAllCurrentByBookerId(id, now, ORDER_BY_ID_ASC);
                default:
                    return bookingRepository.findAllByBookerIdAndStatusIs(id, Status.valueOf(state.toString()), ORDER_BY_DATE_DESC);
            }
        } else {
            switch (state) {
                case PAST:
                    return bookingRepository.findAllByItemOwnerIdAndEndDateIsBefore(id, now, ORDER_BY_DATE_DESC);
                case FUTURE:
                    return bookingRepository.findAllByItemOwnerIdAndStartDateIsAfter(id, now, ORDER_BY_DATE_DESC);
                case ALL:
                    return bookingRepository.findAllByItemOwnerId(id, ORDER_BY_DATE_DESC);
                case CURRENT:
                    return bookingRepository.findAllCurrentByItemOwnerId(id, now, ORDER_BY_DATE_DESC);
                default:
                    return bookingRepository.findAllByItemOwnerIdAndStatusIs(id, Status.valueOf(state.toString()), ORDER_BY_DATE_DESC);
            }
        }
    }
}
