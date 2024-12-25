# LibrarySpring
**Инструкция**
--------------------------
**1.Склонировать репозиторий**

```git clone git@github.com:MariaShakuro/LibrarySpring.git```

**2.Проверить скачан ли Maven**

Если нет,то вот [ссылка](https://dlcdn.apache.org/maven/maven-3/3.9.9/binaries/apache-maven-3.9.9-bin.zip)

**3.Переходим в IDE **
В терминале:

Сначала надо собрать jar каждого сервиса(library,jwtDemo,libraryservice):

```cd jwtDemo```
```mvn clean install```
Возвращаемся обратно:
```cd ..```

И проделываем так со всеми тремя

**4.Собираем проект**

```docker-compose build```

**5.Поднимаем его**

```docker-compose up -d```


**5.Тестируем через Postman**

![image](https://github.com/user-attachments/assets/f71a507b-47f7-4118-9f4a-5e20dfaa1f8f)


**5.Закрываем программу**
```docker-compose down```

!!!Для отслеживания рекоммендую скачать:[Docker Desktop](https://www.docker.com/products/docker-desktop/)
!!!Также мои images лежат на Docker Hub:[Мой репозиторий](https://hub.docker.com/repository/docker/shakuro895/spring/general)
