package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController extends BaseEntityController<User> {
    //private final Map<Long, User> users = new ConcurrentHashMap<>();
    //private long nextId = 1;

    @GetMapping
    public Collection<User> findAll() {
        log.info("Получен запрос на получение всех пользователей. Количество пользователей: {}", entities.size());
        return entities.values();
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        user.setId(nextId++);
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        entities.put(user.getId(), user);
        log.info("Создан новый пользователь: {}", user);
        return user;
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        if (!entities.containsKey(user.getId())) {
            log.error("Ошибка обновления: пользователь с id {} не найден", user.getId());
            throw new ValidationException("Пользователь с id " + user.getId() + " не найден");
        }
        entities.put(user.getId(), user);
        log.info("Обновлен пользователь: {}", user);
        return user;
    }
}
