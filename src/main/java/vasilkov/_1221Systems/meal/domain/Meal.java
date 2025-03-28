package vasilkov._1221Systems.meal.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import vasilkov._1221Systems.user.domain.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "meals")
public class Meal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "meal", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<MealDish> mealDishes = new ArrayList<>();

    public void addMealDishes(MealDish mealDish) {
        this.mealDishes.add(mealDish);
        mealDish.setMeal(this);
    }

    public void removeMealDishes(MealDish mealDish) {
        this.mealDishes.remove(mealDish);
        mealDish.setMeal(null);
    }

    private LocalDate date;
    private LocalTime time;

}
