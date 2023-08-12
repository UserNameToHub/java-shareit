package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.dto.BookingCreatingDto;
import ru.practicum.shareit.booking.dto.BookingGettingDto;
import ru.practicum.shareit.booking.entity.Booking;
import ru.practicum.shareit.booking.mapper.BookingGettingDtoMapper;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.entity.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.entity.User;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.user.service.UserService;

import javax.persistence.EntityManager;

import java.time.LocalDateTime;

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

    @Mock
    private BookingService mockBookingService;

    @Mock
    private BookingGettingDtoMapper mapper;

    @Mock
    private BookingRepository mockBookingRepo;

//    @Mock
    private UserRepository userRepository;

    private UserService userService;

    private ItemService itemService;

//    @Mock
    private ItemRepository itemRepository;

    private Booking booking;
    private BookingGettingDto gettingDto;
    private BookingCreatingDto creatingDto;

    private User user;
    private Item item;
    private UserDto userDto;
    private ItemDto itemDto;

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

        userDto = UserDto.builder()
                .name("user")
                .email("user@yandex.ru")
                .build();

        item = Item.builder()
                .id(1L)
                .name("Some name")
                .owner(user)
                .available(true)
                .description("Some description.")
                .build();
    }

    @Test
    void findById() {
    }

    @Test
    void create() {
    }

    @Test
    void findAll() {
    }

    @Test
    void updateStatus() {
    }
}