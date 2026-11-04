package com.applyflow.job;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public final class JobViews {
  private JobViews() { }
  public record Requirement(UUID id, String category, String requirement, boolean mandatory) { }
  public record Job(UUID id, String source, String company, String title, String location, String description, String url, Instant createdAt, List<Requirement> requirements) { }
  public record Match(UUID id, int score, boolean hardEligible, List<String> matchedRequirements, List<String> gaps, String explanation, Instant createdAt) { }
}

