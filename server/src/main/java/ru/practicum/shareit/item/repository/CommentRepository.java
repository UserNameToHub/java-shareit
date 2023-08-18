package ru.practicum.shareit.item.repository;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.common.repository.BaseRepository;
import ru.practicum.shareit.item.entity.Comment;

import java.util.List;

@Repository
public interface CommentRepository extends BaseRepository<Comment, Long> {
    List<Comment> findAllByItem_Id(Long itemId);
}
