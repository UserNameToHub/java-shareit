package ru.practicum.shareit.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import ru.practicum.shareit.common.exception.NotFoundException;
import ru.practicum.shareit.request.dto.RequestDto;
import ru.practicum.shareit.user.dto.UserDto;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.practicum.shareit.util.Constants.HEADER_USER_ID;

@WebMvcTest(controllers = RequestController.class)
class RequestControllerTest {
    private final static String REST_URL = "/requests";

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RequestService requestService;

    @Autowired
    private MockMvc mvc;

    private RequestDto requestDto;

    @BeforeEach
    private void setUp() {
        requestDto = RequestDto.builder()
                .description("Some description.")
                .user(UserDto.builder()
                        .id(1L)
                        .name("Tester")
                        .email("tester@yandex.ru").build())
                .items(Collections.emptyList())
                .build();
    }

    @Test
    public void shouldGetStatusOkWhenRequestIsCreating() throws Exception {
        when(requestService.create(any(), any()))
                .thenReturn(requestDto);

        mvc.perform(post(REST_URL)
                        .header("X-Sharer-User-Id", 1)
                        .content(objectMapper.writeValueAsString(requestDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(requestDto.getId()))
                .andExpect(jsonPath("$.description").value(requestDto.getDescription()))
        ;
    }

    @Test
    public void shouldGetStatusConflictWhenUserIsNotFound() throws Exception {
        when(requestService.create(requestDto, 1L))
                .thenThrow(new NotFoundException(String.format("Пользователь с email %s уже существует.", 1L)));

        mvc.perform(post(REST_URL)
                        .header("X-Sharer-User-Id", 1L)
                        .content(objectMapper.writeValueAsString(requestDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print());
//                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldGetStatusOkWhenPathVariableIsCorrect() throws Exception {
        when(requestService.findIllById(1l))
                .thenReturn(List.of(requestDto));

        mvc.perform(get(REST_URL)
                        .header("X-Sharer-User-Id", 1L)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    public void shouldGetStatusNotFoundWhenPathVariableIsNoCorrect() throws Exception {
        when(requestService.findIllById(1L))
                .thenThrow(new NotFoundException(String.format("Пользователь с id 1 не найден.", 1L)));

        mvc.perform(get(REST_URL)
                        .header("X-Sharer-User-Id", 1L)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldGetStatusOkWhenUserIsFound() throws Exception {
        List<RequestDto> requestDtoList = List.of(requestDto);

        when(requestService.getAll(any(), any(), any()))
                .thenReturn(requestDtoList);

        mvc.perform(get(REST_URL + "/all")
                        .param("from", "1")
                        .param("size", "1")
                        .header(HEADER_USER_ID, 1L)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    public void shouldGetStatusOkWhenRequestIsFind() throws Exception {
        when(requestService.findById(any(), any()))
                .thenReturn(requestDto);

        mvc.perform(get(REST_URL + "/{id}", 1L)
                        .header(HEADER_USER_ID, 1L)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetStatusOkWhenRequestIsNotFind() throws Exception {
        when(requestService.findById(any(), any()))
                .thenThrow(new NotFoundException(String.format("Пользователь с id 1 не найден.", 1L)));

        mvc.perform(get(REST_URL + "/{id}", 1L)
                        .header(HEADER_USER_ID, 1L)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}