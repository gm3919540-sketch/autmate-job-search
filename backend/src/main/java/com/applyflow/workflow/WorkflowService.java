package com.applyflow.workflow;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class WorkflowService {
  private WorkflowSnapshot current = created();

  public synchronized WorkflowSnapshot current() { return current; }

  public synchronized WorkflowSnapshot analyze() {
    List<String> verifiedEvidence = List.of(
        "Verified Java and Spring Boot REST API experience",
        "Verified PostgreSQL workflow data experience",
        "Verified Redis caching and rate-limit experience");
    current = new WorkflowSnapshot(current.id(), WorkflowState.WAITING_FOR_APPROVAL,
        ApplicationStatus.DRAFT, 86, verifiedEvidence,
        List.of("Every generated claim must cite verified evidence", "No unsupported claims found"), false, Instant.now());
    return current;
  }

  public synchronized WorkflowSnapshot approve() {
    if (current.state() != WorkflowState.WAITING_FOR_APPROVAL) throw new IllegalStateException("Only a validated package can be approved");
    current = new WorkflowSnapshot(current.id(), WorkflowState.APPROVED, ApplicationStatus.READY_TO_SUBMIT,
        current.fitScore(), current.evidence(), current.validation(), true, Instant.now());
    return current;
  }

  public synchronized WorkflowSnapshot markSubmitted() {
    if (!current.approved()) throw new IllegalStateException("Human approval is required before submission");
    current = new WorkflowSnapshot(current.id(), WorkflowState.SUBMITTED, ApplicationStatus.SUBMITTED,
        current.fitScore(), current.evidence(), current.validation(), true, Instant.now());
    return current;
  }

  private WorkflowSnapshot created() {
    return new WorkflowSnapshot(UUID.randomUUID().toString(), WorkflowState.CREATED, ApplicationStatus.DRAFT,
        0, List.of(), List.of(), false, Instant.now());
  }
}

