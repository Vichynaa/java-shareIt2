package ru.practicum.shareit.user;

import exception.ConflictException;
import exception.ValidationException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserRequest;

@Controller
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Slf4j
@Validated
public class UserController {
    private final UserClient userClient;

    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody UserRequest userRequest) {
        if (userRequest.getEmail() == null || !(userRequest.getEmail().contains("@"))) {
            log.error("Электронная почта не может быть пустой и должна содержать символ @");
            throw new ValidationException("Электронная почта не может быть пустой и должна содержать символ @");
        }
        if (userRequest.getName() == null) {
            log.error("Имя не может быть пустым");
            throw new ConflictException("Имя не может быть пустым");
        }
        log.info("Post /users");
        return userClient.create(userRequest);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<Object> update(@Valid @RequestBody UserRequest userRequest, @PathVariable Long userId) {
        if (userRequest.getEmail() != null && (userRequest.getEmail().isBlank() || !(userRequest.getEmail().contains("@")))) {
            log.error("Электронная почта не может быть пустой и должна содержать символ @");
            throw new ValidationException("Электронная почта не может быть пустой и должна содержать символ @");
        }
        if (userRequest.getName() != null && userRequest.getName().isBlank()) {
            log.error("Имя не может быть пустым");
            throw new ValidationException("Имя не может быть пустым");
        }
        log.info("PATCH /users/{} - с даннами: name - {}; email - {}", userId, userRequest.getName(), userRequest.getEmail());
        return userClient.update(userRequest, userId);
    }

    @GetMapping
    public ResponseEntity<Object> findAll() {
        log.info("GET /users");
        return userClient.findAll();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> findUserById(@PathVariable Long userId) {
        log.info("GET /users/{}", userId);
        return userClient.findUserById(userId);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> deleteUserById(@PathVariable Long userId) {
        log.info("DELETE /users/{}", userId);
        return userClient.deleteUserById(userId);
    }
}
