package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.dto.BookingCreatingDto;
import ru.practicum.shareit.booking.dto.BookingGettingDto;
import ru.practicum.shareit.booking.entity.Booking;
import ru.practicum.shareit.booking.enumeration.State;
import ru.practicum.shareit.booking.enumeration.Status;
import ru.practicum.shareit.booking.enumeration.UserStatus;
import ru.practicum.shareit.common.exception.DateException;
import ru.practicum.shareit.common.exception.NotFoundException;
import ru.practicum.shareit.common.exception.UnavailableException;
import ru.practicum.shareit.item.entity.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.entity.User;
import ru.practicum.shareit.user.repository.UserRepository;

import javax.persistence.EntityManager;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
@SpringBootTest(
        properties = "db.name=test",
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class BookingServiceImplTest {
    @Autowired
    private EntityManager em;

    @Autowired
    private BookingService service;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;
    private Booking booking;
    private BookingGettingDto gettingDto;
    private BookingCreatingDto creatingDto;
    private User user;
    private User user2;
    private Item item;

    @BeforeEach
    void setUp() {
        gettingDto = BookingGettingDto.builder()
                .start(LocalDateTime.now().plusHours(1).withNano(0))
                .end(LocalDateTime.now().plusHours(5).withNano(0))
                .item(null)
                .booker(null)
                .build();

        creatingDto = BookingCreatingDto.builder()
                .itemId(1L)
                .bookerId(1L)
                .start(LocalDateTime.now().plusHours(1).withNano(0))
                .end(LocalDateTime.now().plusHours(5).withNano(0))
                .build();


        booking = Booking.builder()
                .startDate(LocalDateTime.now().plusHours(1).withNano(0))
                .endDate(LocalDateTime.now().plusHours(5).withNano(0))
                .item(item)
                .booker(user)
                .build();

        user = User.builder()
                .id(1L)
                .name("User")
                .email("user@yandex.ru")
                .build();

        user2 = User.builder()
                .name("User22")
                .email("user22@yandex.ru")
                .build();

        item = Item.builder()
                .id(1L)
                .name("Some name")
                .owner(user2)
                .available(true)
                .description("Some description.")
                .build();

        userRepository.save(user);
        userRepository.save(user2);
        itemRepository.save(item);
    }

    @Test
    void findById() {
        service.create(creatingDto);
        BookingGettingDto bookingGettingDto = service.findById(1L, 1L);
        assertThat(bookingGettingDto.getId(), equalTo(1L));
    }

    @Test
    void testCreate() {
        service.create(creatingDto);

        Booking bookingDB = em.createQuery("select b from Booking as b where b.id = :id", Booking.class)
                .setParameter("id", 1L).getSingleResult();

        assertThat(bookingDB.getId(), notNullValue());
        assertThat(bookingDB.getItem().getId(), equalTo(item.getId()));
        assertThat(bookingDB.getBooker().getId(), equalTo(user.getId()));
    }

    @Test
    void testCreateWhenUserIsNoyFound() {
        creatingDto.setBookerId(100L);

        NotFoundException nfeUser = assertThrows(NotFoundException.class, () ->
                service.create(creatingDto));
        assertEquals("Пользователь с id 100 не найден.", nfeUser.getMessage());
    }

    @Test
    void testCreateWhenItemIsNotFound() {
        creatingDto.setItemId(99L);
        NotFoundException nfeItem = assertThrows(NotFoundException.class, () ->
                service.create(creatingDto));
        assertEquals("Вещь с id 99 не найдена.", nfeItem.getMessage());
    }

    @Test
    void testCreateWhenUserIsOwner() {
        creatingDto.setBookerId(2L);
        NotFoundException nfeItem = assertThrows(NotFoundException.class, () ->
                service.create(creatingDto));
        assertEquals("Пользователь с id 2 является владельцем вещи.", nfeItem.getMessage());
    }

    @Test
    void testCreateWhenItemIsNotAvailable() {
        item.setAvailable(false);
        itemRepository.saveAndFlush(item);
        UnavailableException ue = assertThrows(UnavailableException.class, () ->
                service.create(creatingDto));
        assertEquals("Вещь с id 1 недоступна для бронирования.", ue.getMessage());
    }

    @Test
    void testCreateWhenBookingDataIsNotCorrect() {
        creatingDto.setEnd(gettingDto.getStart().minusDays(2).withNano(0));

        DateException de = assertThrows(DateException.class, () ->
                service.create(creatingDto));
        assertEquals("Дата начала бронирования или дата окончания бронирования неверна.", de.getMessage());
    }

    @Test
    void testFindAll() {
        service.create(creatingDto);

        List<BookingGettingDto> bookings = service.findAll(1L, State.ALL, UserStatus.BOOKER, 0, 10);
        assertThat(bookings.size(), equalTo(1));

        List<BookingGettingDto> bookingsFuture = service.findAll(1L, State.FUTURE, UserStatus.BOOKER, 0, 10);
        assertThat(bookingsFuture.size(), equalTo(1));

        List<BookingGettingDto> bookingsPast = service.findAll(1L, State.PAST, UserStatus.BOOKER, 0, 10);
        assertThat(bookingsPast.size(), equalTo(0));

        List<BookingGettingDto> bookingsCurrent = service.findAll(1L, State.CURRENT, UserStatus.BOOKER, 0, 10);
        assertThat(bookingsCurrent.size(), equalTo(0));

        List<BookingGettingDto> bookingsOwner = service.findAll(1L, State.ALL, UserStatus.OWNER, 0, 10);
        assertThat(bookingsOwner.size(), equalTo(0));

        List<BookingGettingDto> bookingsOwnerFuture = service.findAll(1L, State.FUTURE, UserStatus.OWNER, 0, 10);
        assertThat(bookingsOwnerFuture.size(), equalTo(0));

        List<BookingGettingDto> bookingsOwnerPast = service.findAll(1L, State.PAST, UserStatus.OWNER, 0, 10);
        assertThat(bookingsOwnerPast.size(), equalTo(0));

        List<BookingGettingDto> bookingsOwnerCurrent = service.findAll(1L, State.CURRENT, UserStatus.OWNER, 0, 10);
        assertThat(bookingsOwnerCurrent.size(), equalTo(0));
    }

    @Test
    void testUpdateStatus() {
        service.create(creatingDto);
        BookingGettingDto bookingGettingDto = service.updateStatus(1L, true, 2L);

        assertThat(bookingGettingDto.getStatus(), equalTo(Status.APPROVED));
    }

    @Test
    void testUpdateStatusWhenBookingIsNotFound() {
        NotFoundException nfe = assertThrows(NotFoundException.class, () ->
                service.updateStatus(99L, true, 2L));
        assertEquals("Бронирование с id 99 не найдено.", nfe.getMessage());
    }

    @Test
    void testUpdateStatusWhenOwnerIsNotFound() {
        service.create(creatingDto);
        NotFoundException nfe = assertThrows(NotFoundException.class, () ->
                service.updateStatus(1L, true, 99L));
        assertEquals("Владелец с id 99 не найден.", nfe.getMessage());
    }

    @Test
    void testUpdateStatusWhenDoubleApproved() {
        service.create(creatingDto);
        service.updateStatus(1L, true, 2L);

        UnavailableException ue = assertThrows(UnavailableException.class, () ->
                service.updateStatus(1L, true, 2L));
        assertEquals("Бронирование с id 1 уже подтверждено владельцем.", ue.getMessage());
    }
}