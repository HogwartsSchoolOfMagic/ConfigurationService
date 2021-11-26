<!--suppress HtmlDeprecatedAttribute -->
<div align="center">
    <h1>
        <a href="https://ninjaenterprise.github.io/NinjaConfigurationService/">NinjaConfigurationService</a>
    </h1>
</div>

<div align="center">
    <a href="https://github.com/NinjaEnterprise/NinjaConfigurationService/blob/master/docs/translations/README_RU.md">
        <img alt="russian-version" src="https://raw.githubusercontent.com/NinjaEnterprise/NinjaConfigurationService/master/assets/languages/russian.png"/>
    </a>
</div>

<div align="center">
    <img src="https://img.shields.io/github/last-commit/NinjaEnterprise/NinjaConfigurationService" height="25" alt="last-commit" />
    <img src="https://wakatime.com/badge/github/NinjaEnterprise/NinjaConfigurationService.svg" height="25" alt="time-with-code" />
    <img src="https://img.shields.io/github/v/release/NinjaEnterprise/NinjaConfigurationService" height="25" alt="last-release" />
    <img src="https://tokei.rs/b1/github/NinjaEnterprise/NinjaConfigurationService?category=code" height="25" alt="code-lines" />
    <img src="https://sonarcloud.io/api/project_badges/measure?project=NinjaEnterprise_NinjaConfigurationService&metric=bugs" height="25" alt="sonar-cloud-bugs" />
    <img src="https://github.com/NinjaEnterprise/NinjaConfigurationService/actions/workflows/check-style.yml/badge.svg" height="25" alt="checking-style" />
    <img src="https://github.com/NinjaEnterprise/NinjaConfigurationService/actions/workflows/sonar.yml/badge.svg" height="25" alt="checking-sonar" />
    <img src="https://github.com/NinjaEnterprise/NinjaConfigurationService/actions/workflows/build.yml/badge.svg" height="25" alt="build" />
</div>

### 📖 Description
___

Engaged in maintaining the configurations used by client applications. Consists of the following modules:
1. **[ConfigurationAPI](https://github.com/NinjaEnterprise/NinjaConfigurationService/tree/master/configuration-api)**.
Module providing interfaces for interaction with the server;
   

2. **[ConfigurationClient](https://github.com/NinjaEnterprise/NinjaConfigurationService/tree/master/configuration-client)**.
Module providing interfaces for REST calls from other services;


3. **[ConfigurationJDBC](https://github.com/NinjaEnterprise/NinjaConfigurationService/tree/master/configuration-jdbc)**.
Module responsible for working with the database provider via JDBC;


4. **[ConfigurationREST](https://github.com/NinjaEnterprise/NinjaConfigurationService/tree/master/configuration-rest)**.
Module containing REST interaction with the configuration service;


5. **[ConfigurationServer](https://github.com/NinjaEnterprise/NinjaConfigurationService/tree/master/configuration-server)**.
Module for starting the configuration server.

### ❗ Requirements
___

* **JDK**: 17 and higher;
* **Docker**.

### 📋 Start-up instructions
___

*All commands are executed in the console. For all actions, you can use the IDE, but who knows about it — and
will do it, the instruction is not needed.*
<details style="margin-left: 40px">	
   <summary><b>Clone Repository</b></summary>
   <ol>
      <li>Create a folder: <code>mkdir GitProjects</code> (the folder name can be anything, but you will need to 
      continue to use only it);</li>
      <li>Go to the folder: <code>cd GitProjects</code>;</li>
      <li>Cloning a repository: <code>git clone https://github.com/NinjaEnterprise/NinjaConfigurationService.git</code>;</li>
      <li>Go to the folder: <code>cd NinjaConfigurationService</code>.</li>
      <li>Complete.</li>
   </ol>
</details>

<details style="margin-left: 40px">	
   <summary><b>Database Setup</b></summary>

   <i>After cloning the repository — we should already be in the project folder.</i>
   <ol>
      <li>For docker to work, you need to <b><a href="https://docs.docker.com/engine/install/">install</a></b> it first;</li>
      <li>To configure the database required for the application to work, go to the folder: <code>cd docker\postgresql</code>;</li>
      <li>Run the .bat file with the command: <code>postgres.bat</code>. <b>Default port: 5025</b></li>
   </ol>
</details>


<details style="margin-left: 40px">	
   <summary><b>Build Code</b></summary>
   <ol>
      <li>After starting docker and setting up the database, we go back to the root of the project: <code>cd ..\..</code>;</li>
      <li>We execute the command: <code>mvn clean install</code>.</li>
   </ol>
</details>

<details style="margin-left: 40px">	
   <summary><b>Application launch</b></summary>
   <ol>
      <li>After assembling the application, go to the launched module: <code>cd configuration-server</code>;</li>
      <li>We execute the command: <code>mvn spring-boot:run</code>. <b>Default port: 8888</b>.</li>
   </ol>
</details>

### ⚙ Includes functionality
___

- Translation of configuration properties;
- Setting up, connecting and working with the **Postgres database**;
- **Using LiquiBase** to create a database structure.

### 🔨 Technology stack
___

- Spring Boot: WEB, Data;
- Spring Cloud: Server, OpenFeign;
- Postgres;
- LiquiBase;
- Lombok;
- Mapstruct;
- Docker.

### 🎫 License
___

**[MIT](https://github.com/NinjaEnterprise/NinjaConfigurationService/blob/master/LICENSE)**

_Copyright ©2021, Vladislav [[Bangerok]](https://github.com/Bangerok) Kuznetsov_