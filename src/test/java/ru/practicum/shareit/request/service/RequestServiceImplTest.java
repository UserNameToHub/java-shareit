package ru.practicum.shareit.request.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.shareit.common.exception.NotFoundException;
import ru.practicum.shareit.request.dto.RequestDto;
import ru.practicum.shareit.request.entity.ItemRequest;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(
        properties = "db.name=test",
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class RequestServiceImplTest {
    @Autowired
    private EntityManager em;

    @Autowired
    private RequestService service;

    @Autowired
    private UserService userService;

    private static RequestDto requestDto;

    private static UserDto user;

    @BeforeAll
    public static void setUp() {
        requestDto = RequestDto.builder()
                .description("Some description.")
                .user(UserDto.builder().id(1L).name("Tester").email("tester@yandex.ru").build())
                .created(LocalDateTime.now())
                .items(Collections.emptyList())
                .build();

        user = UserDto.builder()
                .email("tester@gmail.com")
                .name("Tester")
                .build();
    }

    @Test
    void testFindById() {
        UserDto userDto = userService.create(user);

        RequestDto requestDtoCreated = service.create(requestDto, userDto.getId());
        RequestDto requestDtoRes = service.findById(requestDtoCreated.getId(), userDto.getId());

        assertEquals(requestDtoRes.getId(), requestDtoCreated.getId());
        assertEquals(requestDtoRes.hashCode(), requestDtoCreated.hashCode());
    }

    @Test
    void testFindByIdWhenUserNotFound() {
        NotFoundException nfe = assertThrows(NotFoundException.class, () ->
                service.findById(1L, 1L));
        assertEquals("Пользователь с id 1 не найден.", nfe.getMessage());
    }

    @Test
    void testFindByIdWhenRequestNotFound() {
        userService.create(user);

        NotFoundException nfe = assertThrows(NotFoundException.class, () ->
                service.findById(1L, 1L));
        assertEquals("Запрос с id 1 не найден.", nfe.getMessage());
    }

    @Test
    void testCreate() {
        UserDto userDto = userService.create(user);
        service.create(requestDto, userDto.getId());

        TypedQuery<ItemRequest> query = em.createQuery("select r from ItemRequest as r where r.id = :id", ItemRequest.class);
        ItemRequest request = query.setParameter("id", userDto.getId()).getSingleResult();

        assertThat(request.getId(), notNullValue());
        assertThat(request.getDescription(), equalTo(requestDto.getDescription()));
        assertThat(request.getRequester().getId(), equalTo(userDto.getId()));
    }

    @Test
    void testCreateWhenUserIsNotFound() {
        NotFoundException nfe = assertThrows(NotFoundException.class, () ->
                service.create(requestDto, 99L));
        assertEquals("Пользователь с id 99 не найден.", nfe.getMessage());
    }

    @Test
    void testFindIllById() {
        UserDto userDto = userService.create(user);
        List<RequestDto> requestDtoList = List.of(service.create(requestDto, userDto.getId()));

        List<RequestDto> requests = service.findIllById(userDto.getId());
        assertEquals(requests.size(), requestDtoList.size());
    }

    @Test
    void testFindIllByIdWhenUserIsNotFound() {
        NotFoundException nfe = assertThrows(NotFoundException.class, () ->
                service.findIllById(99L));
        assertEquals("Пользователь с id 99 не найден.", nfe.getMessage());
    }

    @Test
    void getAll() {
        UserDto userDto = userService.create(user);
        List<RequestDto> requestDtoList = List.of(service.create(requestDto, userDto.getId()));

        List<RequestDto> allRequests = service.getAll(0, 10, 99L);

        assertEquals(requestDtoList.size(), allRequests.size());
    }
}