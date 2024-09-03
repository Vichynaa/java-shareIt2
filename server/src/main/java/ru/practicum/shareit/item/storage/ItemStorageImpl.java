//package ru.practicum.shareit.item.storage;
//
//import exception.NotFoundException;
//import exception.ValidationException;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//import ru.practicum.shareit.item.dto.ItemDto;
//import ru.practicum.shareit.item.mappers.ItemMapper;
//import ru.practicum.shareit.item.Item;
//import ru.practicum.shareit.user.storage.UserStorage;
//
//import java.util.*;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class ItemStorageImpl implements ItemStorage {
//    private final UserStorage inMemoryUserStorage;
//    private final Map<Long, Map<Long, Item>> items = new HashMap<>();
//    private long itemId = 0;
//
//    @Override
//    public Item create(Long userId, ItemDto itemDto) {
//        if (itemDto.getAvailable() == null || (itemDto.getName() == null ||
//                itemDto.getName().isBlank()) || (itemDto.getDescription() == null ||
//                itemDto.getDescription().isBlank())) {
//            log.error("Нельзя создать объект без указания полей - available, name, description");
//            throw new ValidationException("Нельзя создать объект без указания полей - available, name, description");
//        }
//
//        if (!inMemoryUserStorage.isUserContains(userId)) {
//            log.error("Error такого пользователя не существует");
//            throw new NotFoundException("Error такого пользователя не существует");
//        }
//        Item newItem = ItemMapper.mapToItem(itemDto);
//        newItem.setId(getNextId());
//        newItem.setOwnerId(userId);
//        Map<Long, Item> itemList = items.getOrDefault(userId, new HashMap<>());
//        itemList.put(newItem.getId(), newItem);
//        items.put(userId, itemList);
//        return newItem;
//    }
//
//    @Override
//    public Item update(Long userId, Long itemId, ItemDto itemDto) {
//        Map<Long, Item> itemsForUpdate = items.getOrDefault(userId, new HashMap<>());
//        if (getItemById(itemId).isEmpty()) {
//            log.error("Error объекта с id - {}, не существует", itemId);
//            throw new NotFoundException(String.format("Error объекта с id - " + itemId + ", не существует"));
//        }
//        if (!inMemoryUserStorage.isUserContains(userId)) {
//            log.error("Error такого пользователя не существует");
//            throw new NotFoundException("Error такого пользователя не существует");
//        }
//        if (!itemsForUpdate.containsKey(itemId)) {
//            log.debug("Debug пользователь с id - {}, не владеет объектом - {}", userId, itemId);
//            throw new NotFoundException(String.format("Пользователь с id - %d, не владеет объектом - %d", userId, itemId));
//        }
//        Item itemForUpdate = itemsForUpdate.get(itemId);
//        if (itemDto.getDescription() != null) {
//            itemForUpdate.setDescription(itemDto.getDescription());
//        }
//        if (itemDto.getName() != null) {
//            itemForUpdate.setName(itemDto.getName());
//        }
//        if (itemDto.getAvailable() != null) {
//            itemForUpdate.setAvailable(itemDto.getAvailable());
//        }
//        itemsForUpdate.put(itemId, itemForUpdate);
//        items.put(userId, itemsForUpdate);
//        return itemForUpdate;
//    }
//
//    @Override
//    public Optional<Item> getItemById(Long itemId) {
//        return items.values().stream()
//                .flatMap(itemMap -> itemMap.values().stream())
//                .filter(item -> item.getId().equals(itemId))
//                .findFirst();
//    }
//
//    @Override
//    public List<Item> getItemsByUser(Long userId) {
//        if (!inMemoryUserStorage.isUserContains(userId)) {
//            log.error("Error такого пользователя не существует");
//            throw new NotFoundException("Error такого пользователя не существует");
//        }
//        return items.get(userId).values().stream().toList();
//    }
//
//    @Override
//    public List<Item> search(Optional<String> text, Long userId) {
//        if (text.isEmpty() || text.get().isBlank()) {
//            return new ArrayList<>();
//        }
//        String searchText = text.get().toLowerCase();
//        return items.values().stream().flatMap(
//                itemMap -> itemMap.values().stream()).filter(Item::getAvailable).filter(
//                        item -> item.getName().toLowerCase().contains(searchText) ||
//                                item.getDescription().toLowerCase().contains(searchText)).toList();
//    }
//
//    private long getNextId() {
//        return ++itemId;
//    }
//}
