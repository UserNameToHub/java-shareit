package ru.practicum.shareit.item.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.shareit.common.repository.BaseRepository;
import ru.practicum.shareit.item.entity.Item;

import java.util.List;

public interface ItemRepository extends BaseRepository<Item, Long> {
    @Query("select i from Item as i " +
            "where i.available is true and :search <> '' and (upper(i.name) like concat('%', upper(:search), '%') " +
            "or upper(i.description) like concat('%', upper(:search), '%'))")
    List<Item> findByNameOrDescriptionText(@Param("search") String search);
    @Query("select i from Item as i " +
            "join fetch i.owner as o " +
            "where o.id = :ownerId")
    List<Item> findAllByOwnerId(@Param("ownerId") Long ownerId,
                                @Param("sort") Sort sort);

    @Query("delete from Item as i " +
            "where i.id = :itemId and i.owner.id = :ownerId")
    void deleteByOwnerId(@Param("itemId") Long itemId,
                         @Param("ownerId") Long ownerId);

    boolean existsByIdAndOwnerId(@Param("itemId") Long itemId,
                                 @Param("ownerId") Long ownerId);
}
