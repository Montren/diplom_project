####Выбор базы данных:
#####MySQL
Для тестирования с использованием базы данных MySQL: 
1. docker-compose.yml должен быть следующего вида:
````
version: '3'
services:
mysql:
          image: mysql:8
          command: --innodb_use_native_aio=0
            --default-authentication-plugin=mysql_native_password
          restart: always
          ports:
            - '3306:3306'
          volumes:
            - ./data:/var/lib/mysql

          environment:
            - MYSQL_RANDOM_ROOT_PASSWORD=yes
            - MYSQL_DATABASE=app
            - MYSQL_USER=app
            - MYSQL_PASSWORD=pass
````
2 . application.properties должен быть следующего вида:

````
spring.credit-gate.url=http://localhost:9999/credit
spring.payment-gate.url=http://localhost:9999/payment
spring.datasource.url=jdbc:mysql://192.168.99.100:3306/app
spring.datasource.username=app
spring.datasource.password=pass
````
3 . Используйте тесты в классе JourneyOfTheDayWithMySQLTest

#####PostgreSQL
Для тестирования с использованием базы данных PostgreSQLL: 
1. docker-compose.yml должен быть следующего вида:

````
version: '3'
services:
mysql:
          image: mysql:8
          command: --innodb_use_native_aio=0
            --default-authentication-plugin=mysql_native_password
          restart: always
          ports:
            - '3306:3306'
          volumes:
            - ./data:/var/lib/mysql

          environment:
            - MYSQL_RANDOM_ROOT_PASSWORD=yes
            - MYSQL_DATABASE=app
            - MYSQL_USER=app
            - MYSQL_PASSWORD=pass
````

2 . application.properties должен быть следующего вида:

````
spring.credit-gate.url=http://localhost:9999/credit
spring.payment-gate.url=http://localhost:9999/payment
#spring.datasource.url=jdbc:mysql://192.168.99.100:3306/app
#spring.datasource.username=app
#spring.datasource.password=pass
````

3 . Используйте тесты в классе JourneyOfTheDayWithPostgreSQLTest

####Инструкции для запуска тестов

Перед стартом тестов используйте команды:
1. Настройте docker-compose.yml в зависимости от выбранной БД.
1. Настройте application.properties в зависимости от выбранной БД.
1. docker-machine start default
1. docker-machine env
1. docker-compose start -d
1. java -jar artifacts/aqa-shop.jar
1. cd gate-simulator
1. npm start
1. Запускайте тесты в классе в зависимости от выбранной БД.