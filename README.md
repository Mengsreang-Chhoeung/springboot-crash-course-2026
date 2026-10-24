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

1. [Setup & Project Creation](docs/01-setup-and-project-creation.md)
2. [Packages & Beans](docs/02-packages-and-beans.md)
3. [Controllers & Routing](docs/03-controllers-and-routing.md)
4. [Services & Dependency Injection](docs/04-services-and-dependency-injection.md)
5. [DTOs & Validation](docs/05-dto-and-validation.md)
6. [Database Integration (Spring Data JPA + PostgreSQL)](docs/06-database-integration.md)
7. [Error Handling](docs/07-error-handling.md)
8. [API Documentation with Swagger](docs/08-api-documentation-swagger.md)
9. [Architecture Review](docs/09-architecture-review.md)

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
