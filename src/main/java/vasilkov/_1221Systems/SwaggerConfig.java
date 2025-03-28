package vasilkov._1221Systems;

import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Конфигурация Swagger для проекта.
 * Настройка генерации документации API с помощью Springdoc OpenAPI.
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("Calorie Tracker API")
                        .version("1.0")
                        .description("API для отслеживания дневной нормы калорий"));
    }
}
