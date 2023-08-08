package ru.practicum.shareit.request;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.common.repository.BaseRepository;
import ru.practicum.shareit.request.entity.ItemRequest;

import java.util.List;

public interface RequestRepository extends BaseRepository<ItemRequest, Long> {
    List<ItemRequest> findAllByRequester_Id(Long id);

    @Query("select r from ItemRequest r " +
            "where r.requester.id <> :userId")
    Page<ItemRequest> findAll(Long userId, Pageable pageable);
}
