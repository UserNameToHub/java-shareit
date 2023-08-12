package ru.practicum.shareit.request.dto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static ru.practicum.shareit.util.Constants.DATE_TIME_PATTERN;

@JsonTest
class RequestDtoTest {
    @Autowired
    private JacksonTester<RequestDto> json;

    @Test
    void testUserDto() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        List<ItemDto> emptyItems = List.of(ItemDto.builder().build());
        UserDto emptyUser = UserDto.builder().id(1L).build();
        RequestDto requestDto = RequestDto.builder()
                .id(1L)
                .items(emptyItems)
                .user(emptyUser)
                .description("Description")
                .created(now)
                .build();

        JsonContent<RequestDto> result = json.write(requestDto);

        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.description").isEqualTo("Description");
        assertThat(result).extractingJsonPathStringValue("$.created").isEqualTo(now.format(DateTimeFormatter
                .ofPattern(DATE_TIME_PATTERN)));
    }
}