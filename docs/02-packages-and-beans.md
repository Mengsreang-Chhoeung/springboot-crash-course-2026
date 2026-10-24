# Part 2: Packages & Beans

## Table of Contents

1. [No Modules — Packages Are the Unit](#1-no-modules--packages-are-the-unit)
2. [Component Scanning](#2-component-scanning)
3. [Creating the `tasks` Package](#3-creating-the-tasks-package)
4. [Trying It](#4-trying-it)
5. [Beginner Pitfalls](#5-beginner-pitfalls)

---

## 1. No Modules — Packages Are the Unit

Some frameworks make you declare a *module* for every feature and register it
somewhere. Spring Boot doesn't: a feature is just a **Java package**. Everything
about tasks — controller, service, repository, DTOs — lives together in one
package, and the app picks it up automatically.

This is called **package-by-feature**, and it's how this crash course (and most
real Spring apps) are organised: one package per part of the domain, not one
package per technical layer.

```text
com.example.taskapi
├── TaskManagementApiApplication.java
└── tasks/                 ← everything about tasks, together
    ├── TaskController.java
    ├── TaskService.java
    └── ...
```

## 2. Component Scanning

How does Spring find your classes? At startup, `@SpringBootApplication` scans the
application class's package **and every package beneath it**, looking for classes
annotated with **stereotype annotations**:

| Annotation | Meaning |
| --- | --- |
| `@Component` | A generic Spring-managed object (a **bean**) |
| `@Service` | A bean holding business logic |
| `@Repository` | A bean that talks to the database |
| `@RestController` | A bean that handles HTTP requests and returns JSON |

Each one it finds is instantiated once and kept in the container as a **bean**.
No registration list to maintain — put a class in the right package, annotate it,
and it exists.

> **Note:** the scan only goes *down* from the application class. A class in a
> package *outside* `com.example.taskapi` is invisible to Spring.

## 3. Creating the `tasks` Package

Create the folder `src/main/java/com/example/taskapi/tasks/`. That's all there is
to it — a package is a folder. The next two parts fill it with a controller
([Part 3](03-controllers-and-routing.md)) and a service
([Part 4](04-services-and-dependency-injection.md)).

To see scanning at work right now, add one throwaway class:

```java
// src/main/java/com/example/taskapi/tasks/Hello.java
package com.example.taskapi.tasks;

import org.springframework.stereotype.Component;

@Component
public class Hello {

    public Hello() {
        System.out.println("Hello bean created");
    }
}
```

## 4. Trying It

```bash
./gradlew bootRun
```

`Hello bean created` appears in the startup output — nobody called `new Hello()`.
Spring found the class and built it. Now move `Hello.java` into a folder *outside*
`com/example/taskapi/` (for example `com/other/`) and restart: the message
disappears, because scanning never sees it.

Delete `Hello.java` before moving on.

## 5. Beginner Pitfalls

- **Putting a class outside the application class's package.** It compiles fine and
  is silently ignored. Keep everything under `com.example.taskapi`.
- **Forgetting the stereotype annotation.** A plain class without `@Service` or
  `@Component` isn't a bean, so nothing can inject it ([Part 4](04-services-and-dependency-injection.md)).
- **Organising by layer** (`controllers/`, `services/`, `repositories/`). It looks
  tidy at five classes and becomes a scavenger hunt at fifty. Group by feature.

**Official docs:**
- [Structuring your code](https://docs.spring.io/spring-boot/reference/using/structuring-your-code.html)
- [Spring Beans and dependency injection](https://docs.spring.io/spring-boot/reference/using/spring-beans-and-dependency-injection.html)

---

Previous: [Part 1 — Setup & Project Creation](01-setup-and-project-creation.md) ·
Next: [Part 3 — Controllers & Routing](03-controllers-and-routing.md)
