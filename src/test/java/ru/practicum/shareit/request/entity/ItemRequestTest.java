package ru.practicum.shareit.request.entity;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ItemRequestTest {
    private static ItemRequest itemRequest1;
    private static ItemRequest itemRequest2;

    @BeforeAll
    private static void setUp() {
        itemRequest1 = ItemRequest.builder()
                .id(1L)
                .requester(null)
                .description("Description.")
                .created(LocalDateTime.now())
                .build();

        itemRequest2 = ItemRequest.builder()
                .id(1L)
                .requester(null)
                .description("Description.")
                .created(LocalDateTime.now())
                .build();
    }

    @Test
    void testEquals() {
        assertTrue(itemRequest1.equals(itemRequest2));
    }

    @Test
    void testHashCode() {
        assertEquals(itemRequest1.hashCode(), itemRequest2.hashCode());
    }
}