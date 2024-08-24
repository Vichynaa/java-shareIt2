package ru.practicum.shareit.item.dto;

import lombok.Builder;
import lombok.Data;

/**
 * TODO Sprint add-controllers.
 */
@Builder
@Data
public class ItemRequest {
    private String name;
    private String description;
    private Boolean available;
}
