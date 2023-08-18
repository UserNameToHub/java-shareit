package ru.practicum.shareit.item.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.common.repository.BaseRepository;
import ru.practicum.shareit.item.entity.Item;

import java.util.List;

@Repository
public interface ItemRepository extends BaseRepository<Item, Long> {
    @Query("select i from Item as i " +
            "where i.available is true and :search <> '' and (upper(i.name) like concat('%', upper(:search), '%') " +
            "or upper(i.description) like concat('%', upper(:search), '%'))")
    Page<Item> findByNameOrDescriptionText(@Param("search") String search, Pageable pageable);

    @Query("select i from Item as i " +
            "where i.owner.id = :ownerId")
    Page<Item> findAllByOwnerId(@Param("ownerId") Long ownerId,
                                @Param("page") Pageable pageable);

    @Modifying
    @Query("delete from Item as i " +
            "where i.id = :itemId and i.owner.id = :ownerId")
    void deleteByOwnerId(@Param("itemId") Long itemId,
                         @Param("ownerId") Long ownerId);

    boolean existsByIdAndOwnerId(@Param("itemId") Long itemId,
                                 @Param("ownerId") Long ownerId);

    List<Item> findAllByRequest_Id(Long id);
}
