package ru.yandex.practicum.filmorate.model;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
public abstract class BaseEntity {
    protected Long id;
}
