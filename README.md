![Bot](https://github.com/sanyarnd/java-course-2023-backend-template/actions/workflows/bot.yml/badge.svg)
![Scrapper](https://github.com/sanyarnd/java-course-2023-backend-template/actions/workflows/scrapper.yml/badge.svg)

# Link Tracker

ФИО: Стрижов Александр Иванович

Приложение для отслеживания обновлений контента по ссылкам.
При появлении новых событий отправляется уведомление в Telegram.

Проект написан на `Java 21` с использованием `Spring Boot 3`.

Проект состоит из 2-х приложений:
* Bot
* Scrapper

Для работы требуется БД `PostgreSQL`. Присутствует опциональная зависимость на `Kafka`.
## Дизайн приложения
![image](https://github.com/bifidok/TinkoffBackend/assets/16528008/03e94ced-e450-481a-83dd-46a55910c37b)


## Технологический стэк
* Java 21
* Spring Boot 3
* JPA, JdbcTemplate, JOOQ (кофнигурируемый вобор технологии для работы с бд)
* Liquibase
* PostgreSQL
* Kafka
* [Pengrad Telegram Bot API](https://github.com/pengrad/java-telegram-bot-api)
* Docker
* JUnit 5, Testcontainers, Mockito, Wiremock
* Prometheus, Grafana

## Swagger документация к API
[Bot API](https://github.com/bifidok/TinkoffBackend/blob/master/bot/src/main/resources/bot-api.json)  
[Scrapper API](https://github.com/bifidok/TinkoffBackend/blob/master/scrapper/src/main/resources/scrapper-api.json)
