package vasilkov._1221Systems.dish.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "dishes")
public class Dish {
    @Id
    private Long id;

    @NotBlank
    private String name;

    @Min(1)
    private double caloriesPerServing;

    @Min(0)
    private double proteins;

    @Min(0)
    private double fats;

    @Min(0)
    private double carbohydrates;
}
