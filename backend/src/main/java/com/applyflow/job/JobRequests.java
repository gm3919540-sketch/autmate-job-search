package com.applyflow.job;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.List;

public final class JobRequests {
  private JobRequests() { }
  public record Requirement(@NotBlank @Size(max = 80) String category, @NotBlank @Size(max = 1000) String requirement, boolean mandatory) { }
  public record CreateJob(
      @NotBlank @Size(max = 80) String source, @NotBlank @Size(max = 320) String company,
      @NotBlank @Size(max = 320) String title, @NotBlank @Size(max = 320) String location,
      @NotBlank @Size(max = 20000) String description, @NotBlank @Size(max = 2048) String url,
      @NotEmpty List<@Valid Requirement> requirements) { }
}

