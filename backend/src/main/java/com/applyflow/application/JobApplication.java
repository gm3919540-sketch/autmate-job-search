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
@Table(name = "applications")
public class JobApplication {
  @Id @GeneratedValue @UuidGenerator private UUID id;
  @Column(nullable = false, unique = true) private UUID packageId;
  @Column(nullable = false) private UUID profileId;
  @Column(nullable = false) private UUID jobId;
  @Enumerated(EnumType.STRING) @Column(nullable = false, length = 32) private SubmissionStatus status;
  private Instant submittedAt;
  @Column(nullable = false) private Instant createdAt;
  protected JobApplication() { }
  JobApplication(ApplicationPackage item) {
    this.packageId = item.getId(); this.profileId = item.getProfileId(); this.jobId = item.getJobId();
    this.status = SubmissionStatus.READY_TO_SUBMIT; this.createdAt = Instant.now();
  }
  void markSubmitted() { if (status != SubmissionStatus.READY_TO_SUBMIT) throw new IllegalStateException("Only approved applications can be submitted"); status = SubmissionStatus.SUBMITTED; submittedAt = Instant.now(); }
  void updateStatus(SubmissionStatus status) { this.status = status; }
  public UUID getId() { return id; }
  public UUID getPackageId() { return packageId; }
  public UUID getProfileId() { return profileId; }
  public UUID getJobId() { return jobId; }
  public SubmissionStatus getStatus() { return status; }
  public Instant getSubmittedAt() { return submittedAt; }
  public Instant getCreatedAt() { return createdAt; }
}

