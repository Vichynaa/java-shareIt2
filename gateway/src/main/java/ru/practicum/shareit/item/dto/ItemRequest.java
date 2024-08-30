package ru.practicum.shareit.item.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ItemRequest {
    private String name;
    private String description;
    private Boolean available;
    private Long requestId;
}
