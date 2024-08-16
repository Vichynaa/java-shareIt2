package ru.practicum.shareit.user;

import ru.practicum.shareit.user.dto.UserRequest;

import java.util.List;

public interface UserInterface {
    public User create(UserRequest userRequest);

    public User update(UserRequest userRequest, Long userId);

    public List<User> findAll();

    public User findUserById(Long userId);

    public String deleteUserById(Long userId);
}
