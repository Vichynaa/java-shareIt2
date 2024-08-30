package ru.practicum.shareit.request;

import exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.request.dto.ItemRequestData;
import ru.practicum.shareit.user.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ItemRequestDbService implements ItemRequestInterface {
    private final ItemRequestRepository itemRequestRepository;
    private final UserRepository userRepository;

    public ItemRequest create(Long userId, ItemRequestData itemRequestData) {
        if (!userRepository.existsById(userId)) {
            log.error("Error такого пользователя не существует");
            throw new NotFoundException("Error такого пользователя не существует");
        }
        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setDescription(itemRequestData.getDescription());
        itemRequest.setOwner(userRepository.findById(userId).get());
        return itemRequestRepository.save(itemRequest);
    }

    public List<ItemRequest> getAllByUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            log.error("Error такого пользователя не существует");
            throw new NotFoundException("Error такого пользователя не существует");
        }
        return itemRequestRepository.findByOwnerId(userId);
    }

    public List<ItemRequest> getAllByOtherUsers(Long userId) {
        if (!userRepository.existsById(userId)) {
            log.error("Error такого пользователя не существует");
            throw new NotFoundException("Error такого пользователя не существует");
        }
        return itemRequestRepository.findAllByOthersOwners(userId);
    }

    public ItemRequest getById(Long itemRequestId) {
        if (!itemRequestRepository.existsById(itemRequestId)) {
            log.error("Error запрос не найден");
            throw new NotFoundException("Error запрос не найден");
        }
        return itemRequestRepository.findById(itemRequestId).get();
    }

}
