package ru.practicum.shareit.booking;

import exception.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingRequest;

import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
@Slf4j
@Validated
public class BookingController {
    private final BookingClient bookingClient;

    @PostMapping
    public ResponseEntity<Object> create(@RequestHeader("${shareIt.headers.userHeader}") Long userId, @RequestBody BookingRequest bookingRequest) {
        log.info("POST /booking - добавление запроса пользователем с id - {}", userId);
        if (bookingRequest.getItemId() == null) {
            log.error("Не указан id предмета");
            throw new ValidationException("Не указан id предмета");
        }
        if (bookingRequest.getStart() == null || bookingRequest.getEnd() == null) {
            log.error("Дата не может равняться null");
            throw new ValidationException("Дата не может быть пустой");
        }
        LocalDateTime startTime = LocalDateTime.parse(bookingRequest.getStart());
        LocalDateTime endTime = LocalDateTime.parse(bookingRequest.getEnd());
        if (startTime.isAfter(endTime) || startTime.isEqual(endTime) || endTime.isBefore(LocalDateTime.now().withNano(0)) || startTime.isBefore(LocalDateTime.now().withNano(0))) {
            log.error("Дата начала должна быть до даты конца");
            throw new ValidationException("Дата начала должна быть до даты конца");
        }
        return bookingClient.createRequest(bookingRequest, userId);
    }

    @PatchMapping("/{bookingId}")
    public ResponseEntity<Object> answer(@RequestHeader("${shareIt.headers.userHeader}") Long userId, @PathVariable Long bookingId, @RequestParam Optional<Boolean> approved) {
        log.info("PATCH /bookings/{}", userId);
        return bookingClient.setApproved(approved, bookingId, userId);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<Object> getBookingById(@RequestHeader("${shareIt.headers.userHeader}") Long userId, @PathVariable Long bookingId) {
        log.info("GET /bookings/{}", bookingId);
        return bookingClient.getBookingById(bookingId, userId);
    }

    @GetMapping
    public ResponseEntity<Object> getBookingsByRequesterId(@RequestHeader("${shareIt.headers.userHeader}") Long userId, @RequestParam Optional<String> status) {
        log.info("GET /bookings by Requester Id");
        return bookingClient.getBookingsByRequester(userId, status);
    }

    @GetMapping("/owner")
    public ResponseEntity<Object> getBookingsByOwnerId(@RequestHeader("${shareIt.headers.userHeader}") Long userId, @RequestParam Optional<String> status) {
        log.info("GET /bookings by Owner Id");
        return bookingClient.getBookingsByOwner(userId, status);
    }
}