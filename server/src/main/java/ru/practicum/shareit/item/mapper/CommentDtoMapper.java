package ru.practicum.shareit.item.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.common.dto.BaseDtoMapper;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.entity.Comment;
import ru.practicum.shareit.item.entity.Item;
import ru.practicum.shareit.user.entity.User;

import java.time.LocalDateTime;

@Component
public class CommentDtoMapper implements BaseDtoMapper<Comment, CommentDto> {
    @Override
    public CommentDto toDto(Comment type) {
        return CommentDto.builder()
                .id(type.getId())
                .text(type.getText())
                .authorName(type.getAuthor().getName())
                .created(type.getCreated())
                .build();
    }

    public Comment toEntity(CommentDto type, Item item, User user, LocalDateTime now) {
        return Comment.builder()
                .text(type.getText())
                .item(item)
                .author(user)
                .created(now)
                .build();
    }
}
