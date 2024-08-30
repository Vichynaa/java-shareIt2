package ru.practicum.shareit.booking;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingRequest;
import ru.practicum.shareit.booking.dto.ItemForBookingDto;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.ItemInterface;
import ru.practicum.shareit.request.ItemRequestInterface;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserInterface;
import ru.practicum.shareit.user.dto.UserDto;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class BookingControllerIntegrationTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserInterface userService;

    @MockBean
    private BookingInterface bookingService;

    @MockBean
    private ItemInterface itemService;

    @MockBean
    private ItemRequestInterface itemRequestService;

    @SneakyThrows
    @Test
    void getBookingById() {
        long userId = 1L;
        long bookingId = 1L;
        Booking booking = new Booking();
        booking.setId(1L);
        booking.setItem(new Item());
        booking.setBooker(new User());
        booking.setStatus(BookingStatus.APPROVED);

        Mockito.when(bookingService.getBookingById(bookingId, userId)).thenReturn(booking);

        mockMvc.perform(get("/bookings/{bookingId}", bookingId)
                        .header("X-Sharer-User-Id", userId))
                .andExpect(status().isOk());
        Mockito.verify(bookingService).getBookingById(bookingId, userId);
    }

    @SneakyThrows
    @Test
    void create() {
        long userId = 1L;
        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setItemId(1L);
        BookingDto bookingDto = new BookingDto();
        bookingDto.setItem(new ItemForBookingDto());
        bookingDto.setBooker(new UserDto());
        Booking booking = new Booking();
        Item item = new Item();
        item.setAvailable(true);
        booking.setItem(item);
        booking.setBooker(new User());

        Mockito.when(bookingService.createRequest(bookingRequest, userId)).thenReturn(booking);

        String request = mockMvc.perform(post("/bookings")
                        .contentType("application/json")
                        .header("X-Sharer-User-Id", userId)
                        .content(objectMapper.writeValueAsString(bookingRequest)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Assertions.assertEquals(objectMapper.writeValueAsString(bookingDto), request);
    }
}
