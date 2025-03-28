package vasilkov._1221Systems.api.response;

/**
 * Содержит суммарную пищевую ценность за определенный период.
 *
 * @param totalCalories общее количество калорий
 * @param totalProteins общее количество белков (в граммах)
 * @param totalFats общее количество жиров (в граммах)
 * @param totalCarbohydrates общее количество углеводов (в граммах)
 */
public record NutritionSummary(
        double totalCalories,
        double totalProteins,
        double totalFats,
        double totalCarbohydrates
) {
}
