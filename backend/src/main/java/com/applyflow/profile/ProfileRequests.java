package com.applyflow.profile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public final class ProfileRequests {
  private ProfileRequests() { }
  public record CreateProfile(
      @NotBlank @Size(max = 320) String displayName,
      @NotBlank @Size(max = 5000) String summary,
      @NotBlank @Size(max = 120) String authorization) { }
  public record CreateFact(
      @NotBlank @Size(max = 80) String category,
      @NotBlank @Size(max = 8000) String factText,
      @NotBlank @Size(max = 255) String source) { }
}

