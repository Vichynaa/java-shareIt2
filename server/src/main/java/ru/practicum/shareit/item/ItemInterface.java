package ru.practicum.shareit.item;

import ru.practicum.shareit.item.comment.Comment;
import ru.practicum.shareit.item.dto.CommentRequest;
import ru.practicum.shareit.item.dto.ItemRequest;

import java.util.List;
import java.util.Optional;

public interface ItemInterface {
    public Item create(Long userId, ItemRequest itemRequest);

    public Item update(Long userId, Long itemId, ItemRequest itemRequest);

    public List<Item> getItemsByUser(Long userId);

    public Item getItemById(Long itemId);

    public List<Item> search(Optional<String> text, Long userId);

    public Comment createComment(CommentRequest commentRequest, Long userId, Long itemId);
}
