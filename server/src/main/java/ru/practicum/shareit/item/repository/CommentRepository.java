package ru.practicum.shareit.item.repository;

import ru.practicum.shareit.common.repository.BaseRepository;
import ru.practicum.shareit.item.entity.Comment;

import java.util.List;

public interface CommentRepository extends BaseRepository<Comment, Long> {
    List<Comment> findAllByItem_Id(Long itemId);
}
