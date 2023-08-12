package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.dto.BookingCreatingDto;
import ru.practicum.shareit.booking.dto.BookingGettingDto;
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
    public BookingGettingDto findById(Long bookingId, Long userId) {
//        log.info("Запрос на получение бронирование с id {} для пользователя с id {}");
        return gettingDtoMapper.toDto(bookingRepository.findByIdAndUserId(bookingId, userId)
                .orElseThrow(() -> new NotFoundException(String.format(
                        "Бронирование с id %s не найдено или пользователь с id %s не найден.", bookingId, userId))));
    }

    @Override
    @Transactional
    public BookingGettingDto create(BookingCreatingDto booking) {
//        log.info("Запрос на создание бронирования.");
        User user = userRepository.findById(booking.getBookerId()).orElseThrow(() ->
                new NotFoundException(String.format("Пользователь с id %d не найден.", booking.getBookerId())));
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
    public List<BookingGettingDto> findAll(Long userId, State state, UserStatus userStatus, Integer from, Integer size) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException(String.format("Пользователь с id %d не найден.", userId));
        }
        return gettingDtoMapper.toDtoList(execute(userId, state, userStatus, List.of(from, size)));
    }

    @Override
    @Transactional
    public BookingGettingDto updateStatus(Long bookingId, Boolean boolStatus, Long ownerId) {
//        log.info("Запрос на обновление бронирования с id {} владельца с id {}", bookingId, ownerId);
        Booking updatingBooking = bookingRepository.findById(bookingId).orElseThrow(() ->
                new NotFoundException(String.format("Бронирование с id %d не найдено.", bookingId)));

        if (!updatingBooking.getItem().getOwner().getId().equals(ownerId)) {
            throw new NotFoundException(String.format("Владелец с id %d не найден.", ownerId));
        }

        if (updatingBooking.getStatus().equals(Status.APPROVED)) {
            throw new UnavailableException(String.format("Бронирование с id %d уже подтверждено владельцем.", bookingId));
        }

        Status newStatus = boolStatus ? Status.APPROVED : Status.REJECTED;
        updatingBooking.setStatus(newStatus);

        return gettingDtoMapper.toDto(bookingRepository.save(updatingBooking));
    }

    private List<Booking> execute(Long id, State state, UserStatus userStatus, List<Integer> pageParam) {
        LocalDateTime now = LocalDateTime.now();

        if (userStatus.equals(UserStatus.BOOKER)) {
            switch (state) {
                case PAST:
                    return bookingRepository.findAllByBookerIdAndEndDateIsBefore(id, now, getPage(pageParam, ORDER_BY_DATE_DESC)).toList();
                case FUTURE:
                    return bookingRepository.findAllByBookerIdAndStartDateIsAfter(id, now, getPage(pageParam, ORDER_BY_DATE_DESC)).toList();
                case ALL:
                    return bookingRepository.findAllByBooker_Id(id, getPage(pageParam, ORDER_BY_DATE_DESC)).toList();
                case CURRENT:
                    return bookingRepository.findAllCurrentByBookerId(id, now, getPage(pageParam, ORDER_BY_ID_ASC)).toList();
                default:
                    return bookingRepository.findAllByBookerIdAndStatusIs(id, Status.valueOf(state.toString()),
                            getPage(pageParam, ORDER_BY_ID_ASC)).toList();
            }
        } else {
            switch (state) {
                case PAST:
                    return bookingRepository.findAllByItemOwnerIdAndEndDateIsBefore(id, now, getPage(pageParam, ORDER_BY_DATE_DESC)).toList();
                case FUTURE:
                    return bookingRepository.findAllByItemOwnerIdAndStartDateIsAfter(id, now, getPage(pageParam, ORDER_BY_DATE_DESC)).toList();
                case ALL:
                    return bookingRepository.findAllByItemOwnerId(id, getPage(pageParam, ORDER_BY_DATE_DESC)).toList();
                case CURRENT:
                    return bookingRepository.findAllCurrentByItemOwnerId(id, now, getPage(pageParam, ORDER_BY_DATE_DESC)).toList();
                default:
                    return bookingRepository.findAllByItemOwnerIdAndStatusIs(id, Status.valueOf(state.toString()),
                            getPage(pageParam, ORDER_BY_DATE_DESC)).toList();
            }
        }
    }

    private Pageable getPage(List<Integer> pageParam, Sort sort) {
        return PageRequest.of(pageParam.get(0), pageParam.get(1), sort);
    }
}
