package vasilkov._1221Systems.dish.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vasilkov._1221Systems.dish.domain.Dish;

@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {
}
