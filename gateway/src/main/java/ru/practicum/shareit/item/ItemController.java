package ru.practicum.shareit.item;

import exception.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentRequest;
import ru.practicum.shareit.item.dto.ItemRequest;

import java.util.Optional;

@Controller
@RequestMapping(path = "/items")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ItemController {
    private final ItemClient itemClient;

    @PostMapping
    public ResponseEntity<Object> create(@RequestHeader("X-Sharer-User-Id") Long userId,
                                         @RequestBody ItemRequest itemRequest) {
        log.info("POST /items - добавление предмета пользователем с id - {}", userId);
        if (itemRequest.getAvailable() == null || (itemRequest.getName() == null ||
                itemRequest.getName().isBlank()) || (itemRequest.getDescription() == null ||
                itemRequest.getDescription().isBlank())) {
            log.error("Нельзя создать объект без указания полей - available, name, description");
            throw new ValidationException("Нельзя создать объект без указания полей - available, name, description");
        }
        return itemClient.create(userId, itemRequest);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<Object> update(@RequestHeader("X-Sharer-User-Id") Long userId,
                                         @PathVariable Long itemId, @RequestBody ItemRequest itemRequest) {
        log.info("PATCH /items/{}", userId);
        if (itemRequest.getDescription() != null && itemRequest.getDescription().isBlank()) {
            log.error("Описание не может быть пустым");
            throw new ValidationException("Описание не может быть пустым");
        }
        if (itemRequest.getName() != null && itemRequest.getName().isBlank()) {
            log.error("Имя не может быть пустым");
            throw new ValidationException("Имя не может быть пустым");
        }
        return itemClient.update(userId, itemId, itemRequest);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<Object> getItemById(@PathVariable Long itemId) {
        log.info("GET /items/{}", itemId);
        return itemClient.getItemById(itemId);
    }

    @GetMapping
    public ResponseEntity<Object> getItemsByUser(@RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("GET /items с id - {}", userId);
        return itemClient.getItemsByUser(userId);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> searchItems(@RequestParam Optional<String> text,
                                              @RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("GET /items/search с текстом - {}", text);
        return itemClient.searchItems(text, userId);
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> createComments(@PathVariable Long itemId,
                                                 @RequestHeader("X-Sharer-User-Id") Long userId,
                                                 @RequestBody CommentRequest commentRequest) {
        log.info("POST /itemId/comment");
        ResponseEntity<Object> objectResponseEntity = itemClient.createComments(itemId, userId, commentRequest);
        log.info(objectResponseEntity.toString());
        return objectResponseEntity;
    }
}
