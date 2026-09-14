# ApplyFlow

ApplyFlow is a local, portfolio-scale implementation of a grounded job application workflow. It turns verified candidate evidence into a reviewed application package, without inventing claims or submitting anything automatically.

## What works now

- A dashboard seeded with a realistic job and a verified candidate knowledge base.
- Deterministic workflow orchestration: analysis, fit score, evidence retrieval, package creation, validation, approval, and tracking.
- A hard approval gate: an application cannot be marked submitted before approval.
- A durable local JSON state store for demo sessions.
- Automated tests for grounding, approval, and status transitions.

## Run it

Requires Node.js 20 or later.

```powershell
npm start
```

Open `http://localhost:4173`. The runnable adapter lives in `backend/adapter`; the dashboard assets live in `frontend/public`.

```powershell
npm test
```

## Production Architecture

The runnable demo avoids credentials and job-board access. The production implementation described in the blueprint replaces the local adapter with Spring Boot, Spring AI and Gemini; PostgreSQL plus pgvector as durable fact and vector storage; Redis for locks, rate limits and short-lived coordination; React/Vite/Tailwind for the dashboard; Flyway migrations; Spring Security; Docker; and permitted job/application integrations. See [WHY.md](WHY.md) and [FLOWDIAGRAM.md](FLOWDIAGRAM.md).

## Safety boundary

This project deliberately does not bypass authentication, CAPTCHA, anti-bot controls, or a job platform's terms. The user must approve a package before any consequential status can be recorded.

