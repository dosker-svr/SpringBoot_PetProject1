#!/usr/bin/env bash

mvn clean package

echo 'Copy files...'

#копируем с локал. машины собранный проект на сервер
scp -i ~/.ssh/id_rsa \
  target/SpringBoot_PetProject1_WedApplication-0.0.1-SNAPSHOT.jar \
  dosker@192.168.56.101:/home/dosker/apps/

echo ' |||===   Restart server...   ===|||'

ssh -i ~/.ssh/id_rsa dosker@192.168.56.101 << EOF

pgrep java | xargs kill -9
nohup java -jar ~/apps/SpringBoot_PetProject1_WedApplication-0.0.1-SNAPSHOT.jar > log.txt &

EOF
# выбираем все java процессы | удалем выбранные java процессы

# запускаем .jar, весь вывод записываем в файл log.txt; '&' - для работы программы в фоне
# nohup - позволяет не завершить выполнеение программы даже если пользователь завершит сеанс (программа работает пока работает сервер)

echo 'Bye...'


:<<comments

как деплоим приложение на сервер:
  -добавляем в pom.xml зависимость на JAXB
  -делаем 2 файлы application.properties (1 для сервера, 1 для работы на локальной машине -
в Configuration запуска приложения указать 'Program arguments' '--spring.profiles.active=dev')
  -генерируем ключи ssh и доб. на сервер
  -устанавливаем на сервере java+postgres+nginx
  -добавляем базу данных для приложения в postgres (web_application_sweat) + добавляем пароль
  -настраиваем nginx.conf (/etc/nginx/sites-enabled/default)

  -подготавливаем скрипт
  запускаем в локальном bash этот скрипт

comments