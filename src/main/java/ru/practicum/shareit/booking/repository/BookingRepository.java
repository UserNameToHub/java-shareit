package ru.practicum.shareit.booking.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    List<Booking> findAllByBookerIdAndEndDateIsBefore(Long id, LocalDateTime dateTime, Sort sort);

    @Query("select bk from Booking as bk " +
            "join fetch bk.booker as bkr " +
            "where bkr.id = :id and bk.startDate > :dateTime")
    List<Booking> findAllByBookerIdAndStartDateIsAfter(@Param("id") Long id,
                                                       @Param("dateTime") LocalDateTime dateTime,
                                                       @Param("sort") Sort sort);

    @Query("select bk from Booking as bk " +
            "join fetch bk.booker as bkr " +
            "where bkr.id = :id and :dateTime between bk.startDate and bk.endDate")
    List<Booking> findAllCurrentByBookerId(@Param("id") Long id,
                                           @Param("dateTime") LocalDateTime dateTime,
                                           @Param("sort") Sort sort);

    List<Booking> findAllByBookerId(Long id, Sort sort);

    List<Booking> findAllByBookerIdAndStatusIs(Long id, Status status, Sort sort);

    List<Booking> findAllByItemOwnerIdAndEndDateIsBefore(Long id, LocalDateTime dateTime, Sort sort);

    List<Booking> findAllByItemOwnerIdAndStartDateIsAfter(Long id, LocalDateTime dateTime, Sort sort);

    @Query("select bk from Booking as bk " +
            "join fetch bk.item as i " +
            "join fetch i.owner as o " +
            "where o.id = :id and :dateTime between bk.startDate and bk.endDate")
    List<Booking> findAllCurrentByItemOwnerId(@Param("id") Long id,
                                              @Param("dateTime") LocalDateTime dateTime,
                                              @Param("sort") Sort sort);

    List<Booking> findAllByItemOwnerId(Long id, Sort sort);

    List<Booking> findAllByItemOwnerIdAndStatusIs(Long id, Status status, Sort sort);


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