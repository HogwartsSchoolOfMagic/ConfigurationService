<!--suppress HtmlDeprecatedAttribute -->
<div align="center">
    <h1>
        <a href="https://hogwartsschoolofmagic.github.io/ConfigurationService/">ConfigurationService</a>
    </h1>
</div>

<div align="center">
    <a href="https://github.com/HogwartsSchoolOfMagic/ConfigurationService/blob/master/docs/README.md">
        <img alt="russian-version" src="https://raw.githubusercontent.com/HogwartsSchoolOfMagic/ConfigurationService/master/assets/languages/russian.png"/>
    </a>
</div>

<div align="center">
    <img src="https://img.shields.io/github/last-commit/HogwartsSchoolOfMagic/ConfigurationService" height="25" alt="last-commit" />
    <a href="https://wakatime.com/@SmithyVL"><img src="https://wakatime.com/badge/github/HogwartsSchoolOfMagic/ConfigurationService.svg" height="25" alt="time-with-code" /></a>
    <a href="https://sonarcloud.io/code?id=HogwartsSchoolOfMagic_ConfigurationService"><img src="https://sonarcloud.io/api/project_badges/measure?project=HogwartsSchoolOfMagic_ConfigurationService&metric=ncloc" height="25" alt="sonar-code-lines" /></a>
    <a href="https://sonarcloud.io/summary/new_code?id=HogwartsSchoolOfMagic_ConfigurationService"><img src="https://sonarcloud.io/api/project_badges/measure?project=HogwartsSchoolOfMagic_ConfigurationService&metric=alert_status" height="25" alt="sonar-quality-gate-status" /></a>
    <a href="https://github.com/HogwartsSchoolOfMagic/ConfigurationService/actions/workflows/ci.yml"><img src="https://github.com/HogwartsSchoolOfMagic/ConfigurationService/actions/workflows/ci.yml/badge.svg" height="25" alt="ci" /></a>
</div>

### 📖 Description
___

