# YandexMoneyAutotests

# Описание

Проект содержит смоук автотесты на проверку функционала:

- Пользователь может залогиниться
- Пользователь может разлогиниться

Так же в классе [LoginTest](src/test/java/com/github/money/yandex/LoginTest.java) описаны, но не реализованы тесты для полноценной проверки функционала.

# Запуск

Запустить автотесты можно командой:
```
mvn clean test -Dyandex.money.username=test@yandex.ru -Dyandex.money.password=test
```

# Отчет

Отчет генерируется и открывается командой:
```
mvn allure:serve
```