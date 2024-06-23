# MVP Level 3 реализация микросервиса Заявка
## Реализация
Валидация поделена на две группы: Default и Prescoring, что в далнейшем позволяет разграничить
выброс исключения. Везде где это возможно применялся принцип fail-fast: выброс одних исключений
заменялся выбросами других, более конкретных, пример [тут](src/main/java/com/munsun/statement/aspects/DealClientAspects.java).

Результаты покрытия [здесь](jacoco/index.html).

Документация доступна после сборки и запуска сервиса по адресу */api-docs-ui*.
Запросы [Postman](https://www.postman.com/navigation-candidate-51855014/workspace/system-projects/collection/27612511-0ad9e853-7906-4e4c-8504-e076b5361815).

# Запуск
Перейти в корень и собрать проект(заранее включить Docker):
```
mvn clean package
```
или без прогона тестов
```
mvn clean package -DskipTests=true
```
Запуск:
```
docker compose up
```