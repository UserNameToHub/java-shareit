package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.common.dto.BaseDtoMapper;
import ru.practicum.shareit.item.mapper.ItemDtoMapper;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.request.dto.RequestDto;
import ru.practicum.shareit.request.entity.ItemRequest;
import ru.practicum.shareit.user.entity.User;
import ru.practicum.shareit.user.mapper.UserDtoMapper;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class RequestDtoMapper implements BaseDtoMapper<ItemRequest, RequestDto> {
    private final UserDtoMapper userMapper;
    private final ItemRepository itemRepository;
    private final ItemDtoMapper itemMapper;

    @Override
    public RequestDto toDto(ItemRequest type) {
        return RequestDto.builder()
                .id(type.getId())
                .user(userMapper.toDto(type.getRequester()))
                .description(type.getDescription())
                .created(type.getCreated())
                .items(itemMapper.toDtoList(itemRepository.findAllByRequest_Id(type.getId())))
                .build();
    }

    public ItemRequest toEntity(RequestDto requestDto, User requester, LocalDateTime now) {
        return ItemRequest.builder()
                .requester(requester)
                .description(requestDto.getDescription())
                .created(now)
                .build();
    }
}