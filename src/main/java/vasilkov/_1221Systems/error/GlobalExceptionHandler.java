package vasilkov._1221Systems.error;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import vasilkov._1221Systems.api.response.ErrorResponse;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Глобальный обработчик ошибок.
 * Обрабатывает исключения, возникающие в контроллерах, и возвращает соответствующие ответы.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());

        ErrorResponse response = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Ошибка валидации",
                Instant.now(),
                errors);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex) {
        ErrorResponse response = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                Instant.now(),
                Collections.singletonList(ex.getLocalizedMessage())
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleResourceAlreadyExists(ResourceAlreadyExistsException ex) {
        ErrorResponse response = new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                ex.getMessage(),
                Instant.now(),
                Collections.singletonList(ex.getLocalizedMessage())
        );
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex) {
        ErrorResponse response = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Внутренняя ошибка сервера",
                Instant.now(),
                List.of(ex.getLocalizedMessage())
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        String errorMessage = "Ошибка валидации данных";
        if (ex.getCause().getMessage().contains("violates check constraint")) {
            errorMessage = extractConstraintMessage(ex.getCause().getMessage());
        }
        ErrorResponse response = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                errorMessage,
                Instant.now()
        );
        return ResponseEntity.badRequest().body(response);
    }

    private String extractConstraintMessage(String sqlMessage) {
        if (sqlMessage.contains("users_age_check")) {
            return "Возраст должен быть от 1 до 120 лет";
        } else if (sqlMessage.contains("users_weight_check")) {
            return "Вес должен быть от 30 до 300 кг";
        } else if (sqlMessage.contains("users_height_check")) {
            return "Рост должен быть от 100 до 250 см";
        }
        return "Некорректные данные";
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleJsonParseError(HttpMessageNotReadableException ex) {
        String errorMessage = "Некорректный JSON формат";

        if (ex.getMessage() != null && ex.getMessage().contains("Unexpected character")) {
            errorMessage = "Обнаружен недопустимый символ в JSON. Проверьте синтаксис.";
        }

        ErrorResponse response = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                errorMessage,
                Instant.now()
        );
        return ResponseEntity.badRequest().body(response);
    }
}