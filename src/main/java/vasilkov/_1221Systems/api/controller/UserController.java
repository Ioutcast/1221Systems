package vasilkov._1221Systems.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vasilkov._1221Systems.api.request.UserRequest;
import vasilkov._1221Systems.api.response.UserResponse;
import vasilkov._1221Systems.user.impl.UserServiceImpl;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserServiceImpl userService;

    /**
     * Создает нового пользователя в системе.
     * <p>
     * Принимает данные пользователя в формате JSON, валидирует их и сохраняет в базу данных.
     * В случае успеха возвращает данные созданного пользователя со статусом 201 Created.
     * </p>
     *
     * @apiNote Пример запроса:
     * <pre>
     * {
     *   "id": 1,
     *   "name": "Иван Иванов",
     *   "email": "ivan@example.com",
     *   "age": 30,
     *   "weight": 75.5,
     *   "height": 180,
     *   "goal": "WEIGHT_LOSS"
     * }
     * </pre>
     */
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest userRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userRequest));
    }
}
