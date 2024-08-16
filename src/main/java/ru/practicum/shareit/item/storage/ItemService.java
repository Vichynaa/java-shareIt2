//package ru.practicum.shareit.item;:
//
//
//import exception.NotFoundException;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import ru.practicum.shareit.item.dto.ItemDto;
//import ru.practicum.shareit.item.storage.ItemStorage;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class ItemService implements ItemInterface {
//    private final ItemStorage inMemoryItemStorage;
//
//    @Override
//    public Item create(Long userId, ItemDto itemDto) {
//        return inMemoryItemStorage.create(userId, itemDto);
//    }
//
//    @Override
//    public Item update(Long userId, Long itemId, ItemDto itemDto) {
//        return inMemoryItemStorage.update(userId, itemId, itemDto);
//    }
//
//    @Override
//    public Item getItemById(Long itemId) {
//        if (inMemoryItemStorage.getItemById(itemId).isEmpty()) {
//            log.error("Объекта с id - {}, не найден", itemId);
//            throw new NotFoundException(String.format("Объекта с id - %d, не найден", itemId));
//        }
//        return inMemoryItemStorage.getItemById(itemId).get();
//    }
//
//    @Override
//    public List<Item> getItemsByUser(Long userId) {
//        return inMemoryItemStorage.getItemsByUser(userId);
//    }
//
//    @Override
//    public List<Item> search(Optional<String> text, Long userId) {
//        return inMemoryItemStorage.search(text, userId);
//    }
//}
