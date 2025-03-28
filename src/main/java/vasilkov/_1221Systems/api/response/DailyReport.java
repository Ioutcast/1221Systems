package vasilkov._1221Systems.api.response;

import vasilkov._1221Systems.meal.domain.Meal;

import java.time.LocalDate;
import java.util.List;
/**
 * Дневной отчет о питании пользователя.
 *
 * @param date дата отчета
 * @param meals список приемов пищи за день
 * @param summary суммарная пищевая ценность
 * @param dailyCalorieNorm дневная норма калорий пользователя
 * @param withinCalorieLimit флаг соблюдения нормы калорий
 */
public record DailyReport(LocalDate date, List<Meal> meals, NutritionSummary summary, double dailyCalorieNorm,
                          boolean withinCalorieLimit) {
}
