# 1.1.3 - 2025.03.04

### Patch:

- удалены перехватчики для логирования запросов/ответов;
- добавлены аспекты с счетчиками для подсчета заявок со статусом APPROVED и CREDIT_ISSUED. [Статья](https://habr.com/ru/companies/otus/articles/650871/), [Статья](https://docs.micrometer.io/micrometer/reference/concepts/counters.html);
- добавлен ErrorDecoder для feign client.


# 1.1.2 - 2025.03.03

### Patch: 

- поменял название параметра в feign client

# 1.1.1 - 2025.03.03

### Patch:

- поменял свойство для клиента

# 1.1.0 - 2025.03.03

### Features:

- добавление возможности обнаружения сервисов при помощи discovery. [Статья](https://habr.com/ru/companies/otus/articles/539348/)

# 1.0.0 - 2025.03.02

### Patch:

- добавлена генерация моделей с помощью плагина open-api-maven-plugin;

- удалена директория jacoco из корня проекта;

- удалены аспекты;

- добавлены фильтры для логгирования request/response.