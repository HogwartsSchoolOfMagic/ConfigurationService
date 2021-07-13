<!--suppress HtmlDeprecatedAttribute -->
<div align="center">
    <h1>
        <a href="https://ninjaenterprise.github.io/NinjaTemplate/">Readme for Ninja Template Repository</a>
    </h1>
</div>

<div align="center">
    <a href="https://github.com/NinjaEnterprise/NinjaTemplate/blob/master/docs/translations/README_RU.md">
        <img alt="russian-version" src="https://raw.githubusercontent.com/NinjaEnterprise/NinjaTemplate/master/assets/languages/russian.png"/>
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

### 📖 Description
___

A template that can be used when creating new repositories in an organization is NinjaEnterprise.

### ❗ Requirements

Requirements for the environment for the correct operation and launch of the application.

### 📋 Instructions for starting
___

*All commands are executed in the console.*
1. Create a folder: `mkdir GitProjects`.
2. Go to the folder: `cd GitProjects`.
3. Cloning a repository: `git clone https://github.com/NinjaEnterprise/NinjaTemplate.git`.
4. Go to the folder: `cd NinjaTemplate`.
5. Build project: `mvn clean install`.
6. After completing 5 points — you need to go to the module. We go into it: `go ninja-template-module-one`.
7. Inside there will be a target folder with a JAR file — **“ninja-template-module-one-0.0.0.jar”**.
   We execute the command: `java -jar target\ninja-template-module-one-0.0.0.jar`.
8. Spring Boot application will start **(see: Application interface)**.

**Bonus**: instead of the 7 point, you can act differently. Where we were at that moment, we need to execute the command — 
`mvn spring-boot:run`.

```java
public class TemplateApplication {

   public static void main(String[] args) { // Used to launch the application.
      run(TemplateApplication.class, args);
   }
}
```

### 💻 Application interface
___
<div align="center">
   <img style="border: solid #465241;" src="https://raw.githubusercontent.com/NinjaEnterprise/NinjaTemplate/master/assets/ninja-template.gif" alt="spring-interface" />
</div>


### 🎫 License
___

**[Creative Commons Legal Code](https://github.com/NinjaEnterprise/NinjaTemplate/blob/master/LICENSE)**

_Copyright ©2021, Vladislav [[Bangerok]](https://github.com/Bangerok) Kuznetsov_