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
@Table(name = "application_packages")
public class ApplicationPackage {
  @Id @GeneratedValue @UuidGenerator private UUID id;
  @Column(nullable = false) private UUID profileId;
  @Column(nullable = false) private UUID jobId;
  @Column(nullable = false, columnDefinition = "TEXT") private String resumeContent;
  @Column(nullable = false, columnDefinition = "TEXT") private String coverLetterContent;
  @Enumerated(EnumType.STRING) @Column(nullable = false, length = 32) private ValidationStatus validationStatus;
  @Column(nullable = false, columnDefinition = "TEXT") private String validationNotes;
  @Column(nullable = false) private Instant createdAt;
  protected ApplicationPackage() { }
  ApplicationPackage(UUID profileId, UUID jobId, String resumeContent, String coverLetterContent, ValidationStatus validationStatus, String validationNotes) {
    this.profileId = profileId; this.jobId = jobId; this.resumeContent = resumeContent; this.coverLetterContent = coverLetterContent;
    this.validationStatus = validationStatus; this.validationNotes = validationNotes; this.createdAt = Instant.now();
  }
  public UUID getId() { return id; }
  public UUID getProfileId() { return profileId; }
  public UUID getJobId() { return jobId; }
  public String getResumeContent() { return resumeContent; }
  public String getCoverLetterContent() { return coverLetterContent; }
  public ValidationStatus getValidationStatus() { return validationStatus; }
  public String getValidationNotes() { return validationNotes; }
  public Instant getCreatedAt() { return createdAt; }
}

