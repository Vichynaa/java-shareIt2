package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingRequest;
import ru.practicum.shareit.booking.mappers.BookingMapper;

import java.util.List;
import java.util.Optional;

/**
 * TODO Sprint add-bookings.
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/bookings")
public class BookingController {
    private final BookingInterface bookingService;

    @PostMapping
    public BookingDto create(@RequestHeader("X-Sharer-User-Id") Long userId, @RequestBody BookingRequest bookingRequest) {
        log.info("POST /booking - добавление запроса пользователем с id - {}", userId);
        return BookingMapper.mapToBookingDto(bookingService.createRequest(bookingRequest, userId));
    }

    @PatchMapping("/{bookingId}")
    public BookingDto answer(@RequestHeader("X-Sharer-User-Id") Long userId, @PathVariable Long bookingId, @RequestParam Optional<Boolean> approved) {
        log.info("PATCH /bookings/{}", userId);
        return BookingMapper.mapToBookingDto(bookingService.setApproved(approved, bookingId, userId));
    }

    @GetMapping("/{bookingId}")
    public BookingDto getBookingById(@RequestHeader("X-Sharer-User-Id") Long userId, @PathVariable Long bookingId) {
        log.info("GET /bookings/{}", bookingId);
        return BookingMapper.mapToBookingDto(bookingService.getBookingById(bookingId, userId));
    }

    @GetMapping
    public List<BookingDto> getBookingsByRequesterId(@RequestHeader("X-Sharer-User-Id") Long userId, @RequestParam Optional<String> status) {
        log.info("GET /bookings by Requester Id");
        return bookingService.getBookingsByRequester(userId, status).stream().map(BookingMapper::mapToBookingDto).toList();
    }

    @GetMapping("/owner")
    public List<BookingDto> getBookingsByOwnerId(@RequestHeader("X-Sharer-User-Id") Long userId, @RequestParam Optional<String> status) {
        log.info("GET /bookings by Owner Id");
        return bookingService.getBookingsByOwner(userId, status).stream().map(BookingMapper::mapToBookingDto).toList();
    }
}

