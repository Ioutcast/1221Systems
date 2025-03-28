# Calories Tracker API
##
ВАЖНО! К запросу для создания пользователя добавился пол!(Различается с требованиями) Т.к пол формула учитывает. см vasilkov._1221Systems.user.calories.calculation.impl.HarrisBenedictCalorieCalculator#calculateDailyCalories сервис рассчета калорий!
##
## Описание проекта

REST API сервис для отслеживания дневной нормы калорий пользователя и учета съеденных блюд. Сервис предоставляет:

- Управление пользователями
- Учет блюд и их пищевой ценности
- Отслеживание приемов пищи
- Формирование отчетов о питании

## Технологии

- Java 17
- Spring Boot 3.4.4
- Spring Data JPA
- PostgreSQL
- Lombok
- MapStruct
- Swagger/OpenAPI 3.0
- Flyway

## Требования

- JDK 17+
- Maven 3.8+
- PostgreSQL 14+
- Docker

## Установка и запуск

```bash
# Клонировать репозиторий
git clone https://github.com/Ioutcast/1221Systems.git
cd 1221Systems
Вариант 1: Сборка и запуск напрямую
mvn clean install
mvn spring-boot:run
Вариант 2: Запуск через Docker
docker-compose up -d
```

## API Endpoints
Пользователи
POST /api/users - Создать пользователя

Блюда
POST /api/dishes - Добавить новое блюдо

Приемы пищи
POST /api/meals/{userId} - Добавить прием пищи

GET /api/meals/daily-report/{userId} - Получить дневной отчет

GET /api/meals/history/{userId} - Получить историю питания

## Документация API
После запуска приложения документация доступна по адресу:
Swagger UI: http://localhost:10101/api/v1/swagger-ui/index.html#
