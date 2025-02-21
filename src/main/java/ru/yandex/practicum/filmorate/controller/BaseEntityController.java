package ru.yandex.practicum.filmorate.controller;
import ru.yandex.practicum.filmorate.model.Identifiable;

import java.util.HashMap;
import java.util.Map;

public class BaseEntityController <T extends Identifiable>{
    protected final Map<Long, T> entities = new HashMap<>();
    protected long nextId = 1;

    protected Long generateId() {
        return nextId++;
    }
}
