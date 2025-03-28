package vasilkov._1221Systems.meal.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import vasilkov._1221Systems.dish.domain.Dish;

@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "meal_dishes")
public class MealDish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "meal_id")
    @JsonIgnore
    private Meal meal;

    @ManyToOne
    @JoinColumn(name = "dish_id")
    private Dish dish;

    private int quantity;
}
