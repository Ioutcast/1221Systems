package vasilkov._1221Systems.api.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vasilkov._1221Systems.api.request.MealRequest;
import vasilkov._1221Systems.api.response.DailyReport;
import vasilkov._1221Systems.api.response.NutritionHistory;
import vasilkov._1221Systems.meal.impl.MealServiceImpl;

import java.time.LocalDate;

/**
 * Контроллер для работы с приемами пищи.
 * Обеспечивает добавление приемов пищи и получение отчетов.
 */
@RestController
@RequestMapping("/meals")
@RequiredArgsConstructor
@Validated
public class MealController {
    private final MealServiceImpl mealServiceImpl;

    /**
     * Добавляет новый прием пищи для пользователя.
     *
     * @example Пример запроса:
     * POST /api/v1/meals/1
     * {
     * "date": "2023-05-15",
     * "dishIds": [1, 2, 3]
     * }
     */
    @PostMapping("/{userId}")
    public ResponseEntity<?> addMeal(
            @NotNull @PathVariable Long userId,
            @Valid @RequestBody MealRequest mealRequest) {
        mealServiceImpl.addMeal(userId, mealRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Получает дневной отчет о питании пользователя.
     *
     * @example Пример запроса:
     * GET /api/v1/meals/daily-report/1?date=2023-05-15
     */
    @GetMapping("/daily-report/{userId}")
    public ResponseEntity<DailyReport> getDailyReport(
            @PathVariable Long userId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        if (date == null) {
            date = LocalDate.now();
        }
        DailyReport report = mealServiceImpl.getDailyReport(userId, date);
        return ResponseEntity.ok(report);
    }

    /**
     * Получает историю питания за указанный период.
     *
     * @example Пример запроса:
     * GET /api/v1/meals/history/1?startDate=2023-05-01&endDate=2023-05-15
     */
    @GetMapping("/history/{userId}")
    public ResponseEntity<NutritionHistory> getHistory(
            @PathVariable Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        NutritionHistory history = mealServiceImpl.getHistory(userId, startDate, endDate);
        return ResponseEntity.ok(history);
    }
}