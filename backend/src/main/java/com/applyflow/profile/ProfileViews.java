package com.applyflow.profile;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public final class ProfileViews {
  private ProfileViews() { }
  public record Fact(UUID id, String category, String factText, boolean verified, String source, Instant createdAt) { }
  public record Profile(UUID id, String displayName, String summary, String authorization, Instant createdAt, Instant updatedAt, List<Fact> facts) { }
}

