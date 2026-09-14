package com.applyflow.workflow;

import java.time.Instant;
import java.util.List;

public record WorkflowSnapshot(
    String id, WorkflowState state, ApplicationStatus applicationStatus, int fitScore,
    List<String> evidence, List<String> validation, boolean approved, Instant updatedAt) { }

