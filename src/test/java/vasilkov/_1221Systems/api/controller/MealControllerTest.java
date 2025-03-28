package vasilkov._1221Systems.api.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import vasilkov._1221Systems.api.request.MealRequest;
import vasilkov._1221Systems.api.response.DailyReport;
import vasilkov._1221Systems.api.response.NutritionHistory;
import vasilkov._1221Systems.api.response.NutritionSummary;
import vasilkov._1221Systems.meal.impl.MealServiceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MealControllerTest {

    @Mock
    private MealServiceImpl mealServiceImpl;

    @InjectMocks
    private MealController mealController;

    @Test
    void addMeal_shouldReturnCreatedStatus() {
        Long userId = 1L;
        MealRequest request = new MealRequest(LocalDate.now(), List.of(1L, 2L));
        doNothing().when(mealServiceImpl).addMeal(userId, request);

        ResponseEntity<?> response = mealController.addMeal(userId, request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(mealServiceImpl).addMeal(userId, request);
    }

    @Test
    void getDailyReport_shouldReturnReportWithCurrentDateWhenDateNotProvided() {
        Long userId = 1L;
        DailyReport expectedReport = new DailyReport(
                LocalDate.now(),
                List.of(),
                new NutritionSummary(0, 0, 0, 0),
                2000,
                true
        );

        when(mealServiceImpl.getDailyReport(userId, LocalDate.now()))
                .thenReturn(expectedReport);

        ResponseEntity<DailyReport> response = mealController.getDailyReport(userId, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedReport, response.getBody());
        verify(mealServiceImpl).getDailyReport(userId, LocalDate.now());
    }

    @Test
    void getDailyReport_shouldReturnReportWithSpecifiedDate() {
        Long userId = 1L;
        LocalDate date = LocalDate.of(2023, 5, 15);
        DailyReport expectedReport = new DailyReport(
                date,
                List.of(),
                new NutritionSummary(1500, 100, 50, 200),
                2000,
                true
        );

        when(mealServiceImpl.getDailyReport(userId, date))
                .thenReturn(expectedReport);

        ResponseEntity<DailyReport> response = mealController.getDailyReport(userId, date);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedReport, response.getBody());
        verify(mealServiceImpl).getDailyReport(userId, date);
    }

    @Test
    void getHistory_shouldReturnNutritionHistory() {
        Long userId = 1L;
        LocalDate startDate = LocalDate.of(2023, 5, 1);
        LocalDate endDate = LocalDate.of(2023, 5, 15);

        NutritionHistory expectedHistory = new NutritionHistory(
                userId,
                startDate,
                endDate,
                Map.of()
        );

        when(mealServiceImpl.getHistory(userId, startDate, endDate))
                .thenReturn(expectedHistory);

   
        ResponseEntity<NutritionHistory> response =
                mealController.getHistory(userId, startDate, endDate);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedHistory, response.getBody());
        verify(mealServiceImpl).getHistory(userId, startDate, endDate);
    }
}