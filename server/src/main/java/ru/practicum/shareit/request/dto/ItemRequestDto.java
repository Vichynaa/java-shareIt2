package ru.practicum.shareit.request.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * TODO Sprint add-item-requests.
 */

@Data
public class ItemRequestDto {
    private Long id;
    private Long ownerId;
    private String description;
    private LocalDateTime created;
    private List<ItemInfo> items;
}
