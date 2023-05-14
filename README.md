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

- https://rickandmortyapi.com/documentation/#get-all-characters

- https://rickandmortyapi.com/documentation/#get-all-episodes

- https://rickandmortyapi.com/documentation/#get-all-locations


<img src="https://github.com/Ropotov/AstonProject/assets/87120543/1d2c7971-50e7-4226-aa65-41d4174ec75a" width="200" height="400">
<img src="https://github.com/Ropotov/AstonProject/assets/87120543/bfb497c1-3f24-472c-ba30-c25a473878fb" width="200" height="400">
<img src="https://github.com/Ropotov/AstonProject/assets/87120543/88a2a24f-be23-4713-aa28-388474ac2bda" width="200" height="400">

Все списки поддерживают пагинацию, реализованную с помощью библиотеки Paging3. 
Дынные полученные из сети сохраняются в базу данных Room, для доступа к ним без интернета.
Все вкладки поддерживают Pull-to-Refresh.
В момент загрузки данных необходимо отображаетсяпрогресс-индикатор.




Навигация по приложению осуществляеться с помощью BottomNavigation, который имеет 3 вкладки.

<img src="https://github.com/Ropotov/AstonProject/assets/87120543/766aca76-50c3-4aad-8459-250c5a57a613" width="800" height="100">



Ко клику на элемент списка персонажей, локаций или эпизодов открывается экран с детальной информацией.

Данные получены по запросам:

- https://rickandmortyapi.com/documentation/#get-a-single-character

- https://rickandmortyapi.com/documentation/#get-a-single-location

- https://rickandmortyapi.com/documentation/#get-a-single-episode

<img src="https://github.com/Ropotov/AstonProject/assets/87120543/0833cbb2-67b7-4a02-825e-759bc379c65b" width="200" height="400">
<img src="https://github.com/Ropotov/AstonProject/assets/87120543/588d43f2-0e25-47bd-8436-2ab1881074fa" width="200" height="400">
<img src="https://github.com/Ropotov/AstonProject/assets/87120543/4573f571-46d6-4d8a-8a20-6558f1d29e83" width="200" height="400">


В ToolBar отображается название текущего экранаю Приложение поддерживает навигацию назад. На всех не основных экранах в Toolbar появляется кнопка назад.

<img src="https://github.com/Ropotov/AstonProject/assets/87120543/f5bddc37-2c14-42e9-870e-48ed119d9220" width="800" height="100">

Каждый список имеет возможноть фильтрации и поиска по имени.
Для запуска экрана фитрации необходимо нажать кнопку фильтра.

<img src="https://github.com/Ropotov/AstonProject/assets/87120543/fbfdb341-8e5b-4b98-8993-6b6c70e14ccb" width="100" height="100">

После нажатия открывается фрагмент фильтрации и поиска.

Все возможные способы применения фильтров можно обнаружитьтут:

- https://rickandmortyapi.com/documentation/#filter-characters

- https://rickandmortyapi.com/documentation/#filter-locations

- https://rickandmortyapi.com/documentation/#filter-episodes

<img src="https://github.com/Ropotov/AstonProject/assets/87120543/aac1f558-ac14-4e23-b7ec-9d2f5e92944e" width="200" height="400">
<img src="https://github.com/Ropotov/AstonProject/assets/87120543/869d5074-2826-4454-b501-ea9c6e9bce92" width="200" height="400">
<img src="https://github.com/Ropotov/AstonProject/assets/87120543/606c5918-5058-4895-9ded-cee2925c38d0" width="200" height="400">





