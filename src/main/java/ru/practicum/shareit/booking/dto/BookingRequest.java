package ru.practicum.shareit.booking.dto;

import lombok.Builder;
import lombok.Data;

/**
 * TODO Sprint add-bookings.
 */

@Builder
@Data
public class BookingRequest {
    private Long itemId;
    private String start;
    private String end;
}
