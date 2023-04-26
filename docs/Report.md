# Отчет по итогам тестирования

## Описание:
В отчете представляются итоги по тестировнию веб-сервиса "Путешествие дня" покупки тура через оплату по дебетовой карте
(**Payment Gate**) или через оформление кредита по данным банковской карты (**Credit Gate**).

Были проведены: UI-тесты и API-тесты, тесты двух БД: MySQL и PostgreSQL (тип БД не влиял на итог тестирования)


## Количество тест-кейсов
![allure report количество тест-кейсов](https://user-images.githubusercontent.com/117623022/234628856-c478b9b1-9482-40af-a622-4295b966e0ab.png)
![allure 2.png](screen%2Fallure%202.png)
![allure 3.png](screen%2Fallure%203.png)
![allure 4.png](screen%2Fallure%204.png)
![allure 5.png](screen%2Fallure%205.png)
В результате тестирование было проведено **58** тестовых сценариев, среди которых:
* **20** - успешных (34.48%)
* **38** - неуспешных (65,52%)

## Общие рекомендации
1. Подготовить четкую и полную техническую документацию для понимания того, как должен работать веб-сервис и что должно
считаться дефектом
1. Добавить атрибут `test-id` для ключевых элементов веб-сервиса
1. Добавить чекбокс согласия на обработку персональных данных [Баг](https://github.com/Mezis4/AutoTestCoursework/issues/1)
1. Пофиксить баг с отображением ошибки ввода под полем "Владелец" при отправки формы с пустым полем "CVC/CVV"
[Баг](https://github.com/Mezis4/AutoTestCoursework/issues/9)
1. Пофиксить баги с валидацией полем "Владелец" неккоректных значений (символы, цифры и т.д.) [Баг](https://github.com/Mezis4/AutoTestCoursework/issues/8),
[Баг 2](https://github.com/Mezis4/AutoTestCoursework/issues/7), [Баг 3](https://github.com/Mezis4/AutoTestCoursework/issues/6),
[Баг 4](https://github.com/Mezis4/AutoTestCoursework/issues/6), [Баг 4](https://github.com/Mezis4/AutoTestCoursework/issues/5)
1. Пофиксить баг с валидацией полем "Месяц" значения "00" [Баг](https://github.com/Mezis4/AutoTestCoursework/issues/4)
1. Изменить содержание ошибок-предупреждений под полями при отправки формы с пустыми полями "Месяц", "Номер карты", "Год",
"CVC/CVV" [Баг](https://github.com/Mezis4/AutoTestCoursework/issues/3)
1. Добавить функцию автозаполнения значений в нижнем регистре значениями в врехнем регистре в поле "Владелец"
[Баг](https://github.com/Mezis4/AutoTestCoursework/issues/2)
1. Исправить баг с одобрением платежа для DECLINED-карт [Баг](https://github.com/Mezis4/AutoTestCoursework/issues/1)
1. Исправить баг с отсутствием записей в БД [Баг](https://github.com/Mezis4/AutoTestCoursework/issues/10)
