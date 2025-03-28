package vasilkov._1221Systems.meal;

import vasilkov._1221Systems.api.request.MealRequest;
import vasilkov._1221Systems.api.response.DailyReport;
import vasilkov._1221Systems.api.response.NutritionHistory;

import java.time.LocalDate;

public interface MealService {
    void addMeal(Long userId, MealRequest mealRequest);

    DailyReport getDailyReport(Long userId, LocalDate date);

    NutritionHistory getHistory(Long userId, LocalDate startDate, LocalDate endDate);

}
