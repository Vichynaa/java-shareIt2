package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.dto.BookingRequest;

import java.util.List;
import java.util.Optional;

public interface BookingInterface {
    public Booking createRequest(BookingRequest bookingRequest, Long userId);

    public Booking setApproved(Optional<Boolean> isApproved, Long bookingId, Long userId);

    public Booking getBookingById(Long bookingId, Long userId);

    public List<Booking> getBookingsByRequester(Long userId, Optional<String> status);

    public List<Booking> getBookingsByOwner(Long userId, Optional<String> status);
}
