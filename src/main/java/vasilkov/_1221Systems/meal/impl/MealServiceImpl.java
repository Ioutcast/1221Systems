package vasilkov._1221Systems.meal.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vasilkov._1221Systems.api.request.MealRequest;
import vasilkov._1221Systems.api.response.DailyReport;
import vasilkov._1221Systems.api.response.NutritionHistory;
import vasilkov._1221Systems.api.response.NutritionSummary;
import vasilkov._1221Systems.dish.impl.DishServiceImpl;
import vasilkov._1221Systems.dish.domain.Dish;
import vasilkov._1221Systems.meal.MealService;
import vasilkov._1221Systems.meal.domain.Meal;
import vasilkov._1221Systems.meal.domain.MealDish;
import vasilkov._1221Systems.meal.repository.MealRepository;
import vasilkov._1221Systems.user.domain.User;
import vasilkov._1221Systems.user.impl.UserServiceImpl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Сервис для работы с приемами пищи.
 * Обеспечивает добавление приемов пищи, формирование отчетов и истории питания.
 */
@Service
@RequiredArgsConstructor
public class MealServiceImpl implements MealService {

    private final MealRepository mealRepository;
    private final UserServiceImpl userService;
    private final DishServiceImpl dishServiceImpl;

    /**
     * Добавляет новый прием пищи для пользователя.
     * <p>
     * Сохраняет информацию о блюдах и их количестве в приеме пищи.
     * </p>
     *
     * @param userId      идентификатор пользователя
     * @param mealRequest запрос с данными о приеме пищи
     */
    @Transactional
    public void addMeal(Long userId, MealRequest mealRequest) {
        User user = userService.getUserById(userId);

        Meal meal = new Meal();
        meal.setUser(user);
        meal.setDate(mealRequest.date());
        meal.setTime(LocalTime.now());

        Map<Long, Long> dishCounts = mealRequest.dishIds().stream()
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                ));

        dishCounts.forEach((dishId, count) -> {
            Dish dish = dishServiceImpl.getDishById(dishId);
            MealDish mealDish = new MealDish();
            mealDish.setMeal(meal);
            mealDish.setDish(dish);
            mealDish.setQuantity(count.intValue());
            meal.getMealDishes().add(mealDish);
        });

        mealRepository.save(meal);
    }

    /**
     * Формирует дневной отчет о питании пользователя.
     *
     * @param userId идентификатор пользователя
     * @param date   дата для формирования отчета
     * @return отчет с суммарной информацией о питании за день
     */
    public DailyReport getDailyReport(Long userId, LocalDate date) {
        User user = userService.getUserById(userId);
        List<Meal> meals = mealRepository.findByUserAndDate(user, date);

        NutritionSummary summary = computeNutritionSummary(meals);
        boolean withinCalorieLimit = summary.totalCalories() <= user.getDailyCalorieNorm();

        return new DailyReport(date, meals, summary, user.getDailyCalorieNorm(), withinCalorieLimit);
    }

    /**
     * Получает историю питания за указанный период.
     *
     * @param userId    идентификатор пользователя
     * @param startDate начальная дата периода
     * @param endDate   конечная дата периода
     * @return история питания с ежедневной статистикой
     */
    public NutritionHistory getHistory(Long userId, LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date must be before end date");
        }

        User user = userService.getUserById(userId);
        List<Meal> meals = mealRepository.findByUserAndDateBetween(user, startDate, endDate);

        Map<LocalDate, NutritionSummary> dailySummaries = meals.stream()
                .collect(Collectors.groupingBy(Meal::getDate,
                        Collectors.collectingAndThen(Collectors.toList(), this::computeNutritionSummary)));

        return new NutritionHistory(userId, startDate, endDate, dailySummaries);
    }

    /**
     * Вычисляет суммарную пищевую ценность для списка приемов пищи.
     *
     * @param meals список приемов пищи
     * @return объект с суммарными значениями калорий, белков, жиров и углеводов
     */
    NutritionSummary computeNutritionSummary(List<Meal> meals) {
        return meals.stream()
                .flatMap(meal -> meal.getMealDishes().stream())
                .collect(Collectors.collectingAndThen(Collectors.toList(), mealDishes ->
                        new NutritionSummary(
                                mealDishes.stream()
                                        .mapToDouble(md -> md.getDish().getCaloriesPerServing() * md.getQuantity())
                                        .sum(),
                                mealDishes.stream()
                                        .mapToDouble(md -> md.getDish().getProteins() * md.getQuantity())
                                        .sum(),
                                mealDishes.stream()
                                        .mapToDouble(md -> md.getDish().getFats() * md.getQuantity())
                                        .sum(),
                                mealDishes.stream()
                                        .mapToDouble(md -> md.getDish().getCarbohydrates() * md.getQuantity())
                                        .sum()
                        )));
    }
}