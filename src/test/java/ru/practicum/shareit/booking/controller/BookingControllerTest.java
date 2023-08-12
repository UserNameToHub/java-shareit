package ru.practicum.shareit.booking.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.booking.dto.BookingCreatingDto;
import ru.practicum.shareit.booking.dto.BookingGettingDto;
import ru.practicum.shareit.booking.enumeration.Status;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.practicum.shareit.util.Constants.HEADER_USER_ID;

@WebMvcTest(controllers = BookingController.class)
class BookingControllerTest {
    private static final String REST_URL = "/bookings";

    @MockBean
    private BookingService bookingService;

    @Autowired
    private MockMvc mvc;

    private BookingCreatingDto creatingDto;
    private BookingGettingDto gettingDto;
    private LocalDateTime from;
    private LocalDateTime to;

    @BeforeEach
    void setUp() {

        creatingDto = BookingCreatingDto.builder()
                .start(LocalDateTime.now().plusHours(1).withNano(0))
                .end(LocalDateTime.now().plusHours(6).withNano(0))
                .build();

        gettingDto = BookingGettingDto.builder()
                .id(1L)
                .start(from)
                .end(to)
                .booker(UserDto.builder().build())
                .item(ItemDto.builder().build())
                .status(Status.WAITING)
                .build();
    }

    @Test
    void testGetById() throws Exception {
        when(bookingService.findById(any(), any()))
                .thenReturn(gettingDto);


        mvc.perform(get(REST_URL + "/{bookingId}", 1)
                        .header(HEADER_USER_ID, 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(gettingDto.getId()))
                .andExpect(jsonPath("$.status").value(gettingDto.getStatus().toString()));
    }

    @Test
    void testEditStatus() throws Exception {
        gettingDto.setStatus(Status.APPROVED);

        when(bookingService.updateStatus(any(), any(), any()))
                .thenReturn(gettingDto);

        mvc.perform(patch(REST_URL + "/{bookingId}", 1)
                        .param("approved", "true")
                        .header(HEADER_USER_ID, 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.status").value(gettingDto.getStatus().toString()));
    }

    @Test
    void getAllByBooker() throws Exception {
        List<BookingGettingDto> list = List.of(gettingDto);

        when(bookingService.findAll(any(), any(), any(), any(), any()))
                .thenReturn(list);

        mvc.perform(get(REST_URL)
                        .header(HEADER_USER_ID, 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @Test
    void getAllByOwner() throws Exception {
        List<BookingGettingDto> list = List.of(gettingDto);

        when(bookingService.findAll(any(), any(), any(), any(), any()))
                .thenReturn(list);

        mvc.perform(get(REST_URL + "/owner")
                        .header(HEADER_USER_ID, 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());
    }
}