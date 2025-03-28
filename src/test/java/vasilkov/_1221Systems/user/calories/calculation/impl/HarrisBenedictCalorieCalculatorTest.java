package vasilkov._1221Systems.user.calories.calculation.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vasilkov._1221Systems.user.calories.calculation.lifestyle.LifestyleCoefficientCalculator;
import vasilkov._1221Systems.user.domain.User;
import vasilkov._1221Systems.user.domain.enums.Gender;
import vasilkov._1221Systems.user.domain.enums.GoalType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HarrisBenedictCalorieCalculatorTest {

    @Mock
    private LifestyleCoefficientCalculator lifestyleCalculator;

    private HarrisBenedictCalorieCalculator calculator;

    private User maleUser;
    private User femaleUser;

    @BeforeEach
    void setUp() {
        calculator = new HarrisBenedictCalorieCalculator(lifestyleCalculator);

        maleUser = new User();
        maleUser.setGender(Gender.MALE);
        maleUser.setWeight(80);
        maleUser.setHeight(180);
        maleUser.setAge(30);
        maleUser.setGoal(GoalType.MAINTENANCE);

        femaleUser = new User();
        femaleUser.setGender(Gender.FEMALE);
        femaleUser.setWeight(65);
        femaleUser.setHeight(165);
        femaleUser.setAge(25);
        femaleUser.setGoal(GoalType.WEIGHT_LOSS);
    }

    @Test
    void calculateDailyCalories_shouldSetCorrectValueForMale() {
        double expectedBmr = (10 * 80) + (6.25 * 180) - (5 * 30) + 5;
        when(lifestyleCalculator.multiplyByGoal(GoalType.MAINTENANCE, expectedBmr))
                .thenReturn(expectedBmr * 1.2);

        calculator.calculateDailyCalories(maleUser);

        assertEquals(expectedBmr * 1.2, maleUser.getDailyCalorieNorm(), 0.01);
        verify(lifestyleCalculator).multiplyByGoal(GoalType.MAINTENANCE, expectedBmr);
    }

    @Test
    void calculateDailyCalories_shouldSetCorrectValueForFemale() {
        double expectedBmr = (10 * 65) + (6.25 * 165) - (5 * 25) - 161;
        when(lifestyleCalculator.multiplyByGoal(GoalType.WEIGHT_LOSS, expectedBmr))
                .thenReturn(expectedBmr * 0.85);

        calculator.calculateDailyCalories(femaleUser);

        assertEquals(expectedBmr * 0.85, femaleUser.getDailyCalorieNorm(), 0.01);
        verify(lifestyleCalculator).multiplyByGoal(GoalType.WEIGHT_LOSS, expectedBmr);
    }

    @Test
    void calculateBMR_shouldReturnCorrectValueForMale() {
        double result = calculator.calculateBMR(maleUser);

        double expected = (10 * 80) + (6.25 * 180) - (5 * 30) + 5;
        assertEquals(expected, result, 0.01);
    }

    @Test
    void calculateBMR_shouldReturnCorrectValueForFemale() {
        double result = calculator.calculateBMR(femaleUser);

        double expected = (10 * 65) + (6.25 * 165) - (5 * 25) - 161;
        assertEquals(expected, result, 0.01);
    }
}