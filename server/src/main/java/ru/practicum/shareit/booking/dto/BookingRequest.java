package ru.practicum.shareit.booking.dto;

import lombok.Data;

@Data
public class BookingRequest {
    private Long itemId;
    private String start;
    private String end;
}
