package ru.practicum.shareit.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.booking.dto.BookingRequest;
import ru.practicum.shareit.client.BaseClient;

import java.util.Map;
import java.util.Optional;

@Service
public class BookingClient extends BaseClient {
    private static final String API_PREFIX = "/bookings";

    @Autowired
    public BookingClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                        .build()
        );
    }

    public ResponseEntity<Object> createRequest(BookingRequest bookingRequest, Long userId) {
        return post("", userId, bookingRequest);
    }

    public ResponseEntity<Object> setApproved(Optional<Boolean> isApproved, Long bookingId, Long userId) {
        Map<String, Object> parameters = Map.of(
                "approved", isApproved.orElse(false)
        );
        return patch("/" + bookingId + "?approved={approved}", userId, parameters, "{}");
    }

    public ResponseEntity<Object> getBookingById(Long bookingId, Long userId) {
        return get(String.format("/%d", bookingId), userId);

    }

    public ResponseEntity<Object> getBookingsByRequester(Long userId, Optional<String> status) {
        Map<String, Object> parameters = Map.of(
                "status", status.orElse("all")
        );
        return get("?status={status}", userId, parameters);
    }

    public ResponseEntity<Object> getBookingsByOwner(Long userId, Optional<String> status) {
        Map<String, Object> parameters = Map.of(
                "status", status.orElse("all")
        );
        return get("/owner?status={status}", userId, parameters);
    }
}


