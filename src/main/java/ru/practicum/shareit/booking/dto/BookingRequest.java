package ru.practicum.shareit.booking.dto;

import lombok.Builder;
import lombok.Data;

/**
 * TODO Sprint add-bookings.
 */

@Builder
@Data
public class BookingRequest {
    Long itemId;
    String start;
    String end;
}
