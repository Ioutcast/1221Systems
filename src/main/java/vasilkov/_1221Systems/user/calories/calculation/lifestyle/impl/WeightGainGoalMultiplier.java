package vasilkov._1221Systems.user.calories.calculation.lifestyle.impl;

import org.springframework.stereotype.Component;
import vasilkov._1221Systems.user.domain.enums.GoalType;
import vasilkov._1221Systems.user.calories.calculation.lifestyle.CalorieGoalMultiplier;

@Component
public class WeightGainGoalMultiplier implements CalorieGoalMultiplier {
    @Override
    public double multiply(double bmr) {
        return bmr * 1.9;
    }

    @Override
    public GoalType supportedGoalType() {
        return GoalType.WEIGHT_GAIN;
    }
}
