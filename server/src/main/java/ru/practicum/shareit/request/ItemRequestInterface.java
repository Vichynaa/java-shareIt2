package ru.practicum.shareit.request;

import ru.practicum.shareit.request.dto.ItemRequestData;

import java.util.List;

public interface ItemRequestInterface {
    public ItemRequest create(Long userId, ItemRequestData itemRequestData);

    public List<ItemRequest> getAllByUser(Long userId);

    public List<ItemRequest> getAllByOtherUsers(Long userId);

    public ItemRequest getById(Long itemRequestId);

}



