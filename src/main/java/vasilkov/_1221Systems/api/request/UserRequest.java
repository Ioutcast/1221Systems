package vasilkov._1221Systems.api.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import vasilkov._1221Systems.user.domain.User;
import vasilkov._1221Systems.user.domain.enums.Gender;
import vasilkov._1221Systems.user.domain.enums.GoalType;

/**
 * DTO for {@link User}
 */
public record UserRequest(Long id, @NotBlank String name, Gender gender, @Email @NotBlank String email,
                          @Min(1) @Max(120) int age, @Min(30) @Max(300) double weight,
                          @Min(100) @Max(250) double height, GoalType goal) {
}