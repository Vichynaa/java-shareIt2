package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestData;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.mappers.RequestMapper;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
public class ItemRequestController {
    private final ItemRequestInterface itemRequestService;

    @PostMapping
    public ItemRequestDto create(@RequestHeader("X-Sharer-User-Id") Long userId, @RequestBody ItemRequestData itemRequestData) {
        log.info("POST /requests - добавление запроса пользователем с id - {}", userId);
        return RequestMapper.mapToItemRequestDto(itemRequestService.create(userId, itemRequestData));
    }

    @GetMapping
    public List<ItemRequestDto> getRequestsByUser(@RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("GET /requests с id - {}", userId);
        return itemRequestService.getAllByUser(userId).stream().map(RequestMapper::mapToItemRequestDto).toList();
    }

    @GetMapping("/all")
    public List<ItemRequestDto> getOthersUsersRequests(@RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("GET /requests/all с id - {}", userId);
        return itemRequestService.getAllByOtherUsers(userId).stream().map(RequestMapper::mapToItemRequestDto).toList();
    }

    @GetMapping("/{requestId}")
    public ItemRequestDto getRequestById(@PathVariable Long requestId) {
        log.info("GET /requests/{}", requestId);
        return RequestMapper.mapToItemRequestDto(itemRequestService.getById(requestId));
    }

}