Microservice is a configuration service built on top of
**[spring-cloud-config-server](https://cloud.spring.io/spring-cloud-config/reference/html/)**. **JDBC Backend**
(_database — PostgreSQL_) is used to store service configurations. To work with **creating/updating/deleting/getting**
configuration properties — **REST** interaction is provided:

<table>
    <thead>
      <tr>
        <th>METHOD</th>
        <th>PATH</th>
        <th>DESCRIPTION</th>
        <th>BODY</th>
        <th>PARAM</th>
        <th>RESPONSE</th>
      </tr>
    </thead>
    <tbody align="center">
      <tr>
        <td style="color: #168541"><b>POST</b></td>
        <td><i>/api/configurations/page</i></td>
        <td>Getting a page with configuration properties</td>
        <td><a href="https://github.com/HogwartsSchoolOfMagic/SearchLibraryWithQueryDsl/blob/master/src/main/java/io/github/ninjaenterprise/search/model/SearchSettings.java"><b>SearchSettings</b></a></td>
        <td>-</td>
        <td><b><a href="https://github.com/HogwartsSchoolOfMagic/SearchLibraryWithQueryDsl/blob/master/src/main/java/io/github/ninjaenterprise/search/model/TableResult.java">TableResult</a>&lt;PropertyReturnDto&gt;</b></td>
      </tr>
      <tr>
        <td style="color: #168541"><b>POST</b></td>
        <td><i>/api/configurations</i></td>
        <td>Creation of a new configuration property. If there is one already, there is a duplicate error</td>
        <td><b>PropertyDto</b></td>
        <td><b>refresh</b></td>
        <td><b>PropertyReturnDto</b></td>
      </tr>
      <tr>
        <td style="color: #168541"><b>POST</b></td>
        <td><i>/api/configurations/batch</i></td>
        <td>Creation of a large number of new configurations. If there are already such ones - a duplicate error</td>
        <td><b>List&lt;PropertyDto&gt;</b></td>
        <td><b>refresh</b></td>
        <td><b>List&lt;PropertyReturnDto&gt;</b></td>
      </tr>
      <tr>
        <td style="color: #198daa"><b>GET</b></td>
        <td><i>/api/configurations/{propertyId}</i></td>
        <td>Search for a configuration property by its UUID identifier</td>
        <td>-</td>
        <td>-</td>
        <td><b>PropertyReturnDto</b></td>
      </tr>
      <tr>
        <td style="color: #aa770a"><b>PUT</b></td>
        <td><i>/api/configurations</i></td>
        <td>Updating an existing configuration property. If there is no such thing, a new one is created</td>
        <td><b>PropertyDto</b></td>
        <td>refresh</td>
        <td><b>PropertyReturnDto</b></td>
      </tr>
      <tr>
        <td style="color: #aa770a"><b>PUT</b></td>
        <td><i>/api/configurations/batch</i></td>
        <td>Updating a large number of pre-existing configuration properties. If there is no such thing, a new one is created</td>
        <td><b>List&lt;PropertyDto&gt;</b></td>
        <td>refresh</td>
        <td><b>List&lt;PropertyReturnDto&gt;</b></td>
      </tr>
      <tr>
        <td style="color: #ae2a24"><b>DELETE</b></td>
        <td><i>/api/configurations/batch</i></td>
        <td>Removing a large number of configuration properties</td>
        <td><a href="https://github.com/HogwartsSchoolOfMagic/SearchLibraryWithQueryDsl/blob/master/src/main/java/io/github/ninjaenterprise/search/model/SearchSettingsSimple.java"><b>SearchSettingsSimple</b></a></td>
        <td>refresh</td>
        <td><b>List&lt;PropertyReturnDto&gt;</b></td>
      </tr>
      <tr>
        <td style="color: #ae2a24"><b>DELETE</b></td>
        <td><i>/api/configurations/{propertyId}</i></td>
        <td>Deleting a configuration property by its UUID identifier</td>
        <td>-</td>
        <td>refresh</td>
        <td><b>PropertyReturnDto</b></td>
      </tr>
      <tr>
        <td style="color: #168541"><b>POST</b></td>
        <td><i>/api/configurations/refresh/{destination}</i></td>
        <td>Sending a message to the target service to update its configuration properties</td>
        <td>-</td>
        <td>-</td>
        <td>-</td>
      </tr>
      <tr>
        <td style="color: #168541"><b>POST</b></td>
        <td><i>/api/configurations/refresh</i></td>
        <td>Sending a message to services to update their configuration properties</td>
        <td><b>List&lt;String&gt;</b></td>
        <td>-</td>
        <td>-</td>
      </tr>
    </tbody>
</table>

### ❗ Requirements
___

* **JDK**: 18 and higher;
* **Docker**: postgres + kafka;

### 📋 Start-up instructions
___

*All commands are executed in the console. For all actions, you can use the IDE, but who knows about it — and
will do it, the instruction is not needed.*

<details style="margin-left: 40px">	
   <summary><b>Clone Repository</b></summary>

1. Create a folder: `mkdir GitProjects` (the folder name can be anything, but you will need to continue to use only it);
2. Go to the folder: `cd GitProjects`;
3. Cloning a repository: `git clone https://github.com/HogwartsSchoolOfMagic/ConfigurationService.git`;
4. Go to the folder: `cd ConfigurationService`;
5. Complete.

</details>

<details style="margin-left: 40px">	
   <summary><b>Build project</b></summary>

Inside the folder: <code>ConfigurationService</code>, you need to run the command: <code>mvn clean install</code>.
</details>

<details style="margin-left: 40px">	
   <summary><b>Database Setup</b></summary>

*After cloning the repository — we should already be in the project folder.*
1. For docker to work, you need to <b><a href="https://docs.docker.com/engine/install/">install</a></b> it first;
2. To configure the database required for the application to work, go to the folder: `cd docker\postgres`;
3. Run the .bat file with the command: `postgres.bat`. **Default port: 5024**.
</details>

<details style="margin-left: 40px">	
   <summary><b>Application launch</b></summary>

After building the application, execute the command: <code>mvn spring-boot:run</code>. <b>Default port: 8888</b>.
</details>

### ⚙ Includes functionality
___

- Translation of configuration properties;
- Setting up, connecting and working with the **Postgres database**;
- **Using LiquiBase** to create a database structure.

### 🔨 Technology stack
___

- Spring Boot: WEB, Data, Admin client;
- Spring Cloud: Server, Eureka client;
- Postgres;
- LiquiBase;
- Lombok;
- Mapstruct;
- Docker.

### 🎫 License

___

**[Apache License Version 2.0](https://github.com/HogwartsSchoolOfMagic/AuthorizationService/blob/master/LICENSE)**

_Copyright ©2022, Vladislav [[SmithyVL]](https://github.com/SmithyVL) Kuznetsov_