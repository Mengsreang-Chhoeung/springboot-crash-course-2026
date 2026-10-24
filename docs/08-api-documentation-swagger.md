# Part 8: API Documentation with Swagger

## Table of Contents

1. [Why Document the API](#1-why-document-the-api)
2. [Adding springdoc-openapi](#2-adding-springdoc-openapi)
3. [Describing the API](#3-describing-the-api)
4. [Annotating Controllers and DTOs](#4-annotating-controllers-and-dtos)
5. [Trying It](#5-trying-it)
6. [Beginner Pitfalls](#6-beginner-pitfalls)

---

## 1. Why Document the API

Anyone calling your API — a teammate, a frontend developer, you in three months —
needs to know the routes, the request shapes and the responses. A hand-written doc goes
stale the day someone adds a field. **springdoc-openapi** builds the documentation from
the code you already wrote — controllers, records, validation annotations — so it can't
drift, and it serves a browsable **Swagger UI** where you can try every route.

## 2. Adding springdoc-openapi

Add one dependency to `build.gradle`:

```groovy
implementation 'org.springdoc:springdoc-openapi-starter-webmvc-ui:3.1.1'
```

> **Note:** springdoc isn't managed by Spring Boot, so it carries an explicit version.
> Use the 3.x line for Spring Boot 4 (2.x is for Boot 3).

Restart the app. Two things now exist with no further code:

- **`/swagger-ui.html`** — the interactive UI.
- **`/v3/api-docs`** — the raw OpenAPI document, as JSON.

## 3. Describing the API

Give the docs a title and description with one small configuration class:

```java
// src/main/java/com/example/taskapi/config/OpenApiConfig.java
package com.example.taskapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI taskApiDocs() {
        return new OpenAPI().info(new Info()
            .title("Task Management API")
            .description("A small task-management REST API built in the Spring Boot crash course")
            .version("1.0"));
    }
}
```

A `@Configuration` class holds **`@Bean` methods**: each returns an object that Spring
registers as a bean — here, the one springdoc looks for.

## 4. Annotating Controllers and DTOs

springdoc already reads your routes and validation rules. Annotations add the human
touches — a group name, a summary per route, example values:

```java
@Tag(name = "tasks")
@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Operation(summary = "List all tasks")
    @GetMapping
    public List<TaskResponse> findAll() { ... }

    @Operation(summary = "Create a task")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponse create(@Valid @RequestBody CreateTaskRequest request) { ... }
}
```

```java
public record CreateTaskRequest(
    @Schema(example = "Learn Spring Boot") @NotBlank @Size(max = 120) String title,
    @Schema(example = "Build my first API") String description,
    @Schema(defaultValue = "false") Boolean done) {
}
```

Put `@Operation` on every route; the imports are `io.swagger.v3.oas.annotations.*`.

## 5. Trying It

```bash
./gradlew bootRun
```

Open `http://localhost:8080/swagger-ui.html`. Expand **POST /tasks**, click **Try it
out**, run it with the example body, then try **GET /tasks/{id}** with an id that doesn't
exist and watch the `404` come back. Everything you built in Parts 3–7 is exercisable
from the browser.

## 6. Beginner Pitfalls

- **Wrong springdoc version for your Spring Boot.** A 2.x release on Boot 4 fails with
  class-not-found errors at startup. Match the 3.x line to Boot 4.
- **Documenting entities.** The docs show exactly what the controller returns — which is
  why Part 6 returns records. Return an entity and your table layout is published.
- **Leaving Swagger UI public in production.** Fine for a learning project; on a real
  deployment, restrict or disable it (`springdoc.api-docs.enabled=false`).

**Official docs:**
- [springdoc-openapi](https://springdoc.org/)
- [OpenAPI specification](https://spec.openapis.org/oas/latest.html)

---

Previous: [Part 7 — Error Handling](07-error-handling.md) ·
Next: [Part 9 — Architecture Review](09-architecture-review.md)
