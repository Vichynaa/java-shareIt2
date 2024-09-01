package ru.practicum.shareit.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.booking.BookingInterface;
import ru.practicum.shareit.item.ItemInterface;
import ru.practicum.shareit.request.ItemRequestInterface;
import ru.practicum.shareit.user.dto.UserRequest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class UserControllerIntegrationTest {
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
    private ItemRequestInterface itemRequestInterface;

    @SneakyThrows
    @Test
    protected void findUserById() {
        long userId = 0L;

        Mockito.when(userService.findUserById(userId)).thenReturn(new User());

        mockMvc.perform(get("/users/{userId}", userId))
                .andExpect(status().isOk());
        Mockito.verify(userService).findUserById(userId);
    }

    @SneakyThrows
    @Test
    protected void create() {
        UserRequest userToCreate = new UserRequest();
        User user = new User();
        Mockito.when(userService.create(userToCreate)).thenReturn(user);

        String request = mockMvc.perform(post("/users")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(userToCreate)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JSONAssert.assertEquals(objectMapper.writeValueAsString(user), request, true);
    }
}
