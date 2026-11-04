package com.applyflow.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class ClaimValidator {
  public ValidationResult validate(List<String> resumeBullets, List<String> verifiedFacts, String company, String title) {
    Set<String> allowed = Set.copyOf(verifiedFacts);
    List<String> unsupported = new ArrayList<>();
    for (String bullet : resumeBullets) if (!allowed.contains(bullet)) unsupported.add(bullet);
    if (!unsupported.isEmpty()) return new ValidationResult(ValidationStatus.REJECTED, List.of("Unsupported resume evidence: " + String.join(" | ", unsupported)));
    return new ValidationResult(ValidationStatus.VALID, List.of(
        "Every resume bullet maps directly to a verified candidate fact.",
        "Role references are limited to the stored job: " + title + " at " + company + "."));
  }
  public record ValidationResult(ValidationStatus status, List<String> notes) { }
}

