package vasilkov._1221Systems.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vasilkov._1221Systems.dish.domain.Dish;
import vasilkov._1221Systems.dish.impl.DishServiceImpl;


@RestController
@RequestMapping("/dishes")
@Validated
public class DishController {

    private final DishServiceImpl dishServiceImpl;

    public DishController(DishServiceImpl dishServiceImpl) {
        this.dishServiceImpl = dishServiceImpl;
    }

    /**
     * Создает новое блюдо в системе.
     * <p>
     * Принимает данные блюда в формате JSON и сохраняет их в базу данных.
     * В случае успеха возвращает статус 201 Created.
     * </p>
     *
     * @apiNote Пример запроса:
     * <pre>
     * {
     *   "id": 1,
     *   "name": "Салат Цезарь",
     *   "caloriesPerServing": 350.0,
     *   "proteins": 15.0,
     *   "fats": 25.0,
     *   "carbohydrates": 20.0
     * }
     * </pre>
     */
    @PostMapping
    public ResponseEntity<?> createDish(@RequestBody Dish dish) {
        dishServiceImpl.createDish(dish);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}