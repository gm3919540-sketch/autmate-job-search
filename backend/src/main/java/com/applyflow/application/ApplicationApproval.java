package com.applyflow.application;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "approvals")
public class ApplicationApproval {
  @Id @GeneratedValue @UuidGenerator private UUID id;
  @Column(nullable = false, unique = true) private UUID packageId;
  @Column(nullable = false, length = 120) private String actor;
  @Enumerated(EnumType.STRING) @Column(nullable = false, length = 32) private ApprovalDecision decision;
  @Column(nullable = false, columnDefinition = "TEXT") private String note;
  @Column(nullable = false) private Instant decidedAt;
  protected ApplicationApproval() { }
  ApplicationApproval(UUID packageId, String actor, ApprovalDecision decision, String note) {
    this.packageId = packageId; this.actor = actor; this.decision = decision; this.note = note; this.decidedAt = Instant.now();
  }
  public UUID getId() { return id; }
  public UUID getPackageId() { return packageId; }
  public String getActor() { return actor; }
  public ApprovalDecision getDecision() { return decision; }
  public String getNote() { return note; }
  public Instant getDecidedAt() { return decidedAt; }
}

