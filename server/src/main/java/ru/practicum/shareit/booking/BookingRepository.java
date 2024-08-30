package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByBookerId(Long requesterId);

    @Query(value = "SELECT * FROM bookings WHERE booker_id = :bookerId AND item_id = :itemId AND end_time < :timestamp", nativeQuery = true)
    Booking findPastBooking(@Param("bookerId") Long userId, @Param("itemId") Long itemId, @Param("timestamp") Timestamp timestamp);

    @Query("SELECT b FROM Booking AS b WHERE b.start < CURRENT_TIMESTAMP AND b.end > CURRENT_TIMESTAMP AND b.booker = ?1")
    List<Booking> findCurrentBookings(Long userId);

    @Query("SELECT b FROM Booking AS b WHERE b.end < CURRENT_TIMESTAMP AND b.booker = ?1")
    List<Booking> findPastBookings(Long userId);

    @Query("SELECT b FROM Booking AS b WHERE b.start > CURRENT_TIMESTAMP AND b.booker = ?1")
    List<Booking> findFutureBookings(Long userId);

    @Query("SELECT b FROM Booking AS b WHERE b.booker = ?1 AND b.status = ?2")
    List<Booking> findBookingsByStatusByUserId(Long userId, String status);

    @Query("SELECT b FROM Booking AS b WHERE b.item.owner.id = ?1")
    List<Booking> findByOwnerId(Long ownerId);

    @Query("SELECT b FROM Booking AS b WHERE b.start < CURRENT_TIMESTAMP AND b.end > CURRENT_TIMESTAMP AND b.item.owner.id = ?1")
    List<Booking> findOwnerCurrentBookings(Long userId);

    @Query("SELECT b FROM Booking AS b WHERE b.end < CURRENT_TIMESTAMP AND b.item.owner.id = ?1")
    List<Booking> findOwnerPastBookings(Long userId);

    @Query("SELECT b FROM Booking AS b WHERE b.start > CURRENT_TIMESTAMP AND b.item.owner.id = ?1")
    List<Booking> findOwnerFutureBookings(Long userId);

    @Query("SELECT b FROM Booking AS b WHERE b.item.owner.id = ?1 AND b.status = ?2")
    List<Booking> findOwnerBookingsByStatusByUserId(Long userId, String status);
}
