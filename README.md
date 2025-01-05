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

Если вы выбираете регистрироваться,то вы сможете автоматически быть только User и не все функции будут доступны

Если вы хотите иметь доступ ко всем функциям библиотеки,то в login нужно будет указать:```username: "shakuro",
                                                                                           password: "123456"```
                                                                                           
  1.Импортируем коллекцию в Postman
  
    1)Откройте Postman.
    2)Нажмите на кнопку Import в левом верхнем углу.
    Появится окошко:![image](https://github.com/user-attachments/assets/b9a189e8-74e7-4e62-8c4c-86549d748700)
    Нажмите в середине на **files** и нажмите на  BooksAPI.postman_collection.json(которое находится в моем проекте в папке,в которую вы скопировали мой проект
    

**5.Закрываем программу**
```docker-compose down```

!!!Для отслеживания рекоммендую скачать:[Docker Desktop](https://www.docker.com/products/docker-desktop/)

!!!Также мои images лежат на Docker Hub:[Мой репозиторий](https://hub.docker.com/repository/docker/shakuro895/spring/general)

