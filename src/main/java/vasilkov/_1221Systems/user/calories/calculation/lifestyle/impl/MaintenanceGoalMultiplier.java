package vasilkov._1221Systems.user.calories.calculation.lifestyle.impl;

import org.springframework.stereotype.Component;
import vasilkov._1221Systems.user.calories.calculation.lifestyle.CalorieGoalMultiplier;
import vasilkov._1221Systems.user.domain.enums.GoalType;

@Component
public class MaintenanceGoalMultiplier implements CalorieGoalMultiplier {
    @Override
    public double multiply(double bmr) {
        return bmr * 1.375;
    }

    @Override
    public GoalType supportedGoalType() {
        return GoalType.MAINTENANCE;
    }
}
