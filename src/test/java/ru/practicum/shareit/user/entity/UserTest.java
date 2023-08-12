package ru.practicum.shareit.user.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserTest {
    private User user;
    private User user2;

    @BeforeEach
    private void setUp() {
        user = User.builder().id(1L)
                .email("e@mail.ru")
                .build();

        user2 = User.builder().id(1L)
                .email("e@mail.ru")
                .build();
    }

    @Test
    void getId() {
        assertThat(user.getId(), equalTo(1L));
    }

    @Test
    void getEmail() {
        assertThat(user.getEmail(), equalTo("e@mail.ru"));
    }

    @Test
    void getName() {
        assertThat(user.getName(), equalTo(null));
    }

    @Test
    void setId() {
        user.setId(2L);
        assertThat(user.getId(), equalTo(2L));
    }

    @Test
    void setEmail() {
        user.setEmail("new@yandex.ru");
        assertThat(user.getEmail(), equalTo("new@yandex.ru"));
    }

    @Test
    void setName() {
        user.setName("Name");
        assertThat(user.getName(), equalTo("Name"));
    }

    @Test
    void testEquals() {
        assertTrue(user.equals(user2));
    }

    @Test
    void testHashCode() {
        assertThat(user.hashCode(), equalTo(user2.hashCode()));
    }
}