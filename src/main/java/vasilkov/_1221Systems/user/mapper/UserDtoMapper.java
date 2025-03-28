package vasilkov._1221Systems.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import vasilkov._1221Systems.api.request.UserRequest;
import vasilkov._1221Systems.api.response.UserResponse;
import vasilkov._1221Systems.user.domain.User;

/**
 * Маппер для преобразования между сущностью User и DTO.
 * Использует MapStruct для автоматического маппинга.
 */
@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface UserDtoMapper {

    /**
     * Конвертирует UserRequest в сущность User.
     *
     * @param userRequest DTO запроса пользователя
     * @return сущность User
     */
    User toEntity(UserRequest userRequest);

    /**
     * Конвертирует сущность User в UserRequest.
     *
     * @param user сущность User
     * @return DTO запроса пользователя
     */
    UserRequest toUserRequest(User user);

    /**
     * Конвертирует UserResponse в сущность User.
     *
     * @param userResponse DTO ответа пользователя
     * @return сущность User
     */
    User toEntity(UserResponse userResponse);

    /**
     * Конвертирует сущность User в UserResponse.
     *
     * @param user сущность User
     * @return DTO ответа пользователя
     */
    UserResponse toUserResponse(User user);
}