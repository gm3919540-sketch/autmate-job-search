# ApplyFlow Workflow Diagram

```mermaid
flowchart LR
  U[Candidate] --> P[Verified profile and source documents]
  P --> F[Candidate facts]
  F --> V[(PostgreSQL and pgvector)]
  U --> S[Start job discovery]
  S --> J[Permitted job search adapter]
  J --> N[Normalize and deduplicate]
  N --> H[Hard eligibility filters]
  H --> M[Hybrid match]
  M --> R[Retrieve verified evidence]
  R --> G[Generate resume cover letter and answers]
  G --> C[Claim validator]
  C --> A{Human approval}
  A -- Reject --> G
  A -- Approve --> T[Prepare permitted application action]
  T --> K[Application tracker and audit events]
  V <--> R
  V <--> K
  X[Redis locks cache rate limits] <--> J
  X <--> T
```

## State progression

`CREATED -> SEARCHING -> ANALYZING -> MATCHED -> GENERATING -> VALIDATING -> WAITING_FOR_APPROVAL -> SUBMITTING -> SUBMITTED -> TRACKING`

Recoverable problems move to `RETRYING`; unrecoverable failures move to `FAILED`. The demo implements the safe core path from `CREATED` to `WAITING_FOR_APPROVAL`, then `APPROVED` and a user-confirmed tracking update.

## Trust boundary

Only verified candidate facts are eligible as generation evidence. Generated resumes and letters are outputs, never profile truth. An LLM may request controlled tools, but the backend authorizes, executes, records, and limits every tool action.

