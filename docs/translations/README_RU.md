<!--suppress HtmlDeprecatedAttribute -->
<div align="center">
    <h1>
        <a href="https://ninjaenterprise.github.io/ConfigurationService/">ConfigurationService</a>
    </h1>
</div>

<div align="center">
    <a href="https://github.com/NinjaEnterprise/ConfigurationService/blob/master/docs/README.md">
        <img alt="english-version" src="https://raw.githubusercontent.com/NinjaEnterprise/ConfigurationService/master/assets/languages/english.png"/>
    </a>
</div>

<div align="center">
    <img src="https://img.shields.io/github/last-commit/NinjaEnterprise/ConfigurationService" height="25" alt="last-commit" />
    <img src="https://wakatime.com/badge/github/NinjaEnterprise/ConfigurationService.svg" height="25" alt="time-with-code" />
    <img src="https://img.shields.io/github/v/release/NinjaEnterprise/ConfigurationService" height="25" alt="last-release" />
    <img src="https://tokei.rs/b1/github/NinjaEnterprise/ConfigurationService?category=code" height="25" alt="code-lines" />
    <img src="https://sonarcloud.io/api/project_badges/measure?project=NinjaEnterprise_ConfigurationService&metric=bugs" height="25" alt="sonar-cloud-bugs" />
    <img src="https://github.com/NinjaEnterprise/ConfigurationService/actions/workflows/check-style.yml/badge.svg" height="25" alt="checking-style" />
    <img src="https://github.com/NinjaEnterprise/ConfigurationService/actions/workflows/sonar.yml/badge.svg" height="25" alt="checking-sonar" />
    <img src="https://github.com/NinjaEnterprise/ConfigurationService/actions/workflows/build.yml/badge.svg" height="25" alt="build" />
</div>

### 📖 Описание
___

