# Part 1: Setup & Project Creation

## Table of Contents

1. [What Is Spring Boot, and Why Not Just Spring?](#1-what-is-spring-boot-and-why-not-just-spring)
2. [Spring Boot Architecture at a Glance](#2-spring-boot-architecture-at-a-glance)
3. [What We're Building](#3-what-were-building)
4. [Requirements: Java 25 and SDKMAN!](#4-requirements-java-25-and-sdkman)
5. [Creating the Project](#5-creating-the-project)
6. [The Generated Folder Structure](#6-the-generated-folder-structure)
7. [Running the Dev Server](#7-running-the-dev-server)
8. [Beginner Pitfalls](#8-beginner-pitfalls)

---

## 1. What Is Spring Boot, and Why Not Just Spring?

The **Spring Framework** gives Java a dependency-injection container and a web
layer (Spring MVC), and it is extremely configurable — historically, that meant a
lot of XML or Java configuration before anything ran.

**Spring Boot** is Spring with the configuration done for you. It picks sensible
defaults from what is on your classpath, bundles an embedded web server
(Tomcat), and groups dependencies into **starters** — one line in your build
file brings in a whole, version-matched toolkit. You still write ordinary Spring
code; Boot just removes the setup.

## 2. Spring Boot Architecture at a Glance

Every Spring Boot web app is built from the same few building blocks:

| Building block | Job |
| --- | --- |
| **Bean** | Any object the Spring container creates and manages for you |
| **Controller** | Receives HTTP requests, returns HTTP responses |
| **Service** | Holds business logic |
| **Repository** | Talks to the database |
| **Dependency Injection** | Spring constructs your classes and passes in what they need |

You'll meet each of these properly in later parts. For now, just know the
request always flows the same direction: **Controller → Service → Repository**,
never the other way around.

## 3. What We're Building

A **Task Management REST API** — a small but real backend with five
routes:

```text
GET    /tasks
GET    /tasks/{id}
POST   /tasks
PATCH  /tasks/{id}
DELETE /tasks/{id}
```

Every part in this crash course adds one layer to the same project. By
[Part 9](09-architecture-review.md) it's validated, backed by a real PostgreSQL
database, and documented with Swagger UI.

## 4. Requirements: Java 25 and SDKMAN!

You need **JDK 25** (`java -version` to check). There's no separate build tool
to install — the project ships its own **Gradle Wrapper** (`./gradlew`), which
downloads the right Gradle version the first time you use it.

The recommended way to install Java is **SDKMAN!**, rather than an OS package
manager or a download page. SDKMAN! lets you install several JDKs side by side
and switch between them per project — useful the moment a second project needs a
different Java version.

```bash
curl -s "https://get.sdkman.io" | bash
```

Restart your terminal, then list and install a JDK 25 build:

```bash
sdk list java
sdk install java 25-tem
java -version
```

> **Note:** `25-tem` is the Eclipse Temurin build; any JDK 25 works. On Windows,
> use WSL for SDKMAN!, or install a JDK 25 from [adoptium.net](https://adoptium.net).

You'll also need **Docker** from [Part 6](06-database-integration.md) onward, for
the database.

## 5. Creating the Project

Open [start.spring.io](https://start.spring.io) — the **Spring Initializr** — and
choose:

| Field | Value |
| --- | --- |
| Project | Gradle - Groovy |
| Language | Java |
| Spring Boot | 4.1.2 (or the nearest stable 4.x — never a SNAPSHOT) |
| Group | `com.example` |
| Artifact / Name | `task-management-api` |
| Package name | `com.example.taskapi` |
| Packaging | Jar |
| Java | 25 |
| Dependencies | **Spring Web** |

Click **Generate**, unzip the download, and open the folder in your editor. We'll
add the other dependencies (validation, database, docs) in the parts that need
them.

## 6. The Generated Folder Structure

```text
task-management-api/
├── build.gradle
├── settings.gradle
├── gradlew / gradlew.bat
├── gradle/wrapper/
└── src/
    ├── main/
    │   ├── java/com/example/taskapi/
    │   │   └── TaskManagementApiApplication.java
    │   └── resources/
    │       └── application.properties
    └── test/
```

Four files matter most right now:

- **`TaskManagementApiApplication.java`** — the entry point. Creates the Spring
  container and starts the web server.
- **`build.gradle`** — your dependencies and Java version. The Spring Boot Gradle
  plugin supplies the version numbers, which is why dependency lines don't carry one.
- **`application.properties`** — configuration (database URL, port, …). Nearly empty for now.
- **`gradlew`** — the Gradle Wrapper. Always run Gradle through it, never a globally
  installed `gradle`.

```java
// src/main/java/com/example/taskapi/TaskManagementApiApplication.java
@SpringBootApplication
public class TaskManagementApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskManagementApiApplication.class, args);
    }
}
```

`SpringApplication.run(...)` is the whole story of how a Spring Boot app starts:
it creates the container, scans this class's package (and everything beneath it)
for classes to manage, configures what's on the classpath, and starts the embedded
server. `@SpringBootApplication` is what switches all of that on.

## 7. Running the Dev Server

```bash
cd task-management-api
./gradlew bootRun
```

The first run downloads Gradle and your dependencies, so give it a minute. When you
see `Started TaskManagementApiApplication`, the server is up. Visit
`http://localhost:8080` — you'll see an error page (a *Whitelabel*-style 404),
which is correct: the app is running but has no routes yet. You'll add the first
one in [Part 3](03-controllers-and-routing.md).

Stop it with `Ctrl+C` and re-run after code changes for now.

## 8. Beginner Pitfalls

- **Running with the wrong Java.** `release version 25 not supported` or *Cannot
  find a Java installation matching languageVersion=25* means no JDK 25 is active in
  this terminal. Check `java -version` and `sdk use java 25-tem`.
- **Editing the application class to add routes.** It only bootstraps the app —
  routes belong in controllers ([Part 3](03-controllers-and-routing.md)), never here.
- **Port already in use.** If `8080` is taken, stop that process or start on another
  port: `./gradlew bootRun --args='--server.port=8081'`.

**Official docs:**
- [Spring Initializr](https://start.spring.io)
- [Getting started with Spring Boot](https://docs.spring.io/spring-boot/reference/using/index.html)

---

Next: [Part 2 — Packages & Beans](02-packages-and-beans.md)
