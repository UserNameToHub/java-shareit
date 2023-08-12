package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.common.exception.NotFoundException;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.entity.Comment;
import ru.practicum.shareit.item.entity.Item;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest(
        properties = "db.name=test",
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ItemServiceImplTest {
    @Autowired
    private EntityManager em;

    @Autowired
    private ItemServiceImpl service;

    @Autowired
    private UserService userService;

    @MockBean
    private BookingRepository bookingRepository;

    private ItemDto itemDto;
    private ItemDto itemDto2;
    private UserDto userDto;
    private UserDto userDto2;

    @BeforeEach
    void setUp() {
        itemDto = ItemDto.builder()
                .description("some d")
                .name("some n")
                .build();

        itemDto2 = ItemDto.builder()
                .description("Some description2.")
                .name("Some name2")
                .available(true)
                .build();

        userDto = userService.create(UserDto.builder().email("user@gmail.com").name("User").build());
        userDto2 = userService.create(UserDto.builder().email("user2@gmail.com").name("User2").build());
        service.create(itemDto, userDto.getId());
        service.create(itemDto2, userDto.getId());
    }

    @Test
    void testFindByIdWhenUserIsFound() {
        ItemDto item = service.findById(1L, 1L);
        assertEquals(item.getId(), 1L);
    }

    @Test
    void testFindByIdWhenItemIsNotFound() {
        NotFoundException nfe = assertThrows(NotFoundException.class, () ->
                service.findById(103L, 1L));

        assertEquals("Вещь с id 103 не найдена.", nfe.getMessage());
    }

    @Test
    void findAllById() {
        List<ItemDto> itemsDto = service.findAllById(1L, List.of(0, 5));
        assertEquals(itemsDto.size(), 2);
        assertEquals(itemsDto.get(0).getId(), 1L);
    }

    @Test
    void findByText() {
        List<ItemDto> items = service.findByText("name2", List.of(0, 10));
        assertEquals(items.get(0).getName(), "Some name2");
    }

    @Test
    void testFindByText() {
        List<ItemDto> items = service.findByText("name2", 1L, List.of(0, 10));
        assertEquals(items.get(0).getName(), "Some name2");
    }

    @Test
    void testUpdate() {
        ItemDto updatingField = ItemDto.builder().description("New description.").build();

        ItemDto updatedItem = service.update(updatingField, 1L, userDto.getId());

        assertEquals(updatedItem.getDescription(), updatingField.getDescription());
    }

    @Test
    void testUpdateWhenUserOrItemOrOwnerNotFound() {
        ItemDto updatingField = ItemDto.builder().description("New description.").build();

        NotFoundException nfeItem = assertThrows(NotFoundException.class, () ->
                service.update(updatingField, 1L, 99L));

        assertEquals("Пользователь с id 99 не найден.", nfeItem.getMessage());

        NotFoundException nfeUser = assertThrows(NotFoundException.class, () ->
                service.update(updatingField, 99L, userDto.getId()));

        assertEquals("Вещь с id 99 не найдена.", nfeUser.getMessage());

        NotFoundException nfeOwner = assertThrows(NotFoundException.class, () ->
                service.update(updatingField, 1L, 2L));

        assertEquals("Пользователь с id 2 не владеет вещью с id 1.", nfeOwner.getMessage());
    }

    @Test
    void testCreate() {
        service.create(ItemDto.builder().name("Created").description("New creating.").build(), 2L);

        TypedQuery<Item> query = em.createQuery("select i from Item as i where i.name = :name", Item.class);
        Item itemResult = query.setParameter("name", "Created").getSingleResult();

        assertThat(itemResult.getName(), equalTo("Created"));
    }

    @Test
    void testCreateWhenUserIsNotFound() {
        NotFoundException nfeItem = assertThrows(NotFoundException.class, () ->
                service.create(itemDto, 99L));

        assertEquals("Владелец с id 99 не найден.", nfeItem.getMessage());
    }

    @Test
    void testCreateComment() {
        when(bookingRepository.existsBookingsByIdAndAndBookerIdAndEndDateBefore(any(), any(), any()))
                .thenReturn(true);

        CommentDto commentDto = CommentDto.builder()
                .authorName("AntonO").created(LocalDateTime.now().withNano(0))
                .text("Than\'s good!")
                .build();

        service.createComment(commentDto, 1L, 2L);

        TypedQuery<Comment> query = em.createQuery("select c from Comment as c where c.id = :id", Comment.class);
        Comment comment = query.setParameter("id", 1L).getSingleResult();

        assertThat(comment.getId(), notNullValue());
        assertThat(comment.getText(), equalTo(commentDto.getText()));
    }
}