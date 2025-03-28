package vasilkov._1221Systems.dish;

import vasilkov._1221Systems.dish.domain.Dish;

/**
 * Интерфейс для работы с блюдами.
 * Обеспечивает создание и получение информации о блюдах.
 */
public interface DishService {
    /**
     * Получает блюдо по идентификатору.
     *
     * @param id идентификатор блюда
     * @return найденное блюдо
     */
    Dish getDishById(Long id);

    /**
     * Создает новое блюдо.
     *
     * @param dish сущность блюда для создания
     */
    void createDish(Dish dish);
}
