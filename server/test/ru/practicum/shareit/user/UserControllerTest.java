package ru.practicum.shareit.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserRequest;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    @Mock
    private UserInterface userDbService;

    @InjectMocks
    private UserController userController;

    @Test
    void findAllUsers_whenInvoked_thenReturnUserDtoCollectionInBody() {
        List<UserDto> expectedUsers = List.of(new UserDto());
        Mockito.when(userDbService.findAll()).thenReturn(List.of(new User()));

        List<UserDto> users = userController.findAll();

        Assertions.assertEquals(expectedUsers, users);
    }

    @Test
    void findUserById_whenInvoked_thenReturnUserDto() {
        User user = new User();
        user.setId(1L);
        user.setEmail("email@email.ru");
        user.setName("name");
        Mockito.when(userDbService.findUserById(anyLong()))
                .thenReturn(user);

        UserDto userDto = userController.findUserById(1L);

        Assertions.assertEquals(user.getId(), userDto.getId());
        Assertions.assertEquals(user.getName(), userDto.getName());
        Assertions.assertEquals(user.getEmail(), userDto.getEmail());
    }

    @Test
    void deleteUserById_whenInvoked_thenReturnMessage() {
        String expectedMessage = "{\"message\":\"Пользователь с id - 1, удалил свой аккаунт\"}";
        Mockito.when(userDbService.deleteUserById(1L))
                .thenReturn("Пользователь с id - " +  1L+ ", удалил свой аккаунт");

        String message = userController.deleteUserById(1L);

        Assertions.assertEquals(expectedMessage, message);
    }

    @Test
    void create_whenInvoked_thenReturnUserDtoWithSameFieldsAsInUserRequest() {
        UserRequest userRequest = new UserRequest();
        userRequest.setEmail("gmail@gmail.ru");
        userRequest.setName("name");
        User expectedUserFields = new User();
        expectedUserFields.setEmail("gmail@gmail.ru");
        expectedUserFields.setName("name");
        Mockito.when(userDbService.create(any(UserRequest.class))).thenReturn(expectedUserFields);

        UserDto response = userController.create(userRequest);

        Assertions.assertEquals(expectedUserFields.getEmail(), response.getEmail());
        Assertions.assertEquals(expectedUserFields.getName(), response.getName());
    }

    @Test
    void update_whenInvoked_thenReturnUserDtoWithSameFieldsAsInUserRequest() {
        UserRequest userRequest = new UserRequest();
        userRequest.setEmail("gmail@gmail.ru");
        userRequest.setName("name");
        User expectedUserFields = new User();
        expectedUserFields.setEmail("gmail@gmail.ru");
        expectedUserFields.setName("name");
        Mockito.when(userDbService.update(any(UserRequest.class), anyLong())).thenReturn(expectedUserFields);

        UserDto response = userController.update(userRequest, 1L);

        Assertions.assertEquals(expectedUserFields.getEmail(), response.getEmail());
        Assertions.assertEquals(expectedUserFields.getName(), response.getName());
    }
}
