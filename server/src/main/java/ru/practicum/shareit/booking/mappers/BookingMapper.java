package ru.practicum.shareit.booking.mappers;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.ItemForBookingDto;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.user.mappers.UserMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BookingMapper {
    public static BookingDto mapToBookingDto(Booking booking) {
        BookingDto bookingDto = new BookingDto();
        bookingDto.setId(booking.getId());
        bookingDto.setEnd(booking.getEnd());
        bookingDto.setStatus(booking.getStatus());
        bookingDto.setItem(mapToItemForBooking(booking.getItem()));
        bookingDto.setStart(booking.getStart());
        bookingDto.setBooker(UserMapper.mapToUserDto(booking.getBooker()));
        return bookingDto;
    }

    static ItemForBookingDto mapToItemForBooking(Item item) {
        ItemForBookingDto itemForBookingDto = new ItemForBookingDto();
        itemForBookingDto.setId(item.getId());
        itemForBookingDto.setName(item.getName());
        return itemForBookingDto;
    }
}
