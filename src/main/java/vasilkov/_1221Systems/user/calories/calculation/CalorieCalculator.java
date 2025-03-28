package vasilkov._1221Systems.user.calories.calculation;

import vasilkov._1221Systems.user.domain.User;

/**
 * Интерфейс для расчета дневной нормы калорий пользователя.
 * Определяет контракт для различных алгоритмов расчета калорий.
 */
public interface CalorieCalculator {

    /**
     * Рассчитывает и устанавливает дневную норму калорий для пользователя.
     * <p>
     * Реализации должны учитывать параметры пользователя (вес, рост, возраст и т.д.)
     * и устанавливать результат через {@link User#setDailyCalorieNorm(double)}.
     * </p>
     *
     * @param user пользователь, для которого рассчитывается норма калорий
     * @throws IllegalArgumentException если пользователь равен null
     *                                  или содержит некорректные данные для расчета
     */
    void calculateDailyCalories(User user) throws IllegalArgumentException;
}
