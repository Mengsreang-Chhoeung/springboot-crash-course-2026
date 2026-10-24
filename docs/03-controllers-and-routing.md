# Part 3: Controllers & Routing

## Table of Contents

1. [What a Controller Does](#1-what-a-controller-does)
2. [Mapping Routes](#2-mapping-routes)
3. [Path Variables and Request Bodies](#3-path-variables-and-request-bodies)
4. [Trying It](#4-trying-it)
5. [Beginner Pitfalls](#5-beginner-pitfalls)

---

## 1. What a Controller Does

A **controller** receives an HTTP request, hands it to something that does the real
work, and returns the HTTP response. It should stay thin — no business rules, no
database code. Right now we have nothing to hand off to, so this part returns
hard-coded data; [Part 4](04-services-and-dependency-injection.md) moves the logic
out into a service.

## 2. Mapping Routes

`@RestController` marks a class as a web controller whose return values are written
straight to the response as JSON. `@RequestMapping` sets a path prefix for the whole
class, and one annotation per HTTP method maps a route:

| Annotation | HTTP method | Route in this project |
| --- | --- | --- |
| `@GetMapping` | `GET` | `/tasks`, `/tasks/{id}` |
| `@PostMapping` | `POST` | `/tasks` |
| `@PatchMapping` | `PATCH` | `/tasks/{id}` |
| `@DeleteMapping` | `DELETE` | `/tasks/{id}` |

```java
// src/main/java/com/example/taskapi/tasks/TaskController.java
package com.example.taskapi.tasks;

import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @GetMapping
    public List<Map<String, Object>> findAll() {
        return List.of(
            Map.of("id", 1, "title", "Learn Spring Boot", "done", false),
            Map.of("id", 2, "title", "Build my first API", "done", false));
    }
}
```

Spring converts the returned `List` to JSON using Jackson — you never write
serialization code.

## 3. Path Variables and Request Bodies

The other routes need input from the request. Two annotations cover almost
everything:

- **`@PathVariable`** — a value from the URL: `/tasks/{id}` with `@PathVariable Long id`.
  Spring converts the text to a `Long` for you, and answers `400` itself if it isn't a number.
- **`@RequestBody`** — the JSON body, converted into a Java object.

Add the single-task route and a create route that just echoes what it receives:

```java
@GetMapping("/{id}")
public Map<String, Object> findOne(@PathVariable Long id) {
    return Map.of("id", id, "title", "Learn Spring Boot", "done", false);
}

@PostMapping
@ResponseStatus(HttpStatus.CREATED)
public Map<String, Object> create(@RequestBody Map<String, Object> body) {
    return body;
}
```

`@ResponseStatus(HttpStatus.CREATED)` makes a successful `POST` answer `201` instead
of the default `200`. (Add the imports your editor suggests.) `PATCH` and `DELETE`
follow the same shape and arrive for real in [Part 4](04-services-and-dependency-injection.md).

## 4. Trying It

```bash
./gradlew bootRun
```

```bash
curl http://localhost:8080/tasks
curl http://localhost:8080/tasks/7
curl -X POST http://localhost:8080/tasks -H "Content-Type: application/json" \
  -d '{"title":"Try POST"}'
curl http://localhost:8080/tasks/abc
```

The first two return JSON, the third echoes your body with status `201`, and the last
returns `400` — Spring refused to turn `abc` into a number before your code ran.

## 5. Beginner Pitfalls

- **`@Controller` instead of `@RestController`.** Plain `@Controller` treats a returned
  string as a *view name* and gives you a 404 or a template error. For a JSON API,
  always `@RestController`.
- **Forgetting `Content-Type: application/json` on `curl -X POST`.** Without it Spring
  answers `415 Unsupported Media Type`, because it doesn't know how to read the body.
- **Putting logic in the controller.** Anything beyond "take the request, call one
  method, return the result" belongs in a service ([Part 4](04-services-and-dependency-injection.md)).

**Official docs:**
- [Spring MVC controllers](https://docs.spring.io/spring-framework/reference/web/webmvc/mvc-controller.html)
- [Mapping requests](https://docs.spring.io/spring-framework/reference/web/webmvc/mvc-controller/ann-requestmapping.html)

---

Previous: [Part 2 — Packages & Beans](02-packages-and-beans.md) ·
Next: [Part 4 — Services & Dependency Injection](04-services-and-dependency-injection.md)