Сервер конфигураций, построенный на базе
**[spring-cloud-config-server](https://cloud.spring.io/spring-cloud-config/reference/html/)**. Для хранения конфигураций
сервисов используется **JDBC Backend** (_база данных - PostgreSQL_). Для работы с
**созданием/обновлением/удалением/получением** свойств конфигураций - предоставляется **REST** взаимодействие:

<table>
    <thead>
      <tr>
        <th>МЕТОД</th>
        <th>ПУТЬ</th>
        <th>ОПИСАНИЕ</th>
        <th>ТЕЛО ЗАПРОСА</th>
        <th>ПАРАМЕТРЫ ЗАПРОСА</th>
        <th>ОТВЕТ ОТ СЕРВЕРА</th>
      </tr>
    </thead>
    <tbody align="center">
      <tr>
        <td style="color: #168541"><b>POST</b></td>
        <td><i>/api/configurations/page</i></td>
        <td>Постраничный поиск свойств конфигураций</td>
        <td><a href="https://github.com/NinjaEnterprise/SearchLibraryWithQueryDsl/blob/master/src/main/java/io/github/ninjaenterprise/search/model/SearchSettings.java"><b>SearchSettings</b></a></td>
        <td>-</td>
        <td><b><a href="https://github.com/NinjaEnterprise/SearchLibraryWithQueryDsl/blob/master/src/main/java/io/github/ninjaenterprise/search/model/TableResult.java">TableResult</a>&lt;PropertyReturnDto&gt;</b></td>
      </tr>
      <tr>
        <td style="color: #168541"><b>POST</b></td>
        <td><i>/api/configurations</i></td>
        <td>Создание свойства конфигурации. Если оно уже есть - ошибка дублирования</td>
        <td><b>PropertyDto</b></td>
        <td><b>refresh</b></td>
        <td><b>PropertyReturnDto</b></td>
      </tr>
      <tr>
        <td style="color: #168541"><b>POST</b></td>
        <td><i>/api/configurations/batch</i></td>
        <td>Создание нескольких свойств конфигураций. Если они существуют - ошибка дублирования</td>
        <td><b>List&lt;PropertyDto&gt;</b></td>
        <td><b>refresh</b></td>
        <td><b>List&lt;PropertyReturnDto&gt;</b></td>
      </tr>
      <tr>
        <td style="color: #198daa"><b>GET</b></td>
        <td><i>/api/configurations/{propertyId}</i></td>
        <td>Поиск свойства конфигурации по UUID идентификатору</td>
        <td>-</td>
        <td>-</td>
        <td><b>PropertyReturnDto</b></td>
      </tr>
      <tr>
        <td style="color: #aa770a"><b>PUT</b></td>
        <td><i>/api/configurations</i></td>
        <td>Обновление существующего свойства конфигурации. Если оно не существует - создается новое</td>
        <td><b>PropertyDto</b></td>
        <td>refresh</td>
        <td><b>PropertyReturnDto</b></td>
      </tr>
      <tr>
        <td style="color: #aa770a"><b>PUT</b></td>
        <td><i>/api/configurations/batch</i></td>
        <td>Обновление нескольких существующих свойств конфигураций. Если они не существуют - создаются новые</td>
        <td><b>List&lt;PropertyDto&gt;</b></td>
        <td>refresh</td>
        <td><b>List&lt;PropertyReturnDto&gt;</b></td>
      </tr>
      <tr>
        <td style="color: #ae2a24"><b>DELETE</b></td>
        <td><i>/api/configurations/batch</i></td>
        <td>Удаление нескольких свойств конфигураций</td>
        <td><a href="https://github.com/NinjaEnterprise/SearchLibraryWithQueryDsl/blob/master/src/main/java/io/github/ninjaenterprise/search/model/SearchSettingsSimple.java"><b>SearchSettingsSimple</b></a></td>
        <td>refresh</td>
        <td><b>List&lt;PropertyReturnDto&gt;</b></td>
      </tr>
      <tr>
        <td style="color: #ae2a24"><b>DELETE</b></td>
        <td><i>/api/configurations/{propertyId}</i></td>
        <td>Удаление свойства конфигурации по UUID идентификатору</td>
        <td>-</td>
        <td>refresh</td>
        <td><b>PropertyReturnDto</b></td>
      </tr>
      <tr>
        <td style="color: #168541"><b>POST</b></td>
        <td><i>/api/configurations/refresh/{destination}</i></td>
        <td>Отправка сообщения выбранному сервису с информацией о том, что нужно получить обновленные свойства конфигурации</td>
        <td>-</td>
        <td>-</td>
        <td>-</td>
      </tr>
      <tr>
        <td style="color: #168541"><b>POST</b></td>
        <td><i>/api/configurations/refresh</i></td>
        <td>Отправка сообщения нескольким сервисам с информацией о том, что нужно получить обновленные свойства конфигурации</td>
        <td><b>List&lt;String&gt;</b></td>
        <td>-</td>
        <td>-</td>
      </tr>
    </tbody>
</table>

### ❗ Требования
___

* **JDK**: 17 и выше;
* **Docker**;

### 📋 Инструкции по запуску
___

*Все команды выполняются в консоли. Для всех действий можно использовать IDE, но кто это знает — тот так и будет делать
и инструкция ему не нужна.*
<details style="margin-left: 40px">	
   <summary><b>Клонирование репозитория</b></summary>

1. Создаем папку: `mkdir GitProjects` (имя папки может быть любым, но вам нужно будет продолжать использовать только
   его);
2. Переходим в папку: `cd GitProjects`;
3. Клонируем репозиторий: `git clone https://github.com/NinjaEnterprise/ConfigurationService.git`;
4. Переходим в созданную папку: `cd ConfigurationService`;
5. Выполнено.
</details>

<details style="margin-left: 40px">	
   <summary><b>Настройка базы данных</b></summary>

   *После клонирования репозитория — мы уже должны быть в папке проекта.*
   1. Для работы Docker нужно его сначала **[установить](https://docs.docker.com/engine/install/)**;
   2. Чтобы настроить базу данных, необходимую для работы приложения, переходим в папку: `cd docker\postgresql`;
   3. Запускаем .bat файл командой: `postgres.bat`. *Стандартный порт: 5025*
</details>


<details style="margin-left: 40px">	
   <summary><b>Сборка проекта</b></summary>

1. После запуска Docker и настройки базы данных возвращаемся в корень проекта: `cd ..\..`;
2. Выполняем команду: `mvn clean install`.
</details>

<details style="margin-left: 40px">	
   <summary><b>Запуск приложения</b></summary>

После сборки приложения выполните команду: <code>mvn spring-boot:run</code>. <b>Стандартный порт: 8888</b>.
</details>

### ⚙ Включает функциональность
___

- Трансляция свойств конфигурации;
- Настройка, подключение и работа с базой данных **Postgres**;
- **Использование LiquiBase** для создания структуры базы данных.

### 🔨 Стек технологий
___

- Spring Boot: WEB, Data;
- Spring Cloud: Server, Eureka;
- Postgres;
- LiquiBase;
- Lombok;
- Mapstruct;
- Docker.

### 🎫 Лицензия
___

**[MIT](https://github.com/NinjaEnterprise/ConfigurationService/blob/master/LICENSE)**

_Copyright ©2021, Владислав [[Bangerok]](https://github.com/Bangerok) Кузнецов_