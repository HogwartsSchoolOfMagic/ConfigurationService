<!--suppress HtmlDeprecatedAttribute -->
<div align="center">
    <h1>
        <a href="https://ninjaenterprise.github.io/NinjaTemplate/">Readme для репозитория Ninja Template</a>
    </h1>
</div>

<div align="center">
    <a href="https://github.com/NinjaEnterprise/NinjaTemplate/blob/master/docs/translations/README.md">
        <img alt="english-version" src="https://raw.githubusercontent.com/NinjaEnterprise/NinjaTemplate/master/assets/languages/english.png"/>
    </a>
</div>

<div align="center">
    <img src="https://img.shields.io/github/last-commit/NinjaEnterprise/NinjaTemplate" height="25" alt="last-commit" />
    <img src="https://img.shields.io/github/v/release/NinjaEnterprise/NinjaTemplate" height="25" alt="last-release" />
    <img src="https://tokei.rs/b1/github/NinjaEnterprise/NinjaTemplate?category=code" height="25" alt="code-lines" />
    <img src="https://sonarcloud.io/api/project_badges/measure?project=NinjaEnterprise_NinjaTemplate&metric=bugs" height="25" alt="sonar-cloud-bugs" />
    <img src="https://github.com/NinjaEnterprise/NinjaTemplate/actions/workflows/check-style.yml/badge.svg" height="25" alt="checking-style" />
    <img src="https://github.com/NinjaEnterprise/NinjaTemplate/actions/workflows/sonar.yml/badge.svg" height="25" alt="checking-sonar" />
    <img src="https://github.com/NinjaEnterprise/NinjaTemplate/actions/workflows/build.yml/badge.svg" height="25" alt="build" />
</div>

### 📖 Описание
___

Шаблон, который можно использовать при создании новых репозиториев в организации — NinjaEnterprise.

### ❗ Требования

Требования к среде для корректной работы и запуска приложения.

### 📋 Инструкция по запуску
___

*Все команды выполняются в консоли.*
1. Создать папку: `mkdir GitProjects`;
2. Перейти в нее: `cd GitProjects`;
3. Клонировать код репозитория: `git clone https://github.com/NinjaEnterprise/NinjaTemplate.git`;
4. Перейти в созданную папку: `cd NinjaTemplate`;
5. Собрать проект: `mvn clean install`;
6. После выполнения 5 пункта — нужно перейти внутрь модуля. Переходим в него: `go ninja-template-module-one`;
7. Внутри будет находиться файл — **“ninja-template-module-one-0.0.0.jar”**.
Запускаем его командой: `java -jar target\ninja-template-module-one-0.0.0.jar`;
8. Запустится Spring Boot приложение **(see: Application interface)**.

**Бонус**: вместо 7 пункта можно действовать иначе. Там, где мы были в тот момент, нам нужно выполнить команду —
`mvn spring-boot:run`.

```java
public class TemplateApplication {

   public static void main(String[] args) { // Используется для запуска приложения.
      run(TemplateApplication.class, args);
   }
}
```

### 💻 Интерфейс приложения
___
<div align="center">
   <img style="border: solid #465241;" src="https://raw.githubusercontent.com/NinjaEnterprise/NinjaTemplate/master/assets/ninja-template.gif" alt="spring-interface" />
</div>


### 🎫 Лицензия
___

**[Creative Commons Legal Code](https://github.com/NinjaEnterprise/NinjaTemplate/blob/master/LICENSE)**

_Copyright ©2021, Vladislav [[Bangerok]](https://github.com/Bangerok) Kuznetsov_