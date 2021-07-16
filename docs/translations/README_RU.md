<!--suppress HtmlDeprecatedAttribute -->
<div align="center">
    <h1>
        <a href="https://ninjaenterprise.github.io/NinjaConfiguration/">NinjaConfiguration</a>
    </h1>
</div>

<div align="center">
    <a href="https://github.com/NinjaEnterprise/NinjaConfiguration/blob/master/docs/README.md">
        <img alt="english-version" src="https://raw.githubusercontent.com/NinjaEnterprise/NinjaConfiguration/master/assets/languages/english.png"/>
    </a>
</div>

<div align="center">
    <img src="https://img.shields.io/github/last-commit/NinjaEnterprise/NinjaConfiguration" height="25" alt="last-commit" />
    <img src="https://wakatime.com/badge/github/NinjaEnterprise/NinjaConfiguration.svg" height="25" alt="time-with-code" />
    <img src="https://img.shields.io/github/v/release/NinjaEnterprise/NinjaConfiguration" height="25" alt="last-release" />
    <img src="https://tokei.rs/b1/github/NinjaEnterprise/NinjaConfiguration?category=code" height="25" alt="code-lines" />
    <img src="https://sonarcloud.io/api/project_badges/measure?project=NinjaEnterprise_NinjaConfiguration&metric=bugs" height="25" alt="sonar-cloud-bugs" />
    <img src="https://github.com/NinjaEnterprise/NinjaConfiguration/actions/workflows/check-style.yml/badge.svg" height="25" alt="checking-style" />
    <img src="https://github.com/NinjaEnterprise/NinjaConfiguration/actions/workflows/sonar.yml/badge.svg" height="25" alt="checking-sonar" />
    <img src="https://github.com/NinjaEnterprise/NinjaConfiguration/actions/workflows/build.yml/badge.svg" height="25" alt="build" />
</div>

### 📖 Описание
___

Engaged in maintaining the configurations used by client applications. Consists of the following modules:
1. **[ConfigurationAPI](https://github.com/NinjaEnterprise/NinjaConfiguration/tree/master/configuration-api)**.
Модуль, предоставляющий интерфейсы для взаимодействия с сервером;


2. **[ConfigurationClient](https://github.com/NinjaEnterprise/NinjaConfiguration/tree/master/configuration-client)**.
Модуль, предоставляющий интерфейсы для вызовов REST из других сервисов;


3. **[ConfigurationJDBC](https://github.com/NinjaEnterprise/NinjaConfiguration/tree/master/configuration-jdbc)**.
Модуль, отвечающий за работу с провайдером базы данных через JDBC;


4. **[ConfigurationREST](https://github.com/NinjaEnterprise/NinjaConfiguration/tree/master/configuration-rest)**.
Модуль, содержащий взаимодействие REST со службой конфигурации;


5. **[ConfigurationServer](https://github.com/NinjaEnterprise/NinjaConfiguration/tree/master/configuration-server)**.
Модуль для запуска сервера конфигурации.

### ❗ Требования
___

* **JDK**: 16 и выше;
* **Docker**.

### 📋 Инструкции по запуску
___

*Все команды выполняются в консоли. Для всех действий можно использовать IDE, но кто это знает — тот так и будет делать
и инструкция ему не нужна.*
<details style="margin-left: 40px">	
   <summary><b>Клонирование репозитория</b></summary>

   1. Создаем папку: `mkdir GitProjects` (имя папки может быть любым, но вам нужно будет продолжать использовать только
      его);
   2. Переходим в папку: `cd GitProjects`;
   3. Клонируем репозиторий: `git clone https://github.com/NinjaEnterprise/NinjaConfiguration.git`;
   4. Переходим в созданную папку: `cd NinjaConfiguration`;
   5. Выполнено (**см.: результат ниже**).

   <img alt="clone-repo" src="https://raw.githubusercontent.com/NinjaEnterprise/NinjaConfiguration/master/assets/startup/clone-repository.png"/>
</details>

<details style="margin-left: 40px">	
   <summary><b>Настройка базы данных</b></summary>

   *После клонирования репозитория — мы уже должны быть в папке проекта.*
   1. Для работы Docker нужно его сначала **[установить](https://docs.docker.com/engine/install/)**;
   2. Чтобы настроить базу данных, необходимую для работы приложения, переходим в папку: `cd docker\postgresql`;
   3. Запускаем .bat файл командой: `postgres.bat` (**см.: результат ниже**). *Стандартный порт: 5025*

   <img alt="ninja-docker" src="https://raw.githubusercontent.com/NinjaEnterprise/NinjaConfiguration/master/assets/startup/ninja-docker.gif"/>
   <img alt="docker-info" src="https://raw.githubusercontent.com/NinjaEnterprise/NinjaConfiguration/master/assets/startup/docker-info.png"/>
</details>


<details style="margin-left: 40px">	
   <summary><b>Сборка проекта</b></summary>

   1. После запуска Docker и настройки базы данных возвращаемся в корень проекта: `cd ..\..`;
   2. Выполняем команду: `mvn clean install` (**см.: результат ниже**).

   <img alt="build-code" src="https://raw.githubusercontent.com/NinjaEnterprise/NinjaConfiguration/master/assets/startup/build-code.png"/>
</details>

<details style="margin-left: 40px">	
   <summary><b>Запуск приложения</b></summary>

   1. После сборки приложения переходим в модуль для его запуска: `cd configuration-server`;
   2. Выполняем команду: `mvn spring-boot:run` (**см.: результат ниже**). *Стандартный порт: 9001*.

   <img alt="ninja-started" src="https://raw.githubusercontent.com/NinjaEnterprise/NinjaConfiguration/master/assets/startup/ninja-started.gif"/>
</details>

### ⚙ Включает функциональность
___

- Трансляция свойств конфигурации;
- Настройка, подключение и работа с базой данных **Postgres**;
- **Использование LiquiBase** для создания структуры базы данных.

### 🔨 Стек технологий
___

- Spring Boot: WEB, Data;
- Spring Cloud: Server, OpenFeign;
- Postgres;
- LiquiBase;
- Lombok;
- Mapstruct;
- Docker.

### 🎫 Лицензия
___

**[Creative Commons Legal Code](https://github.com/NinjaEnterprise/NinjaTemplate/blob/master/LICENSE)**

_Copyright ©2021, Vladislav [[Bangerok]](https://github.com/Bangerok) Kuznetsov_