package vasilkov._1221Systems.user.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vasilkov._1221Systems.api.request.UserRequest;
import vasilkov._1221Systems.api.response.UserResponse;
import vasilkov._1221Systems.error.ResourceAlreadyExistsException;
import vasilkov._1221Systems.user.calories.calculation.impl.HarrisBenedictCalorieCalculator;
import vasilkov._1221Systems.user.domain.User;
import vasilkov._1221Systems.user.domain.enums.Gender;
import vasilkov._1221Systems.user.domain.enums.GoalType;
import vasilkov._1221Systems.user.mapper.UserDtoMapper;
import vasilkov._1221Systems.user.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private HarrisBenedictCalorieCalculator calorieCalculator;

    @Mock
    private UserDtoMapper mapper;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void createUser_shouldSaveUserWithCalculatedCalories() {
        UserRequest request = new UserRequest(1L, "test", Gender.MALE, "test@mail.com",
                30, 70, 180, GoalType.WEIGHT_LOSS);
        User user = new User();
        UserResponse responseDto = new UserResponse("test", Gender.MALE.toString(), "test@mail.com", 30, 70, 180, GoalType.WEIGHT_LOSS, 2000);

        when(mapper.toEntity((UserRequest) any())).thenReturn(user);
        when(userRepository.existsByEmail(any())).thenReturn(false);
        when(userRepository.save(any())).thenReturn(user);
        when(mapper.toUserResponse(any())).thenReturn(responseDto);

        UserResponse response = userService.createUser(request);

        assertNotNull(response);
        verify(calorieCalculator).calculateDailyCalories(user);
        verify(userRepository).save(user);
        verify(mapper).toUserResponse(user);
    }

    @Test
    void createUser_shouldThrowWhenEmailExists() {
        UserRequest request = new UserRequest(1L, "test", Gender.MALE,
                "exists@mail.com", 30, 70, 180, GoalType.WEIGHT_LOSS);
        User user = new User();

        when(mapper.toEntity((UserRequest) any())).thenReturn(user);
        when(userRepository.existsByEmail(any())).thenReturn(true);

        assertThrows(ResourceAlreadyExistsException.class,
                () -> userService.createUser(request));

        verify(userRepository, never()).save(any());
    }
}