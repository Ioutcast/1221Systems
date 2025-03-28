package vasilkov._1221Systems.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

/**
 * Стандартный формат ответа при возникновении ошибок.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    /**
     * HTTP-статус код ошибки
     */
    private int status;

    /**
     * Основное сообщение об ошибке
     */
    private String message;

    /**
     * Временная метка возникновения ошибки
     */
    private Instant timestamp;

    /**
     * Детализированный список ошибок (может быть пустым)
     */
    private List<String> errors;

    /**
     * Создает ответ об ошибке без детализированного списка.
     */
    public ErrorResponse(int status, String message, Instant timestamp) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
        this.errors = List.of();
    }
}
