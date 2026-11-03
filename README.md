# Spring Boot Crash Course — Build a REST API in 1 Hour

This is a free, self-contained crash course that builds one complete
project from start to finish: a **Task Management REST API**, using
Spring Boot, PostgreSQL, and Spring Data JPA. By the end you'll have a
working, validated, documented API — and understand the relationship between
**Controller → Service → Repository → PostgreSQL**.

It's for developers who are brand new to Spring Boot, or who've only skimmed
the [official docs](https://docs.spring.io/spring-boot/). No prior production
backend experience assumed.

## Table of Contents

The written course lives at [https://mengsreang-chhoeung.work/tutorials/springboot-crash-course](https://mengsreang-chhoeung.work/tutorials/springboot-crash-course). The nine parts:

1. [Setup & Project Creation](https://mengsreang-chhoeung.work/tutorials/springboot-crash-course)
2. [Packages & Beans](https://mengsreang-chhoeung.work/tutorials/springboot-crash-course)
3. [Controllers & Routing](https://mengsreang-chhoeung.work/tutorials/springboot-crash-course)
4. [Services & Dependency Injection](https://mengsreang-chhoeung.work/tutorials/springboot-crash-course)
5. [DTOs & Validation](https://mengsreang-chhoeung.work/tutorials/springboot-crash-course)
6. [Database Integration (Spring Data JPA + PostgreSQL)](https://mengsreang-chhoeung.work/tutorials/springboot-crash-course)
7. [Error Handling](https://mengsreang-chhoeung.work/tutorials/springboot-crash-course)
8. [API Documentation with Swagger](https://mengsreang-chhoeung.work/tutorials/springboot-crash-course)
9. [Architecture Review](https://mengsreang-chhoeung.work/tutorials/springboot-crash-course)

## What you'll build

```text
GET    /tasks
GET    /tasks/{id}
POST   /tasks
PATCH  /tasks/{id}
DELETE /tasks/{id}
```

A single `tasks` feature package, backed by a real PostgreSQL database via
Spring Data JPA and Flyway migrations, with request validation, proper HTTP
error responses, and a live Swagger UI to try it all from the browser.

## What you need

- **JDK 25** (installed with [SDKMAN!](https://sdkman.io) in Part 1)
- **Docker**, for PostgreSQL (from Part 6)
- No build tool to install — the project uses the Gradle Wrapper (`./gradlew`)

## The finished code

[`examples/task-management-api/`](examples/task-management-api/README.md)
holds the complete project in its Part 9 end state, ready to run. Build it
yourself by following the parts in order — then use that folder to
compare against when something doesn't line up.

## Learn more

This crash course covers the ground you need for one working API, not
the whole framework. For everything beyond that — security, testing,
deployment and more — see the
[official Spring Boot documentation](https://docs.spring.io/spring-boot/). A
paid, live cohort goes deeper by building a full production-style e-commerce API.
