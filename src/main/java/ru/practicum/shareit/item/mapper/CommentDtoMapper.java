package ru.practicum.shareit.item.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.common.dto.BaseDtoMapper;
import ru.practicum.shareit.item.dto.CommentTo;
import ru.practicum.shareit.item.entity.Comment;
import ru.practicum.shareit.item.entity.Item;
import ru.practicum.shareit.user.entity.User;

import java.time.LocalDateTime;

@Component
public class CommentDtoMapper implements BaseDtoMapper<Comment, CommentTo> {
    @Override
    public CommentTo toDto(Comment type) {
        return CommentTo.builder()
                .id(type.getId())
                .text(type.getText())
                .authorName(type.getAuthor().getName())
                .build();
    }

    public Comment toEntity(CommentTo type, Item item, User user, LocalDateTime now) {
        return Comment.builder()
                .text(type.getText())
                .item(item)
                .author(user)
                .created(now)
                .build();
    }
}
