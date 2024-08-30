package ru.practicum.shareit.request.mappers;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.request.dto.ItemInfo;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestMapper {
    public static ItemRequestDto mapToItemRequestDto(ItemRequest itemRequest) {
        ItemRequestDto itemRequestDto = new ItemRequestDto();
        itemRequestDto.setItems(mapToListItemInfo(itemRequest.getItems()));
        itemRequestDto.setCreated(itemRequest.getDate());
        itemRequestDto.setDescription(itemRequest.getDescription());
        itemRequestDto.setId(itemRequest.getId());
        itemRequestDto.setOwnerId(itemRequest.getOwner().getId());
        return itemRequestDto;
    }

    private static List<ItemInfo> mapToListItemInfo(List<Item> itemList) {
        return itemList.stream().map(
                item -> {
                    ItemInfo itemInfo = new ItemInfo();
                    itemInfo.setId(item.getId());
                    itemInfo.setName(item.getName());
                    itemInfo.setOwnerId(item.getOwner().getId());
                    return itemInfo;
                }
        ).toList();
    }
}
