package ru.practicum.shareit.booking;

import exception.ConflictException;
import exception.NotFoundException;
import exception.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.booking.dto.BookingRequest;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.ItemDbService;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserDbService;
import ru.practicum.shareit.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {
    @Mock
    private BookingRepository bookingRepository;
    @Mock
    private ItemDbService itemDbService;
    @Mock
    private UserDbService userDbService;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private BookingDbService bookingDbService;
    @Captor
    private ArgumentCaptor<Booking> bookingArgumentCaptor;

    @Test
    void createRequest_whenValidationIsCorrect_thenReturnBooking() {
        Long userId = 1L;
        Long itemId = 1L;
        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setItemId(itemId);
        bookingRequest.setStart(LocalDateTime.now().toString());
        bookingRequest.setEnd(LocalDateTime.now().toString());
        User user = new User();
        user.setId(2L);
        Item item = new Item();
        item.setId(itemId);
        item.setOwner(user);
        item.setAvailable(true);

        Mockito.when(itemDbService.getItemById(bookingRequest.getItemId())).thenReturn(item);
        Mockito.when(userDbService.findUserById(userId)).thenReturn(user);

        Booking request = bookingDbService.createRequest(bookingRequest, userId);

        Assertions.assertEquals(bookingRequest.getItemId(), request.getItem().getId());;
    }
    @Test
    void createRequest_whenItemUnavailable_thenReturnValidationException() {
        long userId = 1L;
        long itemId = 1L;
        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setItemId(itemId);
        bookingRequest.setStart(LocalDateTime.now().plusDays(1).toString());
        bookingRequest.setEnd(LocalDateTime.now().plusDays(2).toString());

        Item item = new Item();
        item.setId(itemId);
        item.setOwner(new User());
        item.getOwner().setId(userId);
        item.setAvailable(false);

        Mockito.when(itemDbService.getItemById(itemId)).thenReturn(item);

        Assertions.assertThrows(ValidationException.class, () -> bookingDbService.createRequest(bookingRequest, userId));
    }

    @Test
    void setApproved_whenBookingExistsAndOwner_thenUpdateBookingStatus() {
        long bookingId = 1L;
        long userId = 1L;
        Booking booking = new Booking();
        booking.setId(bookingId);
        booking.setItem(new Item());
        booking.getItem().setOwner(new User());
        booking.getItem().getOwner().setId(userId);
        booking.setStatus(BookingStatus.WAITING);

        Mockito.when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));
        Mockito.when(bookingRepository.existsById(bookingId)).thenReturn(true);

        bookingDbService.setApproved(Optional.of(true), bookingId, userId);

        Mockito.verify(bookingRepository).save(bookingArgumentCaptor.capture());
        Booking bookingSave = bookingArgumentCaptor.getValue();

        Assertions.assertEquals(BookingStatus.APPROVED, bookingSave.getStatus());
    }

    @Test
    void setApproved_whenBookingDoesNotExist_thenReturnConflictException() {
        long bookingId = 1L;
        long userId = 1L;

        Mockito.when(bookingRepository.existsById(bookingId)).thenReturn(false);

        Assertions.assertThrows(ConflictException.class, () -> bookingDbService.setApproved(Optional.of(true), bookingId, userId));
    }

    @Test
    void getBookingById_whenBookingExists_thenReturnBooking() {
        long bookingId = 1L;
        long userId = 1L;
        Booking booking = new Booking();
        booking.setId(bookingId);
        booking.setBooker(new User());
        booking.getBooker().setId(userId);

        Mockito.when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));
        Mockito.when(bookingRepository.existsById(bookingId)).thenReturn(true);

        Booking request = bookingDbService.getBookingById(bookingId, userId);

        Assertions.assertNotNull(request);
        Assertions.assertEquals(bookingId, request.getId());
    }

    @Test
    void getBookingById_whenBookingNotFound_thenReturnConflictException() {
        long bookingId = 1L;
        long userId = 1L;
        Booking booking = new Booking();
        booking.setId(bookingId);
        booking.setBooker(new User());
        booking.getBooker().setId(2L);

        Mockito.when(bookingRepository.existsById(bookingId)).thenReturn(false);

        Assertions.assertThrows(ConflictException.class, () -> bookingDbService.getBookingById(bookingId, userId));
    }

    @Test
    void getBookingsByRequester_whenStatusAll_thenReturnBookings() {
        long userId = 1L;
        Booking booking = new Booking();
        booking.setId(1L);
        List<Booking> bookings = List.of(booking);

        Mockito.when(userRepository.existsById(userId)).thenReturn(true);
        Mockito.when(bookingRepository.findByBookerId(userId)).thenReturn(bookings);

        List<Booking> result = bookingDbService.getBookingsByRequester(userId, Optional.of("all"));

        Assertions.assertEquals(bookings, result);
    }

    @Test
    void getBookingsByOwner_whenInvalidStatus_thenReturnNotFoundException() {
        long userId = 1L;

        Mockito.when(userRepository.existsById(userId)).thenReturn(true);

        Assertions.assertThrows(NotFoundException.class, () -> bookingDbService.getBookingsByOwner(userId, Optional.of("invalidStatus")));
    }
}
