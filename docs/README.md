<!--suppress HtmlDeprecatedAttribute -->
<div align="center">
    <h1>
        <a href="https://ninjaenterprise.github.io/ConfigurationService/">ConfigurationService</a>
    </h1>
</div>

<div align="center">
    <a href="https://github.com/NinjaEnterprise/ConfigurationService/blob/master/docs/translations/README_RU.md">
        <img alt="russian-version" src="https://raw.githubusercontent.com/NinjaEnterprise/ConfigurationService/master/assets/languages/russian.png"/>
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
        <td><a href="https://github.com/NinjaEnterprise/SearchLibraryWithQueryDsl/blob/master/src/main/java/io/github/ninjaenterprise/search/model/SearchSettings.java"><b>SearchSettings</b></a></td>
        <td>-</td>
        <td><b><a href="https://github.com/NinjaEnterprise/SearchLibraryWithQueryDsl/blob/master/src/main/java/io/github/ninjaenterprise/search/model/TableResult.java">TableResult</a>&lt;PropertyReturnDto&gt;</b></td>
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
        <td><a href="https://github.com/NinjaEnterprise/SearchLibraryWithQueryDsl/blob/master/src/main/java/io/github/ninjaenterprise/search/model/SearchSettingsSimple.java"><b>SearchSettingsSimple</b></a></td>
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

* **JDK**: 17 and higher;
* **Docker**;

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
      <li>Cloning a repository: <code>git clone https://github.com/NinjaEnterprise/ConfigurationService.git</code>;</li>
      <li>Go to the folder: <code>cd ConfigurationService</code>.</li>
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

After building the application, execute the command: <code>mvn spring-boot:run</code>. <b>Default port: 8888</b>.
</details>

### ⚙ Includes functionality
___

- Translation of configuration properties;
- Setting up, connecting and working with the **Postgres database**;
- **Using LiquiBase** to create a database structure.

### 🔨 Technology stack
___

- Spring Boot: WEB, Data;
- Spring Cloud: Server, Eureka;
- Postgres;
- LiquiBase;
- Lombok;
- Mapstruct;
- Docker.

### 🎫 License
___

**[MIT](https://github.com/NinjaEnterprise/ConfigurationService/blob/master/LICENSE)**

_Copyright ©2021, Vladislav [[Bangerok]](https://github.com/Bangerok) Kuznetsov_