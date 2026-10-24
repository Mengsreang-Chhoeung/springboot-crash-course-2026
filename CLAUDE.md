# Crash Course — Scope

## Project overview

This repo is the free Spring Boot crash course — a funnel into a separate paid,
live cohort (`springboot-cohort-2026`). It is a single, continuous **1-hour build**: a Task
Management REST API, built up part by part from Spring Initializr to a
documented, validated, database-backed API with Swagger UI. It is not a
set of isolated concept demos — later parts build directly on the project
created in Part 1. This repo is fully standalone and must not reference or
depend on anything outside itself. It is the Spring Boot sibling of
`nestjs-crash-course-2026` and deliberately mirrors its structure.

The 9 parts, in build order:

1. Setup & Project Creation
2. Packages & Beans
3. Controllers & Routing
4. Services & Dependency Injection
5. DTOs & Validation
6. Database Integration (Spring Data JPA + PostgreSQL + Flyway)
7. Error Handling
8. API Documentation with Swagger (springdoc-openapi)
9. Architecture Review

Deliberately out of scope (each is its own future video, and/or shows up properly
paced inside the paid cohort): Spring Security / JWT, authorization, testing,
`@RestControllerAdvice` and custom exception hierarchies, profiles, rate limiting,
actuator, caching, messaging, Docker images, deployment, advanced JPA (relationships,
custom queries).

## Audience / framing

- Beginner: little to no prior Spring or backend production experience assumed.
  Explain concepts before naming them.
- Practical and hands-on — every part has runnable, copy-pasteable code that fits
  into the one running project, not standalone snippets.
- One project, built incrementally — `tasks` is the only domain package. No
  unrelated example domains.
- Stack: **Java 25, Spring Boot 4.1.2, Gradle (Groovy DSL, wrapper), PostgreSQL 17
  in Docker, Spring Data JPA + Flyway**, matching the paid cohort's decisions so the
  crash course previews real cohort tooling.
- Free and public-facing: works as a standalone read, mentions the paid cohort only
  briefly in the top-level README and Part 9, never inside every part.

## Structure

```
springboot-crash-course-2026/
├── CLAUDE.md
├── README.md                              # intro + table of contents
├── docs/
│   ├── 01-setup-and-project-creation.md … 09-architecture-review.md
│   └── assets/0N-slug/*.svg
└── examples/
    └── task-management-api/               # reference implementation (Part 9 end state)
```

`examples/task-management-api/` is the **runnable reference implementation** — the
one project in its Part 9 end state only. Two rules follow:

- **The docs and the app must not drift.** A change in one lands in the other in the
  same pass; the snippets in a part must be copy-pasteable into the reference app.
- **It is the crash course's app.** It stays standalone.

## Content conventions

Same as the NestJS crash course: `# Part N: Title` → `## Table of Contents` → `---` →
numbered `## N. Subtopic` sections; tutorial prose (no video-script phrasing), bold
key terms, `> **Note:**` asides; a short **Beginner Pitfalls** section (2–3 items),
an **Official docs** link list, and Previous/Next links at the end (the last part
links back to the README). Diagrams are simple viewBox SVGs under `docs/assets/0N-slug/`,
only where they clarify a flow.

## What has been verified

The reference app was built and exercised end to end (all five routes, `201/400/404/409/204`,
Swagger UI, `/v3/api-docs`, Flyway `V1` on an empty database) on JDK 25 / Spring Boot 4.1.2.
The intermediate states in Parts 2–5 (in-memory service, `Map` bodies) are teaching
steps and have **not** been compiled separately; only the Part 6–9 end state is verified.

## Conventions shared with the cohort repo

- Commits never carry Claude attribution.
- `.env`-style secrets are gitignored; the only credentials here are throwaway local
  Docker defaults.
