package vasilkov._1221Systems.user.calories.calculation.lifestyle;

import vasilkov._1221Systems.user.domain.enums.GoalType;

/**
 * Интерфейс для расчета коэффициента калорий в зависимости от цели пользователя.
 * Определяет стратегию модификации базового метаболизма (BMR).
 */
public interface CalorieGoalMultiplier {

    /**
     * Применяет коэффициент к базовому уровню метаболизма (BMR).
     *
     * @param bmr базовый уровень метаболизма (Basal Metabolic Rate)
     * @return скорректированная норма калорий с учетом цели
     */
    double multiply(double bmr);

    /**
     * Возвращает тип цели, который поддерживает данный множитель.
     *
     * @return цель пользователя (похудение/поддержание/набор массы)
     */
    GoalType supportedGoalType();
}