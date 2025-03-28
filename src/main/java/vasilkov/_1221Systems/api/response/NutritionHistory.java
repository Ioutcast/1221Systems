package vasilkov._1221Systems.api.response;

import java.time.LocalDate;
import java.util.Map;

/**
 * Представляет историю питания пользователя за указанный период.
 *
 * @param userId идентификатор пользователя
 * @param startDate начальная дата периода
 * @param endDate конечная дата периода
 * @param dailySummaries ежедневная статистика питания (дата → NutritionSummary)
 */
public record NutritionHistory(
        Long userId,
        LocalDate startDate,
        LocalDate endDate,
        Map<LocalDate, NutritionSummary> dailySummaries
) {
}
