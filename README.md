# В процессе написания (заготовка)
## Документы

## Подключение к БД и запуск SUT
#### MySQL
1. Открыть клон проекта в Intellij IDEA
1. Запустить контейнер MySQL через команду `docker-compose up -d`
1. Запустить SUT через команду `java -jar .\artifacts\aqa-shop.jar --spring.datasource.url=jdbc:mysql://localhost:3306/app`
1. Запустить автотесты через команду `.\gradlew clean test -DdbUrl=jdbc:mysql://localhost:3306/app`
