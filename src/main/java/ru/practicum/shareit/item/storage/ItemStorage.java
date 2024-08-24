package ru.practicum.shareit.item.storage;

import ru.practicum.shareit.item.dto.ItemRequest;
import ru.practicum.shareit.item.Item;

import java.util.List;
import java.util.Optional;

public interface ItemStorage {
    public Item create(Long userId, ItemRequest itemRequest);

    public Item update(Long userId, Long itemId, ItemRequest itemRequest);

    public Optional<Item> getItemById(Long itemId);

    public List<Item> getItemsByUser(Long userId);

    public List<Item> search(Optional<String> text, Long userId);
}
