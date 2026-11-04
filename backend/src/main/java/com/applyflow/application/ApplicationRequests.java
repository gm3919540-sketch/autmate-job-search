package com.applyflow.application;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;

public final class ApplicationRequests {
  private ApplicationRequests() { }
  public record Approval(@NotNull UUID packageId, @NotBlank @Size(max = 120) String actor, @NotNull ApprovalDecision decision, @Size(max = 4000) String note) { }
}

