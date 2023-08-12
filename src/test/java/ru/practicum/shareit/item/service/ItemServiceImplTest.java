package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
//import ru.practicum.shareit.item.dto.ItemDto;
//import ru.practicum.shareit.item.entity.Item;
//import ru.practicum.shareit.item.repository.ItemRepository;
//import ru.practicum.shareit.request.RequestService;
//import ru.practicum.shareit.request.dto.RequestDto;
//import ru.practicum.shareit.user.dto.UserDto;
//import ru.practicum.shareit.user.entity.User;
//import ru.practicum.shareit.user.repository.UserRepository;
//import ru.practicum.shareit.user.service.UserService;

//import javax.persistence.EntityManager;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;

@SpringBootTest(
        properties = "db.name=test",
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ItemServiceImplTest {
//    @Autowired
//    private EntityManager em;
//
//    @Autowired
//    private ItemService service;
//
//    @Autowired
//    private UserService userService;
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private ItemRepository itemRepository;
//
//    private Item item;
//    private ItemDto itemDto;
//
//    @BeforeEach
//    void setUp() {
//        item = Item.builder()
//                .id(1L)
//                .description("Some description")
//                .name("Some name")
//                .build();
//
//        itemDto = ItemDto.builder()
//                .description("some d")
//                .name("some n")
//                .build();
//
//
//        userService.create(UserDto.builder().email("user@gmail.com").name("User").build());
//        service.create(itemDto, 1);
//    }
//
//    @Test
//    void findById() {
//
//    }
//
//    @Test
//    void findAllById() {
//
//    }
//
//    @Test
//    void findByText() {
//    }
//
//    @Test
//    void testFindByText() {
//    }
//
//    @Test
//    void delete() {
//    }
//
//    @Test
//    void update() {
//    }
//
//    @Test
//    void create() {
//    }
//
//    @Test
//    void createComment() {
//    }
}