# Task Management API — Reference Implementation

The finished app from the [Spring Boot crash course](../../README.md), in its
**Part 9 end state**: five REST routes backed by PostgreSQL through
Spring Data JPA, with Flyway migrations, DTO validation, `ResponseStatusException`
errors, and Swagger docs.

This is the reference solution — the thing your project should look like
after following all nine parts. Read the parts and build it yourself
first; use this to compare against when something doesn't work.

## Running it

You need JDK 25 (see [Part 1](../../docs/01-setup-and-project-creation.md)) and Docker.

```bash
docker compose up -d
```

```bash
./gradlew bootRun
```

The API is on `http://localhost:8080`, and Swagger UI on
`http://localhost:8080/swagger-ui.html`.

To stop the database (keeping its data):

```bash
docker compose down
```

Add `-v` to that command to also drop the volume and start from an empty
database next time.

## Routes

| Method   | Route         | Behavior                                                       |
| -------- | ------------- | -------------------------------------------------------------- |
| `GET`    | `/tasks`      | List every task                                                |
| `GET`    | `/tasks/{id}` | One task, or `404`                                             |
| `POST`   | `/tasks`      | Create a task; `400` on invalid body, `409` on duplicate title |
| `PATCH`  | `/tasks/{id}` | Partial update, or `404`                                       |
| `DELETE` | `/tasks/{id}` | Delete (`204`), or `404`                                       |

## Configuration

`DATABASE_URL`, `DATABASE_USERNAME` and `DATABASE_PASSWORD` override the defaults in
`application.properties`, which match `docker-compose.yml` (host port `5424`).
