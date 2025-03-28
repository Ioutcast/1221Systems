package vasilkov._1221Systems.meal.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vasilkov._1221Systems.api.request.MealRequest;
import vasilkov._1221Systems.api.response.DailyReport;
import vasilkov._1221Systems.api.response.NutritionHistory;
import vasilkov._1221Systems.api.response.NutritionSummary;
import vasilkov._1221Systems.dish.domain.Dish;
import vasilkov._1221Systems.dish.impl.DishServiceImpl;
import vasilkov._1221Systems.meal.domain.Meal;
import vasilkov._1221Systems.meal.domain.MealDish;
import vasilkov._1221Systems.meal.repository.MealRepository;
import vasilkov._1221Systems.user.domain.User;
import vasilkov._1221Systems.user.impl.UserServiceImpl;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MealServiceImplTest {

    @Mock
    private MealRepository mealRepository;

    @Mock
    private UserServiceImpl userService;

    @Mock
    private DishServiceImpl dishService;

    @InjectMocks
    private MealServiceImpl mealService;

    @Test
    void addMeal_shouldSaveMealWithDishes() {
        Long userId = 1L;
        MealRequest request = new MealRequest(LocalDate.now(), List.of(1L, 2L, 2L));
        User user = new User();
        Dish dish1 = new Dish(1L, "Dish1", 300, 20, 10, 40);
        Dish dish2 = new Dish(2L, "Dish2", 500, 30, 20, 50);

        when(userService.getUserById(userId)).thenReturn(user);
        when(dishService.getDishById(1L)).thenReturn(dish1);
        when(dishService.getDishById(2L)).thenReturn(dish2);
        when(mealRepository.save(ArgumentMatchers.<Meal>any())).thenAnswer(i -> {
            Meal savedMeal = i.getArgument(0);
            assertNotNull(savedMeal);
            assertEquals(user, savedMeal.getUser());
            assertEquals(2, savedMeal.getMealDishes().size());
            return savedMeal;
        });

        mealService.addMeal(userId, request);

        verify(mealRepository).save(ArgumentMatchers.<Meal>any());
        verify(dishService).getDishById(1L);
    }

    @Test
    void getDailyReport_shouldReturnCorrectReport() {
        Long userId = 1L;
        LocalDate date = LocalDate.now();
        User user = new User();
        user.setDailyCalorieNorm(2000);

        Meal meal = new Meal();
        MealDish mealDish1 = new MealDish(1L, meal, new Dish(1L, "Dish1", 300, 20, 10, 40), 2);
        MealDish mealDish2 = new MealDish(2L, meal, new Dish(2L, "Dish2", 500, 30, 20, 50), 1);
        meal.setMealDishes(List.of(mealDish1, mealDish2));

        when(userService.getUserById(userId)).thenReturn(user);
        when(mealRepository.findByUserAndDate(user, date)).thenReturn(List.of(meal));

        DailyReport report = mealService.getDailyReport(userId, date);

        assertEquals(1100, report.summary().totalCalories()); // 300*2 + 500 = 1100
        assertEquals(70, report.summary().totalProteins());    // 20*2 + 30 = 70
        assertEquals(40, report.summary().totalFats());        // 10*2 + 20 = 40
        assertEquals(130, report.summary().totalCarbohydrates());// 40*2 + 50 = 130
        assertTrue(report.withinCalorieLimit());
        assertFalse(report.dailyCalorieNorm() > 2000); // 1100 > 2000? false
    }

    @Test
    void getHistory_shouldReturnGroupedSummaries() {
        Long userId = 1L;
        LocalDate startDate = LocalDate.now().minusDays(7);
        LocalDate endDate = LocalDate.now();
        User user = new User();

        Meal meal1 = createTestMeal(LocalDate.now().minusDays(1), 500, 30, 20, 50);
        Meal meal2 = createTestMeal(LocalDate.now(), 300, 20, 10, 40);
        Meal meal3 = createTestMeal(LocalDate.now(), 400, 25, 15, 45);

        when(userService.getUserById(userId)).thenReturn(user);
        when(mealRepository.findByUserAndDateBetween(user, startDate, endDate))
                .thenReturn(List.of(meal1, meal2, meal3));

        NutritionHistory history = mealService.getHistory(userId, startDate, endDate);

        assertEquals(2, history.dailySummaries().size()); // Должно быть 2 дня
        assertEquals(700, history.dailySummaries().get(LocalDate.now()).totalCalories()); // 300 + 400
        assertEquals(500, history.dailySummaries().get(LocalDate.now().minusDays(1)).totalCalories());
    }

    @Test
    void getHistory_shouldThrowWhenDatesInvalid() {
        // Arrange
        Long userId = 1L;
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().minusDays(1);

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> mealService.getHistory(userId, startDate, endDate));
    }

    @Test
    void computeNutritionSummary_shouldCalculateCorrectValues() {
        // Arrange
        Meal meal1 = createTestMeal(LocalDate.now(), 300, 20, 10, 40);
        Meal meal2 = createTestMeal(LocalDate.now(), 500, 30, 20, 50);

        // Act
        NutritionSummary summary = mealService.computeNutritionSummary(List.of(meal1, meal2));

        // Assert
        assertEquals(800, summary.totalCalories());
        assertEquals(50, summary.totalProteins());
        assertEquals(30, summary.totalFats());
        assertEquals(90, summary.totalCarbohydrates());
    }

    private Meal createTestMeal(LocalDate date, double calories, double proteins, double fats, double carbs) {
        Meal meal = new Meal();
        meal.setDate(date);
        Dish dish = new Dish(1L, "TestDish", calories, proteins, fats, carbs);
        meal.setMealDishes(List.of(new MealDish(1L, meal, dish, 1)));
        return meal;
    }
}