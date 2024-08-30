package ru.practicum.shareit.request;


import exception.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.request.dto.ItemRequestData;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
public class RequestServiceTest {

    @Mock
    private ItemRequestRepository itemRequestRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private ItemRequestDbService itemRequestDbService;

    @Test
    void create_whenUserFound_thenReturnItemRequest() {
        Long userId = 1L;
        ItemRequestData itemRequestData = new ItemRequestData();
        itemRequestData.setDescription("description");

        ItemRequest expectedItemRequest = new ItemRequest();
        expectedItemRequest.setDescription("description");
        expectedItemRequest.setOwner(new User());

        Mockito.when(userRepository.existsById(userId)).thenReturn(true);
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(new User()));
        Mockito.when(itemRequestRepository.save(any(ItemRequest.class))).thenReturn(expectedItemRequest);

        ItemRequest request = itemRequestDbService.create(userId, itemRequestData);

        Assertions.assertEquals(expectedItemRequest, request);
        Mockito.verify(userRepository).existsById(userId);
        Mockito.verify(userRepository).findById(userId);
        Mockito.verify(itemRequestRepository).save(any(ItemRequest.class));
    }

    @Test
    void create_whenUserNotFound_thenReturnNotFoundException() {
        Long userId = 1L;
        ItemRequestData itemRequestData = new ItemRequestData();
        itemRequestData.setDescription("description");

        Mockito.when(userRepository.existsById(userId)).thenReturn(false);

        Assertions.assertThrows(NotFoundException.class, () -> itemRequestDbService.create(userId, itemRequestData));

        Mockito.verify(userRepository).existsById(userId);
        Mockito.verify(userRepository, Mockito.never()).findById(anyLong());
        Mockito.verify(itemRequestRepository, Mockito.never()).save(any(ItemRequest.class));
    }

    @Test
    void getAllByUser_whenUserFound_thenReturnItemRequests() {
        Long userId = 1L;
        List<ItemRequest> expectedItemRequests = List.of(new ItemRequest(), new ItemRequest());

        Mockito.when(userRepository.existsById(userId)).thenReturn(true);
        Mockito.when(itemRequestRepository.findByOwnerId(userId)).thenReturn(expectedItemRequests);

        List<ItemRequest> request = itemRequestDbService.getAllByUser(userId);

        Assertions.assertEquals(expectedItemRequests, request);
        Mockito.verify(userRepository).existsById(userId);
        Mockito.verify(itemRequestRepository).findByOwnerId(userId);
    }

    @Test
    void getAllByUser_whenUserNotFound_thenReturnNotFoundException() {
        Long userId = 1L;

        Mockito.when(userRepository.existsById(userId)).thenReturn(false);

        Assertions.assertThrows(NotFoundException.class, () -> itemRequestDbService.getAllByUser(userId));

        Mockito.verify(userRepository).existsById(userId);
        Mockito.verify(itemRequestRepository, Mockito.never()).findByOwnerId(anyLong());
    }

    @Test
    void getAllByOtherUsers_whenUserFound_thenReturnItemRequests() {
        Long userId = 1L;
        List<ItemRequest> expectedItemRequests = List.of(new ItemRequest(), new ItemRequest());

        Mockito.when(userRepository.existsById(userId)).thenReturn(true);
        Mockito.when(itemRequestRepository.findAllByOthersOwners(userId)).thenReturn(expectedItemRequests);

        List<ItemRequest> request = itemRequestDbService.getAllByOtherUsers(userId);

        Assertions.assertEquals(expectedItemRequests, request);
        Mockito.verify(userRepository).existsById(userId);
        Mockito.verify(itemRequestRepository).findAllByOthersOwners(userId);
    }

    @Test
    void getAllByOtherUsers_whenUserNotFound_thenReturnNotFoundException() {
        Long userId = 1L;

        Mockito.when(userRepository.existsById(userId)).thenReturn(false);

        Assertions.assertThrows(NotFoundException.class, () -> itemRequestDbService.getAllByOtherUsers(userId));

        Mockito.verify(userRepository).existsById(userId);
        Mockito.verify(itemRequestRepository, Mockito.never()).findAllByOthersOwners(anyLong());
    }

    @Test
    void getById_whenItemRequestFound_thenReturnItemRequest() {
        Long itemRequestId = 1L;
        ItemRequest expectedItemRequest = new ItemRequest();

        Mockito.when(itemRequestRepository.existsById(itemRequestId)).thenReturn(true);
        Mockito.when(itemRequestRepository.findById(itemRequestId)).thenReturn(Optional.of(expectedItemRequest));

        ItemRequest request = itemRequestDbService.getById(itemRequestId);

        Assertions.assertEquals(expectedItemRequest, request);
        Mockito.verify(itemRequestRepository).existsById(itemRequestId);
        Mockito.verify(itemRequestRepository).findById(itemRequestId);
    }

    @Test
    void getById_whenItemRequestNotFound_thenReturnFoundException() {
        Long itemRequestId = 1L;

        Mockito.when(itemRequestRepository.existsById(itemRequestId)).thenReturn(false);

        Assertions.assertThrows(NotFoundException.class, () -> itemRequestDbService.getById(itemRequestId));

        Mockito.verify(itemRequestRepository).existsById(itemRequestId);
        Mockito.verify(itemRequestRepository, Mockito.never()).findById(anyLong());
    }
}