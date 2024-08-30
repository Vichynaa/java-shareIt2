package ru.practicum.shareit.booking.dto;

import jakarta.validation.constraints.FutureOrPresent;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BookingRequest {
    private Long itemId;
    @FutureOrPresent(message = "Время начала бронирования не может быть в прошлом")
    private String start;
    private String end;
}