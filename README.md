# Spring Boot Application productApi

[live url](https://touristspotrest.herokuapp.com/)

## Built With

*   [Kotlin](https://kotlinlang.org/) - Programming Language
* 	[Gradle](https://gradle.org/) - Dependency Management
* 	[JDK](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) - Java™ Platform, Standard Edition Development Kit 
* 	[Spring Boot](https://spring.io/projects/spring-boot) - Framework to ease the bootstrapping and development of new Spring Applications
* 	[MongoDB](https://www.mongodb.com/) - The database for modern applications
*   [Embedded MongoDB](https://github.com/michaelmosmann/embedmongo.flapdoodle.de) - Used to tests
* 	[git](https://git-scm.com/) - Free and Open-Source distributed version control system 
* 	[Swagger](https://swagger.io/) - Open-Source software framework backed by a large ecosystem of tools that helps developers design, build, document, and consume RESTful Web services.
* 	[JUnit 5](https://junit.org/junit5/) - Open-Source software friendly testing framework for Java

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.restapi.touristspot.TouristspotApplication.kt` class from your IDE.

- Install or get a mongodb image from docker
- Download the zip or clone the Git repository.
- Unzip the zip file (if you downloaded one)
- Open Command Prompt and Change directory (cd) to folder containing pom.xml
- Open Intellij or Eclipse 
   - File -> Import -> Existing Gradle Project -> Navigate to the folder where you unzipped the zip
   - Select the project
- Choose the Spring Boot Application file (search for @SpringBootApplication)
- Right Click on the file and Run as Java Application

Alternatively you can use the [Spring Boot Gradle plugin](https://docs.spring.io/spring-boot/docs/current/gradle-plugin/reference/html/) like so:

```shell
gradlew bootRun
```

### OAuth2

```
implementation("org.springframework.security.oauth:spring-security-oauth2:2.2.6.RELEASE")
```

### Create a User
- `Method` - POST
- `URL` - localhost:8080/users/signup
- `Request body` -
```json
{
    "id": null,
    "name": "Demys Cota de Lima",
    "email": "demys_dcl@hotmail.com",
    "password": "456"
}
```
#### Request token
- `Method` - POST
- `URL` - http://localhost:8080/oauth/token?grant_type=password&username={username}&password={password}
- `Response` - 
```json
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
implementation("org.springframework.boot:spring-boot-starter-security")
```

Automated dependency updates done via [Dependabot](https://dependabot.com/)

### URLs

|  URL |  Method |
|----------|--------------|
|`http://localhost:8080/categories`                                  | GET |
|`http://localhost:8080/categories`                                  | POST |
|`http://localhost:8080/favorites`                                   | GET |
|`http://localhost:8080/favorites/{id}`                              | DELETE |
|`http://localhost:8080/spot`                                        | GET |
|`http://localhost:8080/spot`                                        | POST |
|`http://localhost:8080/spot/{id}/comments`                          | GET | 
|`http://localhost:8080/spot/{id}/comments`                          | POST | 
|`http://localhost:8080/spot/{id}/pictures`                          | POST |
|`http://localhost:8080/spot/{id}/pictures/{pictureId}`              | DELETE | 
|`http://localhost:8080/spots/{id}/upvote`                           | PUT | 
|`http://localhost:8080/spots/createdBy`                             | GET | 
|`http://localhost:8080/spots/near?longitude={lon}&latitude={lat}`   | GET | 
|`http://localhost:8080/users/loout`                                 | GET | 
|`http://localhost:8080/users/main`                                  | GET |
|`http://localhost:8080/users/signup`                                | POST |

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
│       └── kotlin
│           ├── com.restapi.touristspot
│           ├── com.restapi.touristspot.config
│           ├── com.restapi.touristspot.domain.category
│           ├── com.restapi.touristspot.domain.comment
│           ├── com.restapi.touristspot.domain.favorite
│           ├── com.restapi.touristspot.domain.picture
│           ├── com.restapi.touristspot.domain.spot
│           ├── com.restapi.touristspot.expection
│           ├── com.restapi.touristspot.security
│           └── com.restapi.touristspot.util
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
│       └── kotlin
│           ├── com.restapi.touristspot
│           ├── com.restapi.touristspot.domain.category
│           ├── com.restapi.touristspot.domain.favorite
│           ├── com.restapi.touristspot.domain.spot
│           └── com.restapi.touristspot.context.user
│
├── JRE System Library
├── Gradle Dependencies
├── src
├── .dockerignore
├── .gitignore
├── build
├── gradlew
├── gradlew.bat
├── HELP.md
├── README.md
├── settings.gradle.kts
├── Tourist Spot.postman_v2.1_collection.json
├── Tourist Spot.postman_v2_collection.json
└── touristspot.iml
```

## packages

- `config` — to configure swagger and filter;
- `domain/` — to hold our project context;
- `exception` — to hold our exceptions;
- `security` — security configuration;
- `util` - utilities


- `resources/` - Contains all the static resources, templates and property files.
- `resources/application.yml` - It contains application-wide properties. Spring reads the properties defined in this file to configure your application. You can define server’s default port, server’s context path, database URLs etc, in this file.

- `test/` - contains unit and integration tests

- `build.gradle.kts` - contains all the project dependencies
