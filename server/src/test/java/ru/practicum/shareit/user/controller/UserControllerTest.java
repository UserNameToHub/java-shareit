package ru.practicum.shareit.user.controller;

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
import ru.practicum.shareit.common.exception.NotUniqueException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {
    private static final String REST_URL = "/users";

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService<Long> userService;

    @Autowired
    private MockMvc mvc;

    private UserDto userDto;

    @BeforeEach
    private void init() {
        userDto = UserDto.builder()
                .id(1L)
                .email("test@gmail.com")
                .name("userTest")
                .build();
    }

    @Test
    public void shouldGetStatusOkWhenUserIsCreating() throws Exception {
        when(userService.create(any()))
                .thenReturn(userDto);

        mvc.perform(post(REST_URL)
                        .content(objectMapper.writeValueAsString(userDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userDto.getId()))
                .andExpect(jsonPath("$.email").value(userDto.getEmail()))
                .andExpect(jsonPath("$.name").value(userDto.getName()))
                .andDo(print());
    }

    @Test
    public void shouldGetStatusConflictWhenUserEmailINotUnique() throws Exception {
        when(userService.create(any()))
                .thenThrow(new NotUniqueException(String.format("Пользователь с email %s уже существует.", userDto.getEmail())));

        mvc.perform(post(REST_URL)
                        .content(objectMapper.writeValueAsString(userDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isConflict());
    }

    @Test
    public void shouldGetStatusOkWhenPathVariableIsCorrect() throws Exception {
        when(userService.findById(1L))
                .thenReturn(userDto);

        mvc.perform(get(REST_URL + "/{id}", 1)
                        .content(objectMapper.writeValueAsString(userDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetStatus404WhenPathVariableIsNotCorrect() throws Exception {
        when(userService.findById(2L))
                .thenThrow(new NotFoundException(String.format("Пользователь с id %d уже существует.", userDto.getId())));

        mvc.perform(get(REST_URL + "/{id}", 2)
                        .content(objectMapper.writeValueAsString(userDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldGetStatusOkWhenReturnList() throws Exception {
        when(userService.findAll())
                .thenReturn(List.of(userDto));

        mvc.perform(get(REST_URL)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andDo(print());
    }

    @Test
    public void shouldGetStatusOkWhenEditingObjectIsFound() throws Exception {
        userDto.setName("newUserTest");

        when(userService.update(userDto, 1L))
                .thenReturn(userDto);

        mvc.perform(patch(REST_URL + "/{id}", 1L)
                        .content(objectMapper.writeValueAsString(userDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(userDto.getName()))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void shouldGetStatus404WhenEditingObjectIsNotFound() throws Exception {
        userDto.setName("newUserTest");

        when(userService.update(userDto, 2L))
                .thenThrow(new NotFoundException(String.format("Пользователь с id %d не найден.", userDto.getId())));

        mvc.perform(patch(REST_URL + "/{id}", 2L)
                        .content(objectMapper.writeValueAsString(userDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    public void shouldGetStatusOkWhenDeletingObjectIsFound() throws Exception {
        mvc.perform(delete(REST_URL + "/{id}", 1L)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> assertEquals("Пользователь c id 1 был успешно удален.",
                        result.getResponse().getContentAsString(StandardCharsets.UTF_8)))
                .andDo(print());
    }
}