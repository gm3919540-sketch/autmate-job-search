package com.applyflow.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import org.junit.jupiter.api.Test;

class ClaimValidatorTest {
  private final ClaimValidator validator = new ClaimValidator();
  @Test void acceptsOnlyVerifiedEvidence() {
    var result = validator.validate(List.of("Built a Java service"), List.of("Built a Java service"), "Northstar", "Engineer");
    assertEquals(ValidationStatus.VALID, result.status());
  }
  @Test void rejectsUnsupportedEvidence() {
    var result = validator.validate(List.of("Built a Java service", "Invented a certification"), List.of("Built a Java service"), "Northstar", "Engineer");
    assertEquals(ValidationStatus.REJECTED, result.status());
  }
}

