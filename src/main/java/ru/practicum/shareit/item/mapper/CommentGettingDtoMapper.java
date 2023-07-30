package ru.practicum.shareit.item.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.common.dto.BaseDtoMapper;
import ru.practicum.shareit.item.dto.CommentGettingTo;
import ru.practicum.shareit.item.entity.Comment;

@Component
public class CommentGettingDtoMapper implements BaseDtoMapper<Comment, CommentGettingTo> {
    @Override
    public CommentGettingTo toDto(Comment type) {
        return CommentGettingTo.builder()
                .id(type.getId())
                .text(type.getText())
                .authorName(type.getAuthor().getName())
                .created(type.getCreated())
                .build();
    }
}
