# Why This Stack

The blueprint calls for a production-minded system that is inexpensive enough for a portfolio demonstration. The central design decision is that an LLM provides language reasoning, while the backend remains the control plane.

| Technology | Why it belongs in ApplyFlow |
| --- | --- |
| React + Vite + Tailwind CSS | Builds a fast, responsive dashboard for reviewing jobs, evidence, generated documents, approvals, and application history. Vite keeps local iteration quick; Tailwind supports a consistent operations-focused interface. |
| Java + Spring Boot | Provides the dependable control plane: REST APIs, validation, transactions, authorization, resilient external clients, and workflow orchestration. It keeps business rules outside prompts. |
| Spring AI | Offers a Spring-native boundary for Gemini, structured responses, retrieval, tool calling, and model configuration. This lets providers change without rewriting the product. |
| Google Gemini API | Supplies language understanding and grounded drafting at MVP scale. Model use is reserved for semantic work after deterministic filters reduce candidates and relevant evidence has been retrieved. |
| PostgreSQL | Holds the durable, transactional source of truth: profiles, facts, jobs, applications, workflow runs, approvals, events, and audit logs. |
| pgvector | Keeps embeddings next to the verified data they represent. This gives a portfolio-scale RAG system semantic retrieval without operating a separate vector database. |
| Redis | Handles short-lived coordination: locks to prevent duplicate runs, cached job or model results, rate-limit state, and retry coordination. PostgreSQL remains durable. |
| Flyway | Version-controls database schema changes so local, test, and deployment environments evolve predictably. |
| Spring Security + JWT/OAuth2 | Protects sensitive candidate data and ensures only the right user can approve or see an application package. |
| REST APIs | Makes the boundary between React and Spring Boot clear, testable, and easy to integrate with permitted job sources or clients. |
| Spring AI tool calling | Lets the model ask for bounded capabilities such as search or evidence retrieval while Spring Boot authorizes and performs them. The model never owns unrestricted side effects. |
| MCP | Standardizes selected reusable AI-accessible tools and resources, such as job search, candidate knowledge, resume validation, and tracking. It is useful where interoperability matters, not forced onto every API. |
| JUnit + Spring Boot Test | Tests the high-risk behavior: filtering, match scoring, grounded claims, retries, idempotency, and approval gates. |
| Docker | Packages the frontend, backend, PostgreSQL/pgvector, and Redis so the demo can be reproduced without manual environment setup. |
| Git + GitHub | Keeps an auditable portfolio history, supports issues and pull requests, and makes the architecture and documentation easy for reviewers to inspect. |

## Why the demo is dependency-light

The included local demo runs without API keys, a database server, or job-board credentials. It demonstrates the most important portfolio behaviors today: verified evidence, deterministic scoring, claim validation, a human approval gate, and persistent tracking. In the production implementation, the local adapter is replaced by the stack above, with secrets provided only through environment variables.

## Why the safety rules are product features

The project does not fabricate candidate history, send secrets to a model, bypass site controls, or submit applications without approval. Those restrictions make the workflow more credible: facts stay authoritative, users retain control over consequential actions, and every important transition can be audited.

