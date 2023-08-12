package ru.practicum.shareit.user.mapper;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.entity.User;
import ru.practicum.shareit.user.service.UserServiceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class UserDtoMapperTest {
    @Mock
    private UserDtoMapper userDtoMapper;

    private static UserDto userDto;

    private static User user;

    @Autowired
    private UserServiceImpl service;

    @BeforeAll
    public static void init() {
        userDto = UserDto.builder()
                .id(1L)
                .email("test@yandex.ru")
                .name("Tester")
                .build();

        user = User.builder()
                .id(1L)
                .email("test@yandex.ru")
                .name("Tester")
                .build();
    }

    @Test
    public void toDtoTest() {
        Mockito
                .when(userDtoMapper.toDto(any()))
                .thenReturn(userDto);

        UserDto dtoFromUser = userDtoMapper.toDto(user);
        assertEquals(dtoFromUser.getClass(), userDto.getClass());
    }
}