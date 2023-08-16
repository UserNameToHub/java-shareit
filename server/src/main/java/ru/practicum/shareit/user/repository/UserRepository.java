package ru.practicum.shareit.user.repository;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.common.repository.BaseRepository;
import ru.practicum.shareit.user.entity.User;

public interface UserRepository extends BaseRepository<User, Long> {
}
