# Part 5: DTOs & Validation

## Table of Contents

1. [Why Not Accept a `Map`](#1-why-not-accept-a-map)
2. [DTOs as Records](#2-dtos-as-records)
3. [Adding Validation](#3-adding-validation)
4. [Using Them in the Controller and Service](#4-using-them-in-the-controller-and-service)
5. [Trying It](#5-trying-it)
6. [Beginner Pitfalls](#6-beginner-pitfalls)

---

## 1. Why Not Accept a `Map`

Part 4's `create` takes a `Map<String, Object>` and casts. A missing `title` becomes a
`null` inside the app, a number where text was expected throws a `ClassCastException`
(a `500`), and nobody reading the signature can tell what a valid body looks like.
The fix is a **DTO** (Data Transfer Object): a class whose only job is to describe the
shape of data crossing the HTTP boundary.

## 2. DTOs as Records

In Java a **record** is the natural DTO — immutable, tiny, and the annotations go
straight on the components. Make a `dto` sub-package inside `tasks`:

```java
// src/main/java/com/example/taskapi/tasks/dto/CreateTaskRequest.java
package com.example.taskapi.tasks.dto;

public record CreateTaskRequest(String title, String description, Boolean done) {
}
```

```java
// src/main/java/com/example/taskapi/tasks/dto/UpdateTaskRequest.java
package com.example.taskapi.tasks.dto;

/** Every field is optional: a field left out of the JSON stays as it is. */
public record UpdateTaskRequest(String title, String description, Boolean done) {
}
```

`done` is `Boolean` (the object type), not `boolean`, so "not provided" is `null`
rather than silently `false`.

## 3. Adding Validation

Add the validation starter to the `dependencies { }` block of `build.gradle`:

```groovy
implementation 'org.springframework.boot:spring-boot-starter-validation'
```

Then declare the rules directly on the record components:

```java
// CreateTaskRequest.java
public record CreateTaskRequest(
    @NotBlank @Size(max = 120) String title,
    String description,
    Boolean done) {
}
```

```java
// UpdateTaskRequest.java
public record UpdateTaskRequest(
    @Size(max = 120) @Pattern(regexp = ".*\\S.*", message = "must not be blank") String title,
    String description,
    Boolean done) {
}
```

| Annotation | Rule |
| --- | --- |
| `@NotBlank` | Not `null`, not empty, not only spaces |
| `@Size(max = 120)` | At most 120 characters |
| `@Pattern(...)` | Must match a regex — here, "contains a non-space character", so an *omitted* title is allowed on update but an empty one is not |

(Imports come from `jakarta.validation.constraints`.)

## 4. Using Them in the Controller and Service

Rules do nothing until something checks them: **`@Valid`** on the controller
parameter tells Spring to validate the body before the method runs.

```java
@PostMapping
@ResponseStatus(HttpStatus.CREATED)
public Task create(@Valid @RequestBody CreateTaskRequest request) {
    return taskService.create(request.title(), request.description(),
        Boolean.TRUE.equals(request.done()));
}

@PatchMapping("/{id}")
public Task update(@PathVariable Long id, @Valid @RequestBody UpdateTaskRequest request) {
    return taskService.update(id, request).orElse(null); // proper 404 in Part 7
}
```

Add the matching `update` to `TaskService`, applying only the fields that were sent:

```java
public Optional<Task> update(Long id, UpdateTaskRequest request) {
    return findOne(id).map(task -> {
        if (request.title() != null) task.setTitle(request.title());
        if (request.description() != null) task.setDescription(request.description());
        if (request.done() != null) task.setDone(request.done());
        return task;
    });
}
```

## 5. Trying It

```bash
curl -i -X POST http://localhost:8080/tasks -H "Content-Type: application/json" \
  -d '{"title":"   "}'
curl -i -X POST http://localhost:8080/tasks -H "Content-Type: application/json" \
  -d '{"title":"Valid task"}'
curl -X PATCH http://localhost:8080/tasks/1 -H "Content-Type: application/json" \
  -d '{"done":true}'
```

The blank title is rejected with `400 Bad Request` before `TaskService` is ever
called; the valid one returns `201`; the patch changes only `done`.

> **Note:** out of the box the `400` body is short. Listing each failing field in a
> consistent error shape takes an exception handler — a topic for the full course
> beyond this crash course.

## 6. Beginner Pitfalls

- **Forgetting `@Valid`.** The annotations on the record are only declarations; without
  `@Valid` on the parameter, invalid input sails straight through.
- **Using `boolean` for an optional field.** A primitive can't be `null`, so an
  omitted field becomes `false` and an update would silently overwrite it.
- **Reusing one DTO for create and update.** Their rules differ — create requires a
  title, update makes everything optional — so keep two small records.

**Official docs:**
- [Validation](https://docs.spring.io/spring-framework/reference/core/validation/beanvalidation.html)
- [Jakarta Bean Validation constraints](https://jakarta.ee/specifications/bean-validation/3.0/jakarta-bean-validation-spec-3.0.html#builtinconstraints)

---

Previous: [Part 4 — Services & Dependency Injection](04-services-and-dependency-injection.md) ·
Next: [Part 6 — Database Integration](06-database-integration.md)
