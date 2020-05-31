# Spring Boot Application productApi

## Built With

*   [Kotlin](https://kotlinlang.org/) - Programming Language
* 	[Gradle](https://gradle.org/) - Dependency Management
* 	[JDK](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) - Java™ Platform, Standard Edition Development Kit 
* 	[Spring Boot](https://spring.io/projects/spring-boot) - Framework to ease the bootstrapping and development of new Spring Applications
* 	[MongoDB](https://www.mongodb.com/) - The database for modern applications
* 	[git](https://git-scm.com/) - Free and Open-Source distributed version control system 
* 	[Swagger](https://swagger.io/) - Open-Source software framework backed by a large ecosystem of tools that helps developers design, build, document, and consume RESTful Web services.
* 	[JUnit 5](https://junit.org/junit5/) - Open-Source software friendly testing framework for Java
## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.wipro.productApi.ProductApiApplication` class from your IDE.

- Download the zip or clone the Git repository.
- Unzip the zip file (if you downloaded one)
- Open Command Prompt and Change directory (cd) to folder containing pom.xml
- Open Intellij or Eclipse 
   - File -> Import -> Existing Maven Project -> Navigate to the folder where you unzipped the zip
   - Select the project
- Choose the Spring Boot Application file (search for @SpringBootApplication)
- Right Click on the file and Run as Java Application

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
gradlew bootRun
```

### OAuth2

```
implementation("org.springframework.security.oauth:spring-security-oauth2:2.2.6.RELEASE")
```

#### Request token
- `Method` - POST
- `URL` - http://localhost:8080/oauth/token?grant_type=password&username={username}&password={password}
- `Response` - 
```
{
  "access_token":"MTQ0NjJkZmQ5OTM2NDE1ZTZjNGZmZjI3",
  "token_type":"bearer",
  "expires_in":3600,
  "refresh_token":"IwOGYzYTlmM2YxOTQ5MGE3YmNmMDFkNTVk",
  "scope":"create"
}
```

#### Refresh token
- `Method` - POST
- `URL` - http://localhost:8080/oauth/token?grant_type=refresh_token&refresh_token={REFRESH_TOKEN}
- `Response` - 
```
{
  "access_token":"MTQ0NjJkZmQ5OTM2NDE1ZTZjNGZmZjI3",
  "token_type":"bearer",
  "expires_in":3600,
  "refresh_token":"IwOGYzYTlmM2YxOTQ5MGE3YmNmMDFkNTVk",
  "scope":"create"
}
```

### Security

```
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

Spring Boot Starter Security main username is `wipro` and password is `123`


Automated dependency updates done via [Dependabot](https://dependabot.com/)

### URLs

|  URL |  Method |
|----------|--------------|
|`http://localhost:8080/products`                                | POST |
|`http://localhost:8080/products/{id}`                           | GET | 
|`http://localhost:8080/products/{id}`                           | PUT | 
|`http://localhost:8080/products/{id}`                           | DELETE |
|`http://localhost:8080/products/{id}/disable`                   | PUT | 
|`http://localhost:8080/products/enable?page=1&size=10`          | GET | 
|`http://localhost:8080/products/disable?page=1&size=10`         | GET | 

## Documentation

* [Swagger](http://localhost:8080/swagger-ui.html) - Documentation & Testing

It is needed to do the authentication.
Pressed the button `Authorize` and inform username `wipro` and password `123`

## Files and Directories

The project (a.k.a. project directory) has a particular directory structure. A representative project is shown below:

```
.
├── Spring Elements
├── src
│   └── main
│       └── java
│           ├── com.wipro.productApi
│           ├── com.wipro.productApi.config
│           ├── com.wipro.productApi.context.product
│           ├── com.wipro.productApi.context.user
│           ├── com.wipro.productApi.exception
│           └── com.wipro.productApi.security
├── src
│   └── main
│       └── resources
│           └──application.yml
│           └──db
│              └──migration
│                 ├──V1__create_product_table.sql
│                 ├──V2__create_user_table.sql
│                 ├──V3__load_product_table.sql
│                 └──V4__load_user_table.sql
├── src
│   └── test
│       └── java
│           ├── com.wipro.productApi
│           ├── com.wipro.productApi.context.product
│           └── com.wipro.productApi.context.user
│
├── JRE System Library
├── Maven Dependencies
├── src
├── target
├── HELP.md
├── mvnw
├── mvnw.cmd
├── pom.xml
├── productApi.iml
└── README.md
```

## packages

- `context` — to hold our project context;
- `user` — to hold our users;
- `product` — to hold our product;
- `security` — security configuration;
- `config` — to configure swagger and filter;

- `resources/` - Contains all the static resources, templates and property files.
- `resources/application.yml` - It contains application-wide properties. Spring reads the properties defined in this file to configure your application. You can define server’s default port, server’s context path, database URLs etc, in this file.

- `test/` - contains unit and integration tests

- `pom.xml` - contains all the project dependencies
