package ru.practicum.shareit.item.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest
class ItemBookingGettingDtoTest {
    private ItemBookingGettingDto gettingDto;

    @BeforeEach
    void setUp() {
        gettingDto = new ItemBookingGettingDto(1L, 2L);
    }

    @Test
    void getId() {
        assertThat(gettingDto.getId(), equalTo(1L));
    }

    @Test
    void getBookerId() {
        assertThat(gettingDto.getBookerId(), equalTo(2L));
    }

    @Test
    void setId() {
        gettingDto.setId(99L);
        assertThat(gettingDto.getId(), equalTo(99L));
    }

    @Test
    void setBookerId() {
        gettingDto.setBookerId(109L);
        assertThat(gettingDto.getBookerId(), equalTo(109L));
    }
}