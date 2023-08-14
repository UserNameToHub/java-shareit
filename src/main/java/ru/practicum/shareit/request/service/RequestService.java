package ru.practicum.shareit.request.service;

import ru.practicum.shareit.common.service.BaseService;
import ru.practicum.shareit.request.dto.RequestDto;

import java.util.List;

public interface RequestService extends BaseService<RequestDto, Long> {
    RequestDto findById(Long id, Long userId);

    RequestDto create(RequestDto type, Long userId);

    List<RequestDto> findIllById(Long id);

    List<RequestDto> getAll(Integer from, Integer size, Long userId);
}
