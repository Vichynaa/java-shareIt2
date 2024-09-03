package ru.practicum.shareit.request.dto;

import lombok.Data;

@Data
public class ItemInfo {
    private Long id;
    private String name;
    private Long ownerId;
}
