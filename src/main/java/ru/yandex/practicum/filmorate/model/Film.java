package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.*;
import ru.yandex.practicum.filmorate.validation.ValidReleaseDate;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Film {
   private Long id;

   @NotBlank(message = "Название фильма не может быть пустым")
   private String name;

   @Size(max = 200, message = "Описание не может превышать 200 символов")
   private String description;

   @NotNull(message = "Дата релиза обязательна")
   @PastOrPresent(message = "Дата релиза не может быть в будущем")
   @ValidReleaseDate
   private LocalDate releaseDate;

   public boolean isValidReleaseDate() {
      LocalDate earliestReleaseDate = LocalDate.of(1895, 12, 28);
      return releaseDate != null && !releaseDate.isBefore(earliestReleaseDate);
   }


   @Positive(message = "Продолжительность фильма должна быть положительным числом")
   private int duration;
}
