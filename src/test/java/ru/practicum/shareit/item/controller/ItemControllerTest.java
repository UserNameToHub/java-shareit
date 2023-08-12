package ru.practicum.shareit.item.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.practicum.shareit.util.Constants.HEADER_USER_ID;

@WebMvcTest(ItemController.class)
class ItemControllerTest {
    private final static String REST_URL = "/items";

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ItemService itemService;

    @Autowired
    private MockMvc mvc;

    private ItemDto itemDto;

    private CommentDto commentDto;

    @BeforeEach
    void setUp() {
        itemDto = ItemDto.builder()
                .name("Some name")
                .available(true)
                .description("Some description.")
                .build();

        commentDto = CommentDto.builder()
                .text("Some comment.")
                .authorName("Some author")
                .build();
    }

    @Test
    void getAllById() throws Exception {
        List<ItemDto> items = List.of(itemDto);
        when(itemService.findAllById(any(), any()))
                .thenReturn(items);

        mvc.perform(get(REST_URL)
                        .header(HEADER_USER_ID, 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void getById() throws Exception {
        when(itemService.findById(any(), any()))
                .thenReturn(itemDto);

        mvc.perform(get(REST_URL + "/{id}", 1)
                        .header(HEADER_USER_ID, 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.name").value("Some name"))
                .andExpect(jsonPath("$.description").value("Some description."));
    }

    @Test
    void getByText() throws Exception {
        when(itemService.findByText("description", 1L, List.of(1, 1)))
                .thenAnswer(invocationOnMock -> {
                    String text = invocationOnMock.getArgument(0, String.class);
                    if (itemDto.getDescription().contains("description")) {
                        return List.of(itemDto);
                    } else {
                        return Collections.emptyList();
                    }
                });

        mvc.perform(get(REST_URL + "/search", 1)
                        .param("text", "description")
                        .param("from", "1")
                        .param("size", "1")
                        .header(HEADER_USER_ID, 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].description").value("Some description."));
    }

    @Test
    void create() throws Exception {
        when(itemService.create(any(), any()))
                .thenReturn(itemDto);

        mvc.perform(post(REST_URL)
                        .header(HEADER_USER_ID, 1)
                        .content(objectMapper.writeValueAsString(itemDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void edit() throws Exception {
        itemDto.setDescription("New description.");

        when(itemService.update(any(), any(), any()))
                .thenReturn(itemDto);

        mvc.perform(patch(REST_URL + "/{id}", 1)
                        .content(objectMapper.writeValueAsString(itemDto))
                        .header(HEADER_USER_ID, 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.description").value(itemDto.getDescription()));
    }

    @Test
    void testDelete() throws Exception {
        mvc.perform(delete(REST_URL + "/{id}", 1)
                        .header(HEADER_USER_ID, 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> assertEquals("Вещь с id 1 был успешно удалена.",
                        result.getResponse().getContentAsString(StandardCharsets.UTF_8)))
                .andDo(print());

        verify(itemService, times(1))
                .delete(1L, 1L);
    }

    @Test
    void createComment() throws Exception {
        when(itemService.createComment(any(), any(), any()))
                .thenReturn(commentDto);

        mvc.perform(post(REST_URL + "/{itemId}/comment", 1)
                        .header(HEADER_USER_ID, 1)
                        .content(objectMapper.writeValueAsString(commentDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}