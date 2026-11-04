package com.applyflow.application;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public final class ApplicationTrackingViews {
  private ApplicationTrackingViews() { }
  public record Approval(UUID id, UUID packageId, String actor, ApprovalDecision decision, String note, Instant decidedAt) { }
  public record Event(String eventType, String payload, Instant createdAt) { }
  public record Application(UUID id, UUID packageId, UUID profileId, UUID jobId, SubmissionStatus status, Instant submittedAt, Instant createdAt, List<Event> events) { }
}

