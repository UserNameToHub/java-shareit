package ru.practicum.shareit.booking.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.booking.dto.BookingCreatingDto;
import ru.practicum.shareit.booking.entity.Booking;
import ru.practicum.shareit.booking.enumeration.Status;
import ru.practicum.shareit.item.entity.Item;
import ru.practicum.shareit.user.entity.User;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class BookingCreatingDtoMapperTest {
    @Mock
    private BookingCreatingDtoMapper creatingDtoMapper;
    private BookingCreatingDto bookingCreatingDto;
    private Booking booking;

    @BeforeEach
    void setUp() {
        bookingCreatingDto = BookingCreatingDto.builder()
                .itemId(1L)
                .bookerId(1L)
                .build();

        booking = Booking.builder()
                .id(1L)
                .status(Status.WAITING)
                .booker(User.builder().id(1L).build())
                .item(Item.builder().id(1L).build())
                .build();
    }

    @Test
    void toEntity() {
        Mockito
                .when(creatingDtoMapper.toEntity(any(), any(), any()))
                .thenReturn(booking);

        Booking booking1 = creatingDtoMapper.toEntity(bookingCreatingDto, null, null);
        assertEquals(booking1.getClass(), booking.getClass());
    }
}