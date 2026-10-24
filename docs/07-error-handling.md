# Part 7: Error Handling

## Table of Contents

1. [Why Not Just Return `null`](#1-why-not-just-return-null)
2. [`ResponseStatusException`](#2-responsestatusexception)
3. [Using It in `TaskService`](#3-using-it-in-taskservice)
4. [Problem Details Responses](#4-problem-details-responses)
5. [Trying It](#5-trying-it)
6. [Beginner Pitfalls](#6-beginner-pitfalls)

---

## 1. Why Not Just Return `null`

```text
GET /tasks/999
```

If task `999` doesn't exist, silently returning `null` with a `200 OK` forces every
caller to remember to check, and gives no hint *why* there's no data. (Right now
`orElseThrow()` with no message is even worse: it produces a `500`, which says "the
server is broken" when the truth is "that id isn't there".) An HTTP API has a better
tool: **status codes**.

## 2. `ResponseStatusException`

Spring's built-in way to say "stop here and answer with this status" is to throw a
`ResponseStatusException`. Throw it from anywhere in a request — controller or service
— and Spring turns it into the matching HTTP response.

| Status | Constant | Use for |
| --- | --- | --- |
| 400 | `HttpStatus.BAD_REQUEST` | Malformed input the caller sent |
| 404 | `HttpStatus.NOT_FOUND` | Nothing at that id |
| 409 | `HttpStatus.CONFLICT` | The request clashes with current state (a duplicate title) |

> **Note:** bigger apps usually define their own exception classes and map them all in
> one `@RestControllerAdvice`. For one small API, `ResponseStatusException` is enough.

## 3. Using It in `TaskService`

Replace the bare `orElseThrow()` and add the duplicate check:

```java
private Task getTask(Long id) {
    return taskRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task " + id + " not found"));
}

private void ensureTitleIsFree(String title) {
    if (taskRepository.existsByTitle(title)) {
        throw new ResponseStatusException(HttpStatus.CONFLICT, "A task titled '" + title + "' already exists");
    }
}
```

Call `ensureTitleIsFree(request.title())` at the top of `create`, and in `update` only
when the title is actually changing:

```java
if (request.title() != null && !request.title().equals(task.getTitle())) {
    ensureTitleIsFree(request.title());
    task.setTitle(request.title());
}
```

The database's `UNIQUE` constraint on `title` is the real guarantee; this check just
turns the common case into a friendly `409` instead of a database error.

## 4. Problem Details Responses

By default Spring hides the exception message from error bodies. Turn on **problem
details** — a standard JSON error format (RFC 9457) — so the message reaches the caller:

```properties
# src/main/resources/application.properties
spring.mvc.problemdetails.enabled=true
```

## 5. Trying It

```bash
curl -i http://localhost:8080/tasks/999
curl -i -X POST http://localhost:8080/tasks -H "Content-Type: application/json" \
  -d '{"title":"Persisted task"}'
curl -i -X POST http://localhost:8080/tasks -H "Content-Type: application/json" \
  -d '{"title":"Persisted task"}'
```

The first returns `404` with:

```json
{
  "detail": "Task 999 not found",
  "instance": "/tasks/999",
  "status": 404,
  "title": "Not Found"
}
```

Posting the same title twice returns `201`, then `409`.

## 6. Beginner Pitfalls

- **Returning `null` or an empty body for "not found".** The caller gets `200` and has
  to guess. Throw, and let the status code do the talking.
- **Letting an unexpected exception escape as a `500`.** `500` should mean *our* bug.
  A missing record or a duplicate is the caller's situation — use `404` / `409`.
- **Using `400` for everything.** The status code is part of the API: `404` means "no
  such thing", `409` means "clashes with existing data", `400` means "your input is
  malformed".

**Official docs:**
- [Error responses (Spring MVC)](https://docs.spring.io/spring-framework/reference/web/webmvc/mvc-ann-rest-exceptions.html)
- [Error handling in Spring Boot](https://docs.spring.io/spring-boot/reference/web/servlet.html#web.servlet.spring-mvc.error-handling)

---

Previous: [Part 6 — Database Integration](06-database-integration.md) ·
Next: [Part 8 — API Documentation with Swagger](08-api-documentation-swagger.md)
