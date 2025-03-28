package vasilkov._1221Systems.user.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vasilkov._1221Systems.api.request.UserRequest;
import vasilkov._1221Systems.api.response.UserResponse;
import vasilkov._1221Systems.error.ResourceAlreadyExistsException;
import vasilkov._1221Systems.error.ResourceNotFoundException;
import vasilkov._1221Systems.user.UserService;
import vasilkov._1221Systems.user.calories.calculation.impl.HarrisBenedictCalorieCalculator;
import vasilkov._1221Systems.user.domain.User;
import vasilkov._1221Systems.user.mapper.UserDtoMapper;
import vasilkov._1221Systems.user.repository.UserRepository;

/**
 * Реализация сервиса для работы с пользователями.
 * Обеспечивает создание и получение пользователей, включая расчет дневной нормы калорий.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService<UserRequest, UserResponse> {

    private final HarrisBenedictCalorieCalculator harrisBenedictCalorieCalculator;
    private final UserRepository repository;
    private final UserDtoMapper mapper;

    /**
     * Создает нового пользователя.
     * <p>Выполняет:
     * <ul>
     *   <li>Проверку уникальности email</li>
     *   <li>Расчет дневной нормы калорий</li>
     *   <li>Сохранение пользователя</li>
     * </ul>
     *
     * @param userRequest DTO с данными нового пользователя
     * @return DTO созданного пользователя
     * @throws ResourceAlreadyExistsException если пользователь с таким email уже существует
     */
    @Transactional
    public UserResponse createUser(UserRequest userRequest) {
        User user = mapper.toEntity(userRequest);

        if (repository.existsByEmail(user.getEmail())) {
            throw new ResourceAlreadyExistsException("User with this email already exists");
        }

        harrisBenedictCalorieCalculator.calculateDailyCalories(user);

        return mapper.toUserResponse(repository.save(user));
    }

    /**
     * Получает пользователя по ID.
     *
     * @param id идентификатор пользователя
     * @return найденная сущность пользователя
     * @throws ResourceNotFoundException если пользователь не найден
     */
    public User getUserById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }
}
