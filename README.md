# Rick and morty
Приложение написано на Java и Kotlin.

API https://rickandmortyapi.com/

## Стек

* Kotlin
* Java
* Retrofit
* MVVM, Clean architecture
* Coroutines
* RxJava
* Pagging3
* Room
* Dagger2
* Glide

## Информация

При запуске приложения запускается Splash Screen.

<img src="https://github.com/Ropotov/AstonProject/assets/87120543/635ba9fa-b641-4abe-9f6e-3b267338a639" width="200" height="400">


После загрузки данных отображает списки персонажей, локаций и эпизодов мультсериала Rick and Morty.

Дынные получены с помощью запросов:

https://rickandmortyapi.com/documentation/#get-all-characters

https://rickandmortyapi.com/documentation/#get-all-episodes

https://rickandmortyapi.com/documentation/#get-all-locations


<img src="https://github.com/Ropotov/AstonProject/assets/87120543/1d2c7971-50e7-4226-aa65-41d4174ec75a" width="200" height="400">
<img src="https://github.com/Ropotov/AstonProject/assets/87120543/bfb497c1-3f24-472c-ba30-c25a473878fb" width="200" height="400">
<img src="https://github.com/Ropotov/AstonProject/assets/87120543/88a2a24f-be23-4713-aa28-388474ac2bda" width="200" height="400">

Все списки поддерживают пагинацию, реализованну с помощью библиотеки Paging3.



Навигация по приложению осуществляеться с помощью BottomNavigation, который имеет 3 вкладки.

<img src="https://github.com/Ropotov/AstonProject/assets/87120543/766aca76-50c3-4aad-8459-250c5a57a613" width="800" height="100">



Ко клику на элемент списка персонажей, локаций или эпизодов открывается экран с детальной информацией.

Данные получены по запросам:

https://rickandmortyapi.com/documentation/#get-a-single-character

https://rickandmortyapi.com/documentation/#get-a-single-location

https://rickandmortyapi.com/documentation/#get-a-single-episode

<img src="https://github.com/Ropotov/AstonProject/assets/87120543/0833cbb2-67b7-4a02-825e-759bc379c65b" width="200" height="400">
<img src="https://github.com/Ropotov/AstonProject/assets/87120543/588d43f2-0e25-47bd-8436-2ab1881074fa" width="200" height="400">
<img src="https://github.com/Ropotov/AstonProject/assets/87120543/4573f571-46d6-4d8a-8a20-6558f1d29e83" width="200" height="400">

