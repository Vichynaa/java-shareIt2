package ru.practicum.shareit.request;

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
import ru.practicum.shareit.item.ItemInterface;
import ru.practicum.shareit.request.dto.ItemRequestData;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserInterface;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class RequestControllerIntegrationTest {
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
    protected void getRequestById() {
        long requestId = 1L;
        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setOwner(new User());

        Mockito.when(itemRequestService.getById(requestId)).thenReturn(itemRequest);

        mockMvc.perform(get("/requests/{requestId}", requestId))
                .andExpect(status().isOk());
        Mockito.verify(itemRequestService).getById(requestId);
    }

    @SneakyThrows
    @Test
    protected void create() {
        long userId = 1L;
        ItemRequestData itemRequestData = new ItemRequestData();
        itemRequestData.setDescription("description");
        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setDescription("description");
        User owner = new User();
        owner.setEmail("email@email.ru");
        owner.setId(userId);
        owner.setName("name");
        itemRequest.setOwner(owner);
        ItemRequestDto itemRequestDto = new ItemRequestDto();
        itemRequestDto.setOwnerId(itemRequest.getOwner().getId());
        itemRequestDto.setCreated(itemRequest.getDate());
        itemRequestDto.setId(itemRequest.getId());
        itemRequestDto.setDescription(itemRequest.getDescription());
        itemRequestDto.setItems(new ArrayList<>());


        Mockito.when(itemRequestService.create(userId, itemRequestData)).thenReturn(itemRequest);

        String request = mockMvc.perform(post("/requests")
                        .contentType("application/json")
                        .header("X-Sharer-User-Id", userId)
                        .content(objectMapper.writeValueAsString(itemRequestData)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Assertions.assertEquals(objectMapper.writeValueAsString(itemRequestDto), request);
    }
}
