package ru.practicum.shareit.request;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.request.dto.ItemRequestData;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.user.User;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class RequestControllerTest {

    @Mock
    private ItemRequestInterface itemRequestDbService;
    @InjectMocks
    private ItemRequestController itemRequestController;

    @Test
    void create_whenInvoked_thenReturnItemRequestDto() {
        long userId = 1L;
        User user = new User();
        user.setId(1L);
        ItemRequestData itemRequestData = new ItemRequestData();
        itemRequestData.setDescription("description");
        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setDescription("description");
        itemRequest.setOwner(user);
        ItemRequestDto expectedDto = new ItemRequestDto();
        expectedDto.setDescription("description");
        expectedDto.setOwnerId(1L);
        Mockito.when(itemRequestDbService.create(userId, itemRequestData)).thenReturn(itemRequest);

        ItemRequestDto request = itemRequestController.create(userId, itemRequestData);

        Assertions.assertEquals(expectedDto.getDescription(), request.getDescription());
    }

    @Test
    void getRequestsByUser_whenInvoked_thenReturnItemRequestDto() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        ItemRequestDto expectedValues = new ItemRequestDto();
        expectedValues.setDescription("description");
        List<ItemRequestDto> expectedItemRequest = List.of(expectedValues);
        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setOwner(user);
        itemRequest.setDescription("description");
        List<ItemRequest> itemRequests = List.of(itemRequest);

        Mockito.when(itemRequestDbService.getAllByUser(userId)).thenReturn(itemRequests);

        List<ItemRequestDto> request = itemRequestController.getRequestsByUser(userId);

        Assertions.assertEquals(expectedItemRequest.getFirst().getDescription(), request.getFirst().getDescription());
    }

    @Test
    void getOthersUsersRequests_whenInvoked_thenReturnItemRequestDto() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        ItemRequestDto expectedValue = new ItemRequestDto();
        expectedValue.setDescription("description");
        List<ItemRequestDto> expectedItemRequest = List.of(expectedValue);
        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setOwner(user);
        itemRequest.setDescription("description");
        List<ItemRequest> itemRequests = List.of(itemRequest);
        Mockito.when(itemRequestDbService.getAllByOtherUsers(userId)).thenReturn(itemRequests);

        List<ItemRequestDto> request = itemRequestController.getOthersUsersRequests(userId);

        Assertions.assertEquals(expectedItemRequest.getFirst().getDescription(), request.getFirst().getDescription());
    }

    @Test
    void getRequestById_whenInvoked_thenReturnItemRequestDto() {
        Long requestId = 1L;
        User user = new User();
        user.setId(requestId);
        ItemRequestDto expectedItemRequestDto = new ItemRequestDto();
        expectedItemRequestDto.setDescription("description");

        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setDescription("description");
        itemRequest.setOwner(user);

        Mockito.when(itemRequestDbService.getById(requestId)).thenReturn(itemRequest);

        ItemRequestDto request = itemRequestController.getRequestById(requestId);

        Assertions.assertEquals(expectedItemRequestDto.getDescription(), request.getDescription());
    }
}
