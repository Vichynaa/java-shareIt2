package ru.practicum.shareit.booking.dto;

import lombok.Data;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.user.dto.UserDto;

import java.time.LocalDateTime;

@Data
public class BookingDto {
    private long id;
    ItemForBookingDto item;
    UserDto booker;
    LocalDateTime start;
    LocalDateTime end;
    BookingStatus status;
}
