package ru.practicum.shareit.item.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.user.entity.User;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

class CommentTest {
    private Comment comment;
    private Comment comment1;

    @BeforeEach
    private void setUp() {
        comment = Comment.builder()
                .id(1L)
                .author(User.builder().build())
                .created(LocalDateTime.now().withNano(0))
                .text("Some text")
                .item(Item.builder().build())
                .build();

        comment1 = Comment.builder()
                .id(1L)
                .author(User.builder().build())
                .created(LocalDateTime.now().withNano(0))
                .text("Some text")
                .item(Item.builder().build())
                .build();
    }

    @Test
    void getId() {
       assertThat(comment.getId(), equalTo(1L));
    }

    @Test
    void getText() {
        assertThat(comment.getText(), equalTo("Some text"));
    }

    @Test
    void getItem() {
        assertThat(comment.getItem(), equalTo(comment.getItem()));
    }

    @Test
    void testEquals() {
        assertTrue(comment.equals(comment1));
    }

    @Test
    void testHashCode() {
        assertThat(comment.hashCode(), equalTo(comment1.hashCode()));
    }
}