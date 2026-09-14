package com.applyflow.workflow;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class WorkflowServiceTest {
  @Test void requiresApprovalBeforeSubmission() {
    WorkflowService service = new WorkflowService();
    service.analyze();
    assertThrows(IllegalStateException.class, service::markSubmitted);
    service.approve();
    assertEquals(WorkflowState.SUBMITTED, service.markSubmitted().state());
  }
}

