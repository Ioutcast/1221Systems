package vasilkov._1221Systems.user.calories.calculation.lifestyle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vasilkov._1221Systems.user.domain.enums.GoalType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LifestyleCoefficientCalculatorTest {

    @Mock
    private CalorieGoalMultiplier weightLossMultiplier;

    @Mock
    private CalorieGoalMultiplier maintenanceMultiplier;

    @Mock
    private CalorieGoalMultiplier weightGainMultiplier;

    private LifestyleCoefficientCalculator calculator;

    @BeforeEach
    void setUp() {
        when(weightLossMultiplier.supportedGoalType()).thenReturn(GoalType.WEIGHT_LOSS);
        when(maintenanceMultiplier.supportedGoalType()).thenReturn(GoalType.MAINTENANCE);
        when(weightGainMultiplier.supportedGoalType()).thenReturn(GoalType.WEIGHT_GAIN);

        calculator = new LifestyleCoefficientCalculator(
                List.of(weightLossMultiplier, maintenanceMultiplier, weightGainMultiplier)
        );
    }

    @Test
    void multiplyByGoal_shouldApplyCorrectMultiplierForWeightLoss() {
        double bmr = 2000;
        when(weightLossMultiplier.multiply(bmr)).thenReturn(bmr * 0.85);

        double result = calculator.multiplyByGoal(GoalType.WEIGHT_LOSS, bmr);

        assertEquals(1700, result, 0.01);
        verify(weightLossMultiplier).multiply(bmr);
    }

    @Test
    void multiplyByGoal_shouldApplyCorrectMultiplierForMaintenance() {
        double bmr = 2000;
        when(maintenanceMultiplier.multiply(bmr)).thenReturn(bmr);

        double result = calculator.multiplyByGoal(GoalType.MAINTENANCE, bmr);

        assertEquals(2000, result, 0.01);
        verify(maintenanceMultiplier).multiply(bmr);
    }

    @Test
    void multiplyByGoal_shouldApplyCorrectMultiplierForWeightGain() {
        double bmr = 2000;
        when(weightGainMultiplier.multiply(bmr)).thenReturn(bmr * 1.15);

        double result = calculator.multiplyByGoal(GoalType.WEIGHT_GAIN, bmr);

        assertEquals(2300, result, 0.01);
        verify(weightGainMultiplier).multiply(bmr);
    }

    @Test
    void multiplyByGoal_shouldThrowForUnsupportedGoalType() {
        double bmr = 2000;

        assertThrows(IllegalArgumentException.class,
                () -> calculator.multiplyByGoal(null, bmr));
    }
}