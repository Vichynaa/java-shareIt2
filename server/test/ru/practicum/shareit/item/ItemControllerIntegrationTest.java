package ru.practicum.shareit.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.booking.BookingInterface;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemRequest;
import ru.practicum.shareit.request.ItemRequestInterface;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserInterface;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class ItemControllerIntegrationTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserInterface userService;

    @MockBean
    private BookingInterface bookingService;

    @MockBean
    private ItemInterface itemService;

    @MockBean
    private ItemRequestInterface itemRequestService;

    @SneakyThrows
    @Test
    protected void getItemById() {
        long itemId = 1L;
        Item item = new Item();
        item.setId(1L);
        item.setAvailable(true);
        item.setOwner(new User());

        Mockito.when(itemService.getItemById(itemId)).thenReturn(item);

        mockMvc.perform(get("/items/{itemId}", itemId))
                .andExpect(status().isOk());
        Mockito.verify(itemService).getItemById(itemId);
    }

    @SneakyThrows
    @Test
    protected void create() {
        long userId = 1L;
        long requestId = 1L;
        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setName("name");
        itemRequest.setAvailable(true);
        itemRequest.setDescription("description");
        itemRequest.setRequestId(requestId);
        Item item = new Item();
        item.setOwner(new User());
        item.setName("name");
        item.setDescription("description");
        item.setAvailable(true);
        ItemDto itemDto = new ItemDto();
        itemDto.setName(itemRequest.getName());
        itemDto.setAvailable(itemRequest.getAvailable());
        itemDto.setDescription(itemRequest.getDescription());
        itemDto.setComments(new ArrayList<>());

        Mockito.when(itemService.create(userId, itemRequest)).thenReturn(item);

        String request = mockMvc.perform(post("/items")
                        .contentType("application/json")
                        .header("X-Sharer-User-Id", userId)
                        .content(objectMapper.writeValueAsString(itemRequest)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Assertions.assertEquals(objectMapper.writeValueAsString(itemDto), request);
    }
}
