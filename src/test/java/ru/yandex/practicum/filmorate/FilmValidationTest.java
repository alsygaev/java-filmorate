package ru.yandex.practicum.filmorate;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FilmValidationTest {
    private static Validator validator;

    @BeforeAll
    static void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldFailWhenNameIsBlank() {
        Film film = Film.builder()
                .name("")
                .description("Valid description")
                .releaseDate(LocalDate.of(2000, 1, 1))
                .duration(120)
                .build();

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty(), "Ожидалась ошибка из-за пустого названия");
    }

    @Test
    void shouldFailWhenDescriptionTooLong() {
        String longDescription = "A".repeat(201); // 201 символ, нарушает ограничение в 200
        Film film = Film.builder()
                .name("Valid Name")
                .description(longDescription)
                .releaseDate(LocalDate.of(2000, 1, 1))
                .duration(120)
                .build();

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty(), "Ожидалась ошибка из-за слишком длинного описания");
    }

    @Test
    void shouldFailWhenReleaseDateBefore1895() {
        Film film = Film.builder()
                .name("Valid Name")
                .description("Valid description")
                .releaseDate(LocalDate.of(1890, 1, 1))
                .duration(120)
                .build();

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty(), "Ожидалась ошибка из-за даты релиза до 1895 года");
    }

    @Test
    void shouldFailWhenDurationIsNegative() {
        Film film = Film.builder()
                .name("Valid Name")
                .description("Valid description")
                .releaseDate(LocalDate.of(2000, 1, 1))
                .duration(-10)
                .build();

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty(), "Ожидалась ошибка из-за отрицательной продолжительности");
    }

    @Test
    void shouldPassWhenValidFilm() {
        Film film = Film.builder()
                .name("Valid Name")
                .description("Valid description")
                .releaseDate(LocalDate.of(2000, 1, 1))
                .duration(120)
                .build();

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertTrue(violations.isEmpty(), "Фильм с корректными данными не должен иметь ошибок валидации");
    }
}
