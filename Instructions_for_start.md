
## Инструкции для запуска тестов

Перед стартом тестов используйте команды:
1. docker-machine start default
1. docker-machine env
1. cd gate-simulator
1. npm start
1. cd..

## Выбор базы данных:
### MySQL
Для запуска базы данных спользуйте команду:
````
docker-compose -f docker-compose-mysql.yml up -d
````
Для запуска программы используйте команду:
````
java -Dspring.datasource.url=jdbc:mysql://192.168.99.100:3306/app -jar artifacts/aqa-shop.jar
````
Для запуска тестов используйте команду:
````
gradlew test -Dtest.db.url=jdbc:mysql://192.168.99.100:3306/app
````
После прохождения тестов используйте команду для закрытия БД:
````
docker-compose -f docker-compose-mysql.yml down
````
________________
### PostgeSQL
Для запуска базы данных спользуйте команду:
````
docker-compose -f docker-compose-postgresql.yml up -d
````
Для запуска программы используйте команду:
````
java -Dspring.datasource.url=jdbc:postgresql://192.168.99.100/app -jar artifacts/aqa-shop.jar
````
Для запуска тестов используйте команду:
````
gradlew test -Dtest.db.url=jdbc:postgresql://192.168.99.100/app
````
После прохождения тестов используйте команду для закрытия БД:
````
docker-compose -f docker-compose-postgresql.yml down
````