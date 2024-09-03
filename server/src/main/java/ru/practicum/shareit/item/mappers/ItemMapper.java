package ru.practicum.shareit.item.mappers;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemRequest;
import ru.practicum.shareit.item.Item;

import java.util.ArrayList;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemMapper {
    public static Item mapToItem(ItemRequest itemRequest) {
        Item item = new Item();
        item.setName(itemRequest.getName());
        item.setDescription(itemRequest.getDescription());
        item.setAvailable(itemRequest.getAvailable());
        return item;
    }

    public static ItemDto mapToItemDto(Item item) {
        ItemDto itemDto = new ItemDto();
        itemDto.setAvailable(item.getAvailable());
        itemDto.setId(item.getId());
        itemDto.setOwnerId(item.getOwner().getId());
        if (!item.getComments().isEmpty()) {
            itemDto.setComments(item.getComments().stream().map(CommentMapper::mapToCommentDto).toList());
        } else {
            itemDto.setComments(new ArrayList<>());
        }
        itemDto.setName(item.getName());
        itemDto.setDescription(item.getDescription());
        return itemDto;
    }
}
