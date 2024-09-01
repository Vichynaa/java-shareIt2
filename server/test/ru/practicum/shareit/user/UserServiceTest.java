package ru.practicum.shareit.user;

import exception.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.user.dto.UserRequest;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserDbService userDbService;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @Test
    protected void findUserById_whenUserFound_thenReturnUser() {
        long userId = 1L;
        User expectedUser = new User();
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(expectedUser));
        Mockito.when(userRepository.existsById(userId)).thenReturn(true);

        User user = userDbService.findUserById(userId);

        Assertions.assertEquals(expectedUser, user);
        Mockito.verify(userRepository).findById(userId);
        Mockito.verify(userRepository).existsById(userId);
    }

    @Test
    protected void findUserById_whenUserNotFound_thenReturnNotFoundException() {
        long userId = 1L;
        Mockito.when(userRepository.existsById(userId)).thenReturn(false);

        Assertions.assertThrows(NotFoundException.class, () -> userDbService.findUserById(userId));
        Mockito.verify(userRepository).existsById(userId);
        Mockito.verify(userRepository, Mockito.never()).findById(anyLong());
    }

    @Test
    protected void update_whenUserFound_thenReturnUser() {
        long userId = 1L;
        User oldUser = new User();
        oldUser.setName("name");
        oldUser.setEmail("email@email.ru");

        UserRequest userRequest = new UserRequest();
        userRequest.setName("name1");
        userRequest.setEmail("email1@email.ru");
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(oldUser));
        Mockito.when(userRepository.existsById(userId)).thenReturn(true);

        userDbService.update(userRequest, userId);

        Mockito.verify(userRepository).save(userArgumentCaptor.capture());
        User savedUser = userArgumentCaptor.getValue();

        Assertions.assertEquals("name1", savedUser.getName());
        Assertions.assertEquals("email1@email.ru", savedUser.getEmail());
        Mockito.verify(userRepository).findById(userId);
        Mockito.verify(userRepository).existsById(userId);
    }

    @Test
    protected void update_whenUserNotFound_thenReturnNotFoundException() {
        long userId = 1L;
        UserRequest userRequest = new UserRequest();
        userRequest.setName("name");
        userRequest.setEmail("email@email.ru");
        Mockito.when(userRepository.existsById(userId)).thenReturn(false);

        Assertions.assertThrows(NotFoundException.class, () -> userDbService.update(userRequest, userId));
        Mockito.verify(userRepository).existsById(userId);
        Mockito.verify(userRepository, Mockito.never()).save(any(User.class));
    }

    @Test
    protected void deleteUserById_whenUserFound_thenReturnCorrectMessage() {
        long userId = 1L;
        String expectedMessage = "Пользователь с id - " + userId + ", удалил свой аккаунт";
        Mockito.when(userRepository.existsById(userId)).thenReturn(true);

        String message = userDbService.deleteUserById(userId);

        Assertions.assertEquals(expectedMessage, message);
        Mockito.verify(userRepository).existsById(userId);
        Mockito.verify(userRepository).deleteById(userId);
    }

    @Test
    protected void deleteUserById_whenUserNotFound_thenReturnCorrectMessage() {
        long userId = 1L;
        Mockito.when(userRepository.existsById(userId)).thenReturn(false);

        Assertions.assertThrows(NotFoundException.class, () -> userDbService.deleteUserById(userId));
        Mockito.verify(userRepository).existsById(userId);
        Mockito.verify(userRepository, Mockito.never()).deleteById(userId);
    }

    @Test
    protected void findAllUsers_thenVerify() {
        userDbService.findAll();

        Mockito.verify(userRepository).findAll();
    }

    @Test
    protected void createUser_thenVerify() {
        UserRequest userRequest = new UserRequest();
        userRequest.setName("name");
        userRequest.setEmail("email@email.ru");

        userDbService.create(userRequest);

        Mockito.verify(userRepository).save(any(User.class));
    }
}
