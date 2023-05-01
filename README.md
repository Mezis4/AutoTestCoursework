[![Build status](https://ci.appveyor.com/api/projects/status/2l7spc765r6yncbi?svg=true)](https://ci.appveyor.com/project/Mezis4/autotestcoursework)
# Курсовой проект по модулю «Автоматизация тестирования» (в процессе написания)
## Документы
1. [План автоматизации](https://github.com/Mezis4/AutoTestCoursework/blob/80fc47f09a9b8b9b6b99da8c3f52612f84083ffe/docs/Plan.md)
1. [Отчет по итогам тестирования](https://github.com/Mezis4/AutoTestCoursework/blob/main/docs/Report.md)
1. [Отчёт по итогам автоматизации](https://github.com/Mezis4/AutoTestCoursework/blob/main/docs/Summary.md)

## Подключение к БД и запуск SUT

#### Через MySQL
1. Открыть клон проекта в Intellij IDEA
1. Запустить Docker Desktop
1. Запустить контейнер через команду `docker-compose up -d`
1. Запустить SUT через команду `java -jar .\artifacts\aqa-shop.jar --spring.datasource.url=jdbc:mysql://localhost:3306/app`
1. Запустить автотесты через команду `.\gradlew clean test -DdbUrl=jdbc:mysql://localhost:3306/app`
1. Сгенерировать отчет Allure через команду `.\gradlew allureServe` и ознакомиться с ним в браузере (открывается автоматически)
1. По завршению работы остановить приложение сочетанием клавиш CTRL+C и контейнер через команду `docker-compose down` в терминале

#### Через PostgreSQL
*По умолчанию в build.gradle активна БД MySQL. Для переключения на PostgreSQL нужно закомментировать [строку с MySQL](https://github.com/Mezis4/AutoTestCoursework/blob/80fc47f09a9b8b9b6b99da8c3f52612f84083ffe/build.gradle#L45) и активировать [строку с PostgreSQL](https://github.com/Mezis4/AutoTestCoursework/blob/80fc47f09a9b8b9b6b99da8c3f52612f84083ffe/build.gradle#L46)
1. Открыть клон проекта в Intellij IDEA
1. Запустить Docker Desktop
1. Запустить контейнер через команду `docker-compose up -d`
1. Запустить SUT через команду `java -jar .\artifacts\aqa-shop.jar --spring.datasource.url=jdbc:postgresql://localhost:5432/app`
1. Запустить автотесты через команду `.\gradlew clean test -DdbUrl=jdbc:postgresql://localhost:5432/app`
1. Сгенерировать отчет Allure через команду `.\gradlew allureServe` и ознакомиться с ним в браузере (открывается автоматически)
1. По завршению работы остановить приложение сочетанием клавиш CTRL+C и контейнер через команду `docker-compose down` в терминале
