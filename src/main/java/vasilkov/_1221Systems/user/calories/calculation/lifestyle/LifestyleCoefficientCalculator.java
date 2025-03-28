package vasilkov._1221Systems.user.calories.calculation.lifestyle;

import org.springframework.stereotype.Component;
import vasilkov._1221Systems.user.domain.enums.GoalType;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

/**
 * Калькулятор коэффициентов образа жизни для расчета калорий.
 * Определяет множители калорий в зависимости от цели пользователя.
 */
@Component
public class LifestyleCoefficientCalculator {

    private final Map<GoalType, CalorieGoalMultiplier> calorieCalculationMap;

    /**
     * Конструктор с внедрением всех доступных стратегий расчета.
     *
     * @param calorieGoalMultiplierGenerator список реализаций CalorieGoalMultiplier
     */
    public LifestyleCoefficientCalculator(List<CalorieGoalMultiplier> calorieGoalMultiplierGenerator) {
        this.calorieCalculationMap = calorieGoalMultiplierGenerator.stream()
                .collect(toMap(CalorieGoalMultiplier::supportedGoalType, Function.identity()));
    }

    /**
     * Применяет коэффициент цели к базовому уровню калорий.
     *
     * @param goal         цель пользователя (похудение/поддержание/набор массы)
     * @param baseCalories базовый уровень калорий (BMR)
     * @return скорректированная норма калорий
     * @throws IllegalArgumentException если цель не поддерживается
     */
    public double multiplyByGoal(GoalType goal, double baseCalories) {
        CalorieGoalMultiplier multiplier = calorieCalculationMap.get(goal);
        if (multiplier == null) {
            throw new IllegalArgumentException("Unsupported goal type: " + goal);
        }
        return multiplier.multiply(baseCalories);
    }
}
