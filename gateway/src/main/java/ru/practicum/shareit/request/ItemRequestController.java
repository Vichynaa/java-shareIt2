package ru.practicum.shareit.request;

import exception.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestData;

@Controller
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ItemRequestController {
    private final ItemRequestClient itemRequestClient;

    @PostMapping
    public ResponseEntity<Object> create(@RequestHeader("X-Sharer-User-Id") Long userId, @RequestBody ItemRequestData itemRequestData) {
        log.info("POST /requests - добавление запроса пользователем с id - {}", userId);
        if (itemRequestData.getDescription().isBlank()) {
            log.error("Нельзя создать объект без указания поля - description");
            throw new ValidationException("Нельзя создать объект без указания поля - description");
        }
        return itemRequestClient.create(userId, itemRequestData);
    }

    @GetMapping
    public ResponseEntity<Object> getRequestsByUser(@RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("GET /requests с id - {}", userId);
        return itemRequestClient.getAllByUser(userId);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getOthersUsersRequests(@RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("GET /requests/all с id - {}", userId);
        return itemRequestClient.getAllByOtherUsers(userId);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<Object> getRequestById(@PathVariable Long requestId) {
        log.info("GET /requests/{}", requestId);
        return itemRequestClient.getById(requestId);
    }
}