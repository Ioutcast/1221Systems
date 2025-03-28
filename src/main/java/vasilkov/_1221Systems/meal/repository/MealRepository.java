package vasilkov._1221Systems.meal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vasilkov._1221Systems.meal.domain.Meal;
import vasilkov._1221Systems.user.domain.User;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {
    List<Meal> findByUserAndDate(User user, LocalDate date);

    List<Meal> findByUserAndDateBetween(User user, LocalDate startDate, LocalDate endDate);
}
