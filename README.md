# ApplyFlow

ApplyFlow is a local, portfolio-scale implementation of a grounded job application workflow. It turns verified candidate evidence into a reviewed application package, without inventing claims or submitting anything automatically.

## Spring Backend

- Spring Boot REST API for workflow state, analysis, human approval, and submission enforcement.
- Spring AI configuration for Google Gemini chat and embeddings, with secrets read only from environment variables.
- PostgreSQL/pgvector configuration plus Flyway migrations for verified facts and workflow runs.
- Redis configuration for rate limiting, distributed locks, caching, and short-lived workflow coordination.
- Docker Compose services for PostgreSQL with pgvector and Redis.
- JUnit coverage for the approval boundary.

## Run it

Requires Java 21, Maven, PostgreSQL with pgvector, Redis, and a Gemini API key for AI-enabled flows.

```powershell
cd backend
mvn spring-boot:run
```

Open `http://localhost:8080/api/workflows/current`.

```powershell
cd backend
mvn test
```

## Production Architecture

The service avoids credentials and job-board access until explicit permitted integrations are configured. The next backend increments are Spring Security, candidate/document persistence, vector retrieval, Gemini-backed drafting, and job-source adapters. See [WHY.md](WHY.md) and [FLOWDIAGRAM.md](FLOWDIAGRAM.md).

## Safety boundary

This project deliberately does not bypass authentication, CAPTCHA, anti-bot controls, or a job platform's terms. The user must approve a package before any consequential status can be recorded.

