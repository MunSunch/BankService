# MVP Level 2 реализация микросервиса Сделка
## Реализация
Покрытие тестами составляет [68%](jacoco/index.html).

Документация доступна после сборки и запуска сервиса по адресу */api-docs-ui*.
Запросы [Postman](https://www.postman.com/navigation-candidate-51855014/workspace/system-projects/collection/27612511-0ad9e853-7906-4e4c-8504-e076b5361815)

Использованы доп. технологии: 
инициализация схемы выполнена при помощи Liquibase, маппинг объектов - MapStruct,
интеграционные тесты - TestContainers Postgres и H2, мокирование сервиса "Калькулятор" - WireMock. Также
использован spring boot 3.x для совместимости с OpenFeign(выпало предупреждение при сборке проекта в версии 2.x).
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