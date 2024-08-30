package ru.practicum.shareit.booking;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingRequest;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.user.User;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
public class BookingControllerTest {

    @Mock
    private BookingInterface bookingService;

    @InjectMocks
    private BookingController bookingController;

    @Test
    void create_whenInvoked_thenReturnBookingDto() {
        Long userId = 1L;
        BookingRequest bookingRequest = new BookingRequest();
        Booking booking = new Booking();
        booking.setId(1L);
        booking.setItem(new Item());
        booking.setBooker(new User());
        BookingDto expectedDto = new BookingDto();
        expectedDto.setId(1L);

        Mockito.when(bookingService.createRequest(any(BookingRequest.class), anyLong())).thenReturn(booking);

        BookingDto request = bookingController.create(userId, bookingRequest);

        Assertions.assertEquals(expectedDto.getId(), request.getId());
        Assertions.assertEquals(expectedDto.getClass(), request.getClass());
    }

    @Test
    void answer_whenInvoked_thenReturnBookingDto() {
        long userId = 1L;
        long bookingId = 1L;
        Booking booking = new Booking();
        booking.setId(1L);
        booking.setItem(new Item());
        booking.setBooker(new User());
        BookingDto expectedDto = new BookingDto();
        expectedDto.setId(1L);
        Mockito.when(bookingService.setApproved(Optional.of(true), bookingId, userId)).thenReturn(booking);

        BookingDto request = bookingController.answer(userId, bookingId, Optional.of(true));

        Assertions.assertEquals(expectedDto.getId(), request.getId());
        Assertions.assertEquals(expectedDto.getClass(), request.getClass());
    }

    @Test
    void getBookingById_whenInvoked_thenReturnBookingDto() {
        long userId = 1L;
        long bookingId = 1L;
        Booking booking = new Booking();
        booking.setId(1L);
        booking.setItem(new Item());
        booking.setBooker(new User());
        BookingDto expectedDto = new BookingDto();
        expectedDto.setId(1L);
        Mockito.when(bookingService.getBookingById(bookingId, userId)).thenReturn(booking);

        BookingDto request = bookingController.getBookingById(userId, bookingId);

        Assertions.assertEquals(expectedDto.getId(), request.getId());
        Assertions.assertEquals(expectedDto.getClass(), request.getClass());

    }

    @Test
    void getBookingsByRequesterId_whenInvoked_thenReturnBookingDtos() {
        long userId = 1L;
        Booking booking = new Booking();
        booking.setId(1L);
        booking.setItem(new Item());
        booking.setBooker(new User());
        BookingDto bookingDto = new BookingDto();
        bookingDto.setId(1L);
        List<Booking> bookings = List.of(booking);
        List<BookingDto> expectedDtos = List.of(bookingDto);
        Mockito.when(bookingService.getBookingsByRequester(userId, Optional.empty())).thenReturn(bookings);

        List<BookingDto> request = bookingController.getBookingsByRequesterId(userId, Optional.empty());

        Assertions.assertEquals(expectedDtos.getFirst().getId(), request.getFirst().getId());
        Assertions.assertEquals(expectedDtos.getFirst().getClass(), request.getFirst().getClass());
    }

    @Test
    void getBookingsByOwnerId_whenInvoked_thenReturnBookingDtos() {
        long userId = 1L;
        Booking booking = new Booking();
        booking.setId(1L);
        booking.setItem(new Item());
        booking.setBooker(new User());
        BookingDto bookingDto = new BookingDto();
        bookingDto.setId(1L);
        List<Booking> bookings = List.of(booking);
        List<BookingDto> expectedDtos = List.of(bookingDto);
        Mockito.when(bookingService.getBookingsByOwner(userId, Optional.empty())).thenReturn(bookings);

        List<BookingDto> request = bookingController.getBookingsByOwnerId(userId, Optional.empty());

        Assertions.assertEquals(expectedDtos.getFirst().getId(), request.getFirst().getId());
        Assertions.assertEquals(expectedDtos.getFirst().getClass(), request.getFirst().getClass());
    }
}
