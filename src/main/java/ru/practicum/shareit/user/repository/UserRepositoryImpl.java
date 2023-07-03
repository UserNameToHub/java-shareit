package ru.practicum.shareit.user.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.user.entity.User;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository<Long> {
    private final Map<Long, User> repository = new HashMap<>();

    @Override
    public List<User> findAll() {
        return repository.values().stream().collect(Collectors.toList());
    }

    @Override
    public Optional<User> findById(Long idType) {
        return repository.containsKey(idType) ? Optional.of(repository.get(idType)) : Optional.empty();
    }

    @Override
    public void delete(Long idType) {
        repository.remove(idType);
    }

    @Override
    public User update(User type) {
        User user = repository.get(type.getId());
        if (nonNull(type.getEmail())) {
            user.setEmail(type.getEmail());
        }
        if (nonNull(type.getName())) {
            user.setName(type.getName());
        }

        return user;
    }

    @Override
    public User create(User type) {
        repository.put(type.getId(), type);
        return type;
    }

    @Override
    public boolean existsById(Long idType) {
        return repository.containsKey(idType);
    }

    @Override
    public boolean isUnique(String str) {
        if (repository.values().isEmpty()) {
            return true;
        }
        return !repository.values().stream()
                .map(user -> user.getEmail())
                .anyMatch(email -> email.equals(str));
    }
}