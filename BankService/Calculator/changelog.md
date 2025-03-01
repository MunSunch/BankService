# 0.1.3 - 2025-03-01

### Features
- добавлены отдельные эндпоинты расчета с аннуитентыми и дифференциированными выплатами;
- добавлена возможность генерации моделей по документации OpenAPI с помощью [openapi-generator-maven-plugin](https://openapi-generator.tech/docs/plugins/)
- логирования запросов и ответов [статья](https://frandorado.github.io/spring/2018/11/15/log-request-response-with-body-spring.html)
- возможность анонимизации чувствительных данных в логах за счет перехватчика сериализатора, [статья](https://stackoverflow.com/questions/56070451/mask-json-fields-using-jackson)

### Improved
- покрытие тестами увеличено до 90%;
- обновлены тесты на валидации моделей;
- добавлены тесты на расчет с аннуитентыми и дифференциированными выплатами;
