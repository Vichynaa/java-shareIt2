package ru.practicum.shareit.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserRequest;
import ru.practicum.shareit.user.mappers.UserMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO Sprint add-controllers.
 */

@Slf4j
@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UserController {
    private final UserInterface userService;

    @PostMapping
    public UserDto create(@Valid @RequestBody UserRequest userRequest) {
        log.info("POST /users - с даннами: name - {}; email - {}", userRequest.getName(), userRequest.getEmail());
        return UserMapper.mapToUserDto(userService.create(userRequest));
    }

    @PatchMapping("/{userId}")
    public UserDto update(@Valid @RequestBody UserRequest userRequest, @PathVariable Long userId) {
        log.info("PATCH /users/{} - с даннами: name - {}; email - {}", userId, userRequest.getName(), userRequest.getEmail());
        return UserMapper.mapToUserDto(userService.update(userRequest, userId));
    }

    @GetMapping
    public List<UserDto> findAll() {
        log.info("GET /users");
        return userService.findAll().stream().map(UserMapper::mapToUserDto).toList();
    }

    @GetMapping("/{userId}")
    public UserDto findUserById(@PathVariable Long userId) {
        log.info("GET /users/{}", userId);
        return UserMapper.mapToUserDto(userService.findUserById(userId));
    }

    @DeleteMapping("/{userId}")
    public String deleteUserById(@PathVariable Long userId) {
        log.info("DELETE /users/{}", userId);
        return String.format("{\"message\":\"%s\"}", userService.deleteUserById(userId));
    }

}
