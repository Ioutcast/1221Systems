package vasilkov._1221Systems.api.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;
import java.util.List;

public record MealRequest(@NotNull LocalDate date,
                          @NotEmpty List<@Positive Long> dishIds) { //предполагаю, что позитив :)
}
