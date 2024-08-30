package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.CommentRequest;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemRequest;
import ru.practicum.shareit.item.mappers.CommentMapper;
import ru.practicum.shareit.item.mappers.ItemMapper;

import java.util.List;
import java.util.Optional;

/**
 * TODO Sprint add-controllers.
 */
@Slf4j
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemInterface itemService;

    @PostMapping
    public ItemDto create(@RequestHeader("X-Sharer-User-Id") Long userId, @RequestBody ItemRequest itemRequest) {
        log.info("POST /items - добавление предмета пользователем с id - {}", userId);
        return ItemMapper.mapToItemDto(itemService.create(userId, itemRequest));
    }

    @PatchMapping("/{itemId}")
    public ItemDto update(@RequestHeader("X-Sharer-User-Id") Long userId, @PathVariable Long itemId, @RequestBody ItemRequest itemRequest) {
        log.info("PATCH /items/{}", userId);
        return ItemMapper.mapToItemDto(itemService.update(userId, itemId, itemRequest));
    }

    @GetMapping("/{itemId}")
    public ItemDto getItemById(@PathVariable Long itemId) {
        log.info("GET /items/{}", itemId);
        return ItemMapper.mapToItemDto(itemService.getItemById(itemId));
    }

    @GetMapping
    public List<ItemDto> getItemsByUser(@RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("GET /items с id - {}", userId);
        return itemService.getItemsByUser(userId).stream().map(ItemMapper::mapToItemDto).toList();
    }

    @GetMapping("/search")
    public List<ItemDto> searchItems(@RequestParam Optional<String> text, @RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("GET /items/search с текстом - {}", text);
        return itemService.search(text, userId).stream().map(ItemMapper::mapToItemDto).toList();
    }

    @PostMapping("/{itemId}/comment")
    public CommentDto createComments(@PathVariable Long itemId, @RequestHeader("X-Sharer-User-Id") Long userId, @RequestBody CommentRequest commentRequest) {
        log.info("POST /itemId/comment");
        return CommentMapper.mapToCommentDto(itemService.createComment(commentRequest, userId, itemId));
    }
}
