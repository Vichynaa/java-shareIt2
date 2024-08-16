//package ru.practicum.shareit.user;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import ru.practicum.shareit.user.dto.UserDto;
//import ru.practicum.shareit.user.storage.UserStorage;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class UserService implements UserInterface {
//    private final UserStorage inMemoryUserStorage;
//
//    @Override
//    public User create(UserDto userDto) {
//        return inMemoryUserStorage.create(userDto);
//    }
//
//    @Override
//    public User update(UserDto userDto, Long userId) {
//        return inMemoryUserStorage.update(userDto, userId);
//    }
//
//    @Override
//    public List<User> findAll() {
//        return inMemoryUserStorage.findAll();
//    }
//
//    @Override
//    public User findUserById(Long userId) {
//        return inMemoryUserStorage.findUserById(userId);
//    }
//
//    @Override
//    public String deleteUserById(Long userId) {
//        return inMemoryUserStorage.deleteUserById(userId);
//    }
//
//}
