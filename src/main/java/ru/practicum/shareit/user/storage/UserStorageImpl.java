//package ru.practicum.shareit.user.storage;
//
//import exception.ConflictException;
//import exception.NotFoundException;
//import exception.ValidationException;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//import ru.practicum.shareit.user.User;
//import ru.practicum.shareit.user.dto.UserDto;
//
//import java.util.*;
//
//@Slf4j
//@Component
//public class UserStorageImpl implements UserStorage {
//    private final Map<Long, User> users = new HashMap<>();
//    private final Map<Long, String> emailInUse = new HashMap<>();
//
//    private long userId = 0;
//
//    @Override
//    public User create(UserDto userDto) {
//        if (userDto.getEmail() == null || !(userDto.getEmail().contains("@"))) {
//            log.error("Электронная почта не может быть пустой и должна содержать символ @");
//            throw new ValidationException("Электронная почта не может быть пустой и должна содержать символ @");
//        }
//        if (emailInUse.containsValue(userDto.getEmail())) {
//            log.error("Почта " + userDto.getEmail() + " уже используется");
//            throw new ConflictException("Почта " + userDto.getEmail() + " уже используется");
//        }
//        if (userDto.getName() == null) {
//            log.error("Имя не может быть пустым");
//            throw new ConflictException("Имя не может быть пустым");
//        }
//        User user = new User(getNextId(), userDto.getName(), userDto.getEmail());
//        users.put(user.getId(), user);
//        emailInUse.put(user.getId(), user.getEmail());
//        return user;
//    }
//
//    @Override
//    public User update(UserDto userDto, Long userId) {
//        User user = users.get(userId);
//        if (userDto.getEmail() != null) {
//            if (emailInUse.containsValue(userDto.getEmail())) {
//                if (!userDto.getEmail().equals(emailInUse.get(userId))) {
//                    log.error("Почта " + userDto.getEmail() + " уже используется");
//                    throw new ConflictException("Почта " + userDto.getEmail() + " уже используется");
//                }
//            }
//            if (userDto.getEmail().isBlank() || !(userDto.getEmail().contains("@"))) {
//                log.error("Электронная почта не может быть пустой и должна содержать символ @");
//                throw new ValidationException("Электронная почта не может быть пустой и должна содержать символ @");
//            }
//            emailInUse.remove(user.getId());
//            user.setEmail(userDto.getEmail());
//            emailInUse.put(user.getId(), user.getEmail());
//        }
//        if (userDto.getName() != null) {
//            if (userDto.getName().isBlank()) {
//                log.error("Имя не может быть пустым");
//                throw new ValidationException("Имя не может быть пустым");
//            }
//            user.setName(userDto.getName());
//        }
//        users.put(user.getId(), user);
//        return user;
//    }
//
//    @Override
//    public List<User> findAll() {
//        return users.values().stream().toList();
//    }
//
//    @Override
//    public User findUserById(Long userId) {
//        if (!users.containsKey(userId)) {
//            log.error("Пользователя с id - {}, не найден", userId);
//            throw new NotFoundException("Пользователя с id - " + userId + ", не найден");
//        }
//        return users.get(userId);
//    }
//
//    @Override
//    public String deleteUserById(Long userId) {
//        if (!users.containsKey(userId)) {
//            log.error("Пользователя с id - {}, не найден", userId);
//            throw new NotFoundException("Пользователя с id - " + userId + ", не найден");
//        }
//        emailInUse.remove(userId);
//        users.remove(userId);
//        return "Пользователь с id - " + ", удалил свой аккаунт";
//    }
//
//    @Override
//    public Boolean isUserContains(Long userId) {
//        return users.containsKey(userId);
//    }
//
//    private long getNextId() {
//        return ++userId;
//    }
//}
