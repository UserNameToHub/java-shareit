package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.shareit.common.exception.NotFoundException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.entity.User;
import ru.practicum.shareit.user.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(
        properties = "db.name=test",
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class UserServiceImplTest {
    @Autowired
    private EntityManager em;

    @Autowired
    private UserService service;

    @Autowired
    private UserRepository userRepository;

    private static UserDto userDto;

    @BeforeAll
    public static void setUp() {
        userDto = UserDto.builder()
                .name("Tester")
                .email("tester@yandex.com")
                .build();
    }

    @Test
    public void saveUserTest() {
        service.create(userDto);

        TypedQuery<User> query = em.createQuery("select u from User as u where u.email = :email", User.class);
        User user = query.setParameter("email", userDto.getEmail()).getSingleResult();

        assertThat(user.getId(), notNullValue());
        assertThat(user.getName(), equalTo(userDto.getName()));
        assertThat(user.getEmail(), equalTo(userDto.getEmail()));
    }

    @Test
    public void deleteUserTest() {
        UserDto ud = service.create(userDto);
        service.delete(ud.getId());

        NotFoundException nfe = assertThrows(NotFoundException.class, () -> service.findById(ud.getId()));
        assertEquals(nfe.getMessage(), "Пользователь с id 1 не найден.");
    }

    @Test
    public void deleteUserTestWhenUserNotFound() {
        EmptyResultDataAccessException nfe = assertThrows(EmptyResultDataAccessException.class, () ->
                userRepository.deleteById(99L));
        assertEquals(nfe.getMessage(), nfe.getMessage());
    }

    @Test
    public void findAll() {
        List<UserDto> userDtoList = List.of(service.create(userDto));
        List<UserDto> allUsers = service.findAll();

        assertEquals(userDtoList.size(), allUsers.size());
    }

    @Test
    public void findAllWhenListIsEmpty() {
        int sizeList = em.createQuery("select u from User as u", User.class).getResultList().size();
        assertEquals(0, sizeList);
    }

    @Test
    public void updateUserTest() {
        service.create(userDto);

        TypedQuery<User> query = em.createQuery("select u from User as u where u.email = :email", User.class);
        User user = query.setParameter("email", userDto.getEmail()).getSingleResult();

        assertThat(user.getName(), equalTo(userDto.getName()));
    }

    @Test
    public void updateUserWhenUserNotFound() {
        NotFoundException nfe = assertThrows(NotFoundException.class, () ->
                service.update(userDto, 99L));

        assertEquals("Пользователь с id 99 не найден.", nfe.getMessage());
    }
}