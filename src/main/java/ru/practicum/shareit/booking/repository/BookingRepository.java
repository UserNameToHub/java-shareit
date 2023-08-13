package ru.practicum.shareit.booking.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.shareit.booking.entity.Booking;
import ru.practicum.shareit.booking.enumeration.Status;
import ru.practicum.shareit.common.repository.BaseRepository;
import ru.practicum.shareit.item.dto.ItemBookingGettingDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends BaseRepository<Booking, Long> {
    @Query("select bk from Booking as bk " +
            "join fetch bk.booker as booker " +
            "join fetch bk.item as item " +
            "join fetch item.owner as owner " +
            "where bk.id = :bookingId and (booker.id = :userId or owner.id = :userId)")
    Optional<Booking> findByIdAndUserId(@Param("bookingId") Long bookingId,
                                        @Param("userId") Long userId);

    Page<Booking> findAllByBookerIdAndEndDateIsBefore(Long id, LocalDateTime dateTime, Pageable pageable);

    @Query("select bk from Booking as bk " +
            "where bk.booker.id = :id and bk.startDate > :dateTime")
    Page<Booking> findAllByBookerIdAndStartDateIsAfter(@Param("id") Long id,
                                                       @Param("dateTime") LocalDateTime dateTime,
                                                       @Param("page") Pageable pageable);

    @Query("select bk from Booking as bk " +
            "where bk.booker.id = :id and :dateTime between bk.startDate and bk.endDate")
    Page<Booking> findAllCurrentByBookerId(@Param("id") Long id,
                                           @Param("dateTime") LocalDateTime dateTime,
                                           @Param("page") Pageable pageable);

    Page<Booking> findAllByBooker_Id(Long id, Pageable pageable);

    Page<Booking> findAllByBookerIdAndStatusIs(Long id, Status status, Pageable pageable);

    Page<Booking> findAllByItemOwnerIdAndEndDateIsBefore(Long id, LocalDateTime dateTime, Pageable pageable);

    Page<Booking> findAllByItemOwnerIdAndStartDateIsAfter(Long id, LocalDateTime dateTime, Pageable pageable);

    @Query("select bk from Booking as bk " +
            "where bk.item.owner.id = :id and :dateTime between bk.startDate and bk.endDate")
    Page<Booking> findAllCurrentByItemOwnerId(@Param("id") Long id,
                                              @Param("dateTime") LocalDateTime dateTime,
                                              @Param("page") Pageable pageable);

    Page<Booking> findAllByItemOwnerId(Long id, Pageable pageable);

    Page<Booking> findAllByItemOwnerIdAndStatusIs(Long id, Status status, Pageable pageable);


    @Query("select new ru.practicum.shareit.item.dto.ItemBookingGettingDto(bk.id, bk.booker.id) " +
            "from Booking as bk " +
            "where bk.item.id = :id and bk.status = 'APPROVED' and bk.startDate < :dateTime")
    List<ItemBookingGettingDto> findLastById(@Param("id") Long id,
                                             @Param("dateTime") LocalDateTime dateTime,
                                             @Param("limit") Pageable limit);

    @Query("select new ru.practicum.shareit.item.dto.ItemBookingGettingDto(bk.id, bk.booker.id) " +
            "from Booking as bk " +
            "where bk.item.id = :id and bk.status = 'APPROVED' and bk.startDate > :dateTime")
    List<ItemBookingGettingDto> findNextById(@Param("id") Long id,
                                             @Param("dateTime") LocalDateTime dateTime,
                                             @Param("limit") Pageable limit);

    @Query("select case when count(bk) > 0 then true else false end from Booking as bk " +
            "where bk.item.id = :id and bk.booker.id = :bookerId and bk.endDate < :current")
    boolean existsBookingsByIdAndAndBookerIdAndEndDateBefore(@Param("id") Long id,
                                                             @Param("bookerId") Long bookerId,
                                                             @Param("current") LocalDateTime current);
}