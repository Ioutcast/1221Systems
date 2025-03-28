package vasilkov._1221Systems.dish.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vasilkov._1221Systems.dish.DishService;
import vasilkov._1221Systems.dish.domain.Dish;
import vasilkov._1221Systems.dish.repository.DishRepository;
import vasilkov._1221Systems.error.ResourceAlreadyExistsException;
import vasilkov._1221Systems.error.ResourceNotFoundException;

@Service
@RequiredArgsConstructor
public class DishServiceImpl implements DishService {

    private final DishRepository dishRepository;

    public void createDish(Dish dish) throws ResourceAlreadyExistsException {
        if (dishRepository.existsById(dish.getId())) {
            throw new ResourceAlreadyExistsException("Dish exists with id: " + dish.getId());
        }
        dishRepository.save(dish);
    }

    public Dish getDishById(Long id) {
        return dishRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dish not found with id: " + id));
    }
}