<!--suppress HtmlDeprecatedAttribute -->
<div align="center">
    <h1>
        <a href="https://hogwartsschoolofmagic.github.io/ConfigurationService/">ConfigurationService</a>
    </h1>
</div>

<div align="center">
    <a href="https://github.com/HogwartsSchoolOfMagic/ConfigurationService/blob/master/docs/translations/README_EN.md">
        <img alt="english-version" src="https://raw.githubusercontent.com/HogwartsSchoolOfMagic/ConfigurationService/master/assets/languages/english.png"/>
    </a>
</div>

<div align="center">
    <img src="https://img.shields.io/github/last-commit/HogwartsSchoolOfMagic/ConfigurationService" height="25" alt="last-commit" />
    <a href="https://wakatime.com/@SmithyVL"><img src="https://wakatime.com/badge/github/HogwartsSchoolOfMagic/ConfigurationService.svg" height="25" alt="time-with-code" /></a>
    <a href="https://sonarcloud.io/code?id=HogwartsSchoolOfMagic_ConfigurationService"><img src="https://sonarcloud.io/api/project_badges/measure?project=HogwartsSchoolOfMagic_ConfigurationService&metric=ncloc" height="25" alt="sonar-code-lines" /></a>
    <a href="https://sonarcloud.io/summary/new_code?id=HogwartsSchoolOfMagic_ConfigurationService"><img src="https://sonarcloud.io/api/project_badges/measure?project=HogwartsSchoolOfMagic_ConfigurationService&metric=alert_status" height="25" alt="sonar-quality-gate-status" /></a>
    <a href="https://github.com/HogwartsSchoolOfMagic/ConfigurationService/actions/workflows/ci.yml"><img src="https://github.com/HogwartsSchoolOfMagic/ConfigurationService/actions/workflows/ci.yml/badge.svg" height="25" alt="ci" /></a>
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
        <td><a href="https://github.com/HogwartsSchoolOfMagic/SearchLibraryWithQueryDsl/blob/master/src/main/java/io/github/ninjaenterprise/search/model/SearchSettings.java"><b>SearchSettings</b></a></td>
        <td>-</td>
        <td><b><a href="https://github.com/HogwartsSchoolOfMagic/SearchLibraryWithQueryDsl/blob/master/src/main/java/io/github/ninjaenterprise/search/model/TableResult.java">TableResult</a>&lt;PropertyReturnDto&gt;</b></td>
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
        <td><a href="https://github.com/HogwartsSchoolOfMagic/SearchLibraryWithQueryDsl/blob/master/src/main/java/io/github/ninjaenterprise/search/model/SearchSettingsSimple.java"><b>SearchSettingsSimple</b></a></td>
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

* **JDK**: 18 и выше;
* **Docker**: postgres + kafka;

### 📋 Инструкции по запуску
___

*Все команды выполняются в консоли. Для всех действий можно использовать IDE, но кто это знает — тот так и будет делать
и инструкция ему не нужна.*

<details style="margin-left: 40px">
   <summary><b>Клонирование репозитория</b></summary>
   <ol>
      <li>Создаем папку: <code>mkdir GitProjects</code> (имя папки может быть любым, но вам нужно будет продолжать 
использовать только его);</li>
      <li>Переходим в папку: <code>cd GitProjects</code>;</li>
      <li>Клонируем репозиторий: <code>git clone https://github.com/HogwartsSchoolOfMagic/ConfigurationService.git</code>;</li>
      <li>Переходим в созданную папку: <code>cd ConfigurationService</code>.</li>
      <li>Выполнено.</li>
   </ol>
</details>

<details style="margin-left: 40px">	
   <summary><b>Сборка проекта</b></summary>
   <p>Внутри папки: <code>ConfigurationService</code>, нужно выполнить команду: <code>mvn clean install</code>.</p>
</details>

<details style="margin-left: 40px">	
   <summary><b>Настройка и запуск базы данных</b></summary>
   <p><i>После клонирования репозитория — мы уже должны быть в папке проекта.</i></p>
   <ol>
      <li>Для работы Docker нужно его сначала установить - https://docs.docker.com/engine/install;</li>
      <li>Чтобы настроить базу данных, необходимую для работы приложения, переходим в папку: <code>cd docker\postgres</code>;</li>
      <li>Запускаем .bat файл командой: <code>postgres.bat</code>. <i>Стандартный порт: 5024</i>;</li>
   </ol>
</details>

<details style="margin-left: 40px">	
   <summary><b>Запуск приложения</b></summary>

   <p>После сборки приложения выполните команду: <code>mvn spring-boot:run</code>. <b>Стандартный порт: 8888</b>.</p>
</details>

### ⚙ Включает функциональность
___

- Трансляция свойств конфигурации;
- Настройка, подключение и работа с базой данных **Postgres**;
- **Использование LiquiBase** для создания структуры базы данных.

### 🔨 Стек технологий
___

- Spring Boot: WEB, Data, Admin client;
- Spring Cloud: Server, Eureka client;
- Postgres;
- Kafka;
- LiquiBase;
- Lombok;
- Mapstruct;
- Docker.

### 🎫 Лицензия

___

**[Apache License Version 2.0](https://github.com/HogwartsSchoolOfMagic/ConfigurationService/blob/master/LICENSE)**

_Copyright ©2022, Владислав [[SmithyVL]](https://github.com/SmithyVL) Кузнецов_