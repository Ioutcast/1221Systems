package vasilkov._1221Systems.api.response;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import vasilkov._1221Systems.user.domain.User;
import vasilkov._1221Systems.user.domain.enums.GoalType;

/**
 * DTO for {@link User}
 * Исходя из того, что ID устанавливается при добавлении пользователя, наверное ?, можно и без него возращать.
 */
public record UserResponse(@NotBlank String name, @NotBlank String gender, @Email @NotBlank String email,
                           @Min(1) @Max(120) int age, @Min(30) @Max(300) double weight,
                           @Min(100) @Max(250) double height,
                           GoalType goal, double dailyCalorieNorm) {
}