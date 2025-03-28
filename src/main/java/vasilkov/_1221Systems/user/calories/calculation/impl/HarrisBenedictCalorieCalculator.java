package vasilkov._1221Systems.user.calories.calculation.impl;

import org.springframework.stereotype.Service;
import vasilkov._1221Systems.user.calories.calculation.CalorieCalculator;
import vasilkov._1221Systems.user.calories.calculation.lifestyle.LifestyleCoefficientCalculator;
import vasilkov._1221Systems.user.domain.User;
import vasilkov._1221Systems.user.domain.enums.Gender;

/**
 * Калькулятор рассчета каллорий по уравнению Харриса –Бенедикта.
 * (Это метод, используемый для определения скорости основного обмена веществ (BMR) человека.)
 */
@Service
public class HarrisBenedictCalorieCalculator implements CalorieCalculator {
    private final LifestyleCoefficientCalculator lifestyleCoefficientCalculator;

    public HarrisBenedictCalorieCalculator(LifestyleCoefficientCalculator lifestyleCoefficientCalculator) {
        this.lifestyleCoefficientCalculator = lifestyleCoefficientCalculator;
    }

    /**
     * Метод подсчета суточной нормы ккал.
     * Полученную цифру нужно умножить на коэффициент, соответствующий уровню физической нагрузки:
     * <p>
     * сидячий образ жизни: BMR × 1,2; 1
     * лёгкая активность (упражнения 1–3 раза в неделю): BMR × 1,375; 1
     * умеренная активность (упражнения 3–5 раз в неделю): BMR × 1,55; 1
     * высокая активность (упражнения 6–7 раз в неделю): BMR × 1,725; 1
     * очень высокая активность (упражнения каждый день или физическая работа): BMR × 1,9. 1
     * Результат подсчётов — это суточная норма потребления калорий для поддержания текущего веса.
     * <p>
     * Источник: <a href="https://rkob.ru/rus/klinika/novosti/obshchie-novosti/algoritm-rascheta-kalorij">...</a>
     *
     * @param user пользователь для которого расчитываем суточную норму
     */
    @Override
    public void calculateDailyCalories(User user) {
        final double bmr = calculateBMR(user);
        user.setDailyCalorieNorm(lifestyleCoefficientCalculator.multiplyByGoal(user.getGoal(), bmr));
    }

    /**
     * Метод для расчета скорости основного обмена веществ (BMR) человека.
     * <p>
     * Уравнения Харриса – Бенедикта, пересмотренные Миффлином и Сент-Джором в 1990 году.
     * Мужчины	BMR = (10 × вес в кг) + (6,25 × рост в см) – (5 × возраст в годах) + 5
     * Женщины	BMR = (10 × вес в кг) + (6,25 × рост в см) - (5 × возраст в годах) – 161
     * Источник: <a href="https://translated.turbopages.org/proxy_u/en-ru.ru.47e9e1f1-67e58db5-b09d0269-74722d776562/https/en.wikipedia.org/wiki/Harris%E2%80%93Benedict_equation">...</a>
     *
     * @param user пользователь для которого расчитываем BMR
     * @return BMR
     */
    private double calculateBMR(User user) {
        if (Gender.MALE.equals(user.getGender())) {
            return (10 * user.getWeight()) + (6.25 * user.getHeight()) - (5 * user.getAge()) + 5;
        } else {
            return (10 * user.getWeight()) + (6.25 * user.getHeight()) - (5 * user.getAge()) - 161;
        }
    }
}
