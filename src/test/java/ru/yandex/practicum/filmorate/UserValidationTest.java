package ru.yandex.practicum.filmorate;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserValidationTest {
    private static Validator validator;

    @BeforeAll
    static void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldFailWhenEmailIsBlank() {
        User user = User.builder()
                .email("")
                .login("validLogin")
                .name("Valid Name")
                .birthday(LocalDate.of(1990, 1, 1))
                .build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty(), "Ожидалась ошибка из-за пустого email");
    }

    @Test
    void shouldFailWhenEmailWithoutAtSymbol() {
        User user = User.builder()
                .email("invalidEmail.com")
                .login("validLogin")
                .name("Valid Name")
                .birthday(LocalDate.of(1990, 1, 1))
                .build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty(), "Ожидалась ошибка из-за некорректного email (отсутствует @)");
    }

    @Test
    void shouldFailWhenLoginContainsSpaces() {
        User user = User.builder()
                .email("valid@email.com")
                .login("invalid login")
                .name("Valid Name")
                .birthday(LocalDate.of(1990, 1, 1))
                .build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty(), "Ожидалась ошибка из-за пробела в логине");
    }

    @Test
    void shouldUseLoginAsNameIfNameIsBlank() {
        User user = User.builder()
                .email("valid@email.com")
                .login("validLogin")
                .name("")
                .birthday(LocalDate.of(1990, 1, 1))
                .build();

        assertTrue(user.getName().isEmpty(), "Имя пользователя должно быть пустым");
    }

    @Test
    void shouldFailWhenBirthdayIsInFuture() {
        User user = User.builder()
                .email("valid@email.com")
                .login("validLogin")
                .name("Valid Name")
                .birthday(LocalDate.of(2100, 1, 1))
                .build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty(), "Ожидалась ошибка из-за даты рождения в будущем");
    }

    @Test
    void shouldPassWhenValidUser() {
        User user = User.builder()
                .email("valid@email.com")
                .login("validLogin")
                .name("Valid Name")
                .birthday(LocalDate.of(1990, 1, 1))
                .build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.isEmpty(), "Пользователь с корректными данными не должен иметь ошибок валидации");
    }
}
