package ru.practicum.shareit.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.common.exception.NotFoundException;
import ru.practicum.shareit.request.dto.RequestDto;
import ru.practicum.shareit.request.mapper.RequestDtoMapper;
import ru.practicum.shareit.request.repository.RequestRepository;
import ru.practicum.shareit.user.entity.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.shareit.util.Constants.ORDER_BY_CREATED_DESC;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final RequestDtoMapper requestDtoMapper;

    @Override
    public RequestDto findById(Long id, Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException(String.format("Пользователь с id %d не найден.", userId));
        }
        return requestDtoMapper.toDto(requestRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("Запрос с id %d не найден.", id))));
    }

    @Override
    public RequestDto create(RequestDto type, Long userId) {
        User requester = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("Пользователь с id %d не найден.", userId)));
        return requestDtoMapper.toDto(requestRepository.save(requestDtoMapper.toEntity(type, requester,
                LocalDateTime.now())));
    }

    @Override
    public List<RequestDto> findIllById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException(String.format("Пользователь с id %d не найден.", id));
        }
        return requestDtoMapper.toDtoList(requestRepository.findAllByRequester_Id(id));
    }

    @Override
    public List<RequestDto> getAll(Integer from, Integer size, Long userId) {
        return requestDtoMapper.toDtoList(requestRepository.findAll(userId,
                PageRequest.of(from, size, ORDER_BY_CREATED_DESC)).toList());
    }
}
