package com.applyflow.profile;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "candidate_profiles")
public class CandidateProfile {
  @Id @GeneratedValue @UuidGenerator private UUID id;
  @Column(nullable = false, length = 320) private String displayName;
  @Column(nullable = false, columnDefinition = "TEXT") private String summary;
  @Column(nullable = false, length = 120) private String authorization;
  @Column(nullable = false) private Instant createdAt;
  @Column(nullable = false) private Instant updatedAt;

  protected CandidateProfile() { }
  CandidateProfile(String displayName, String summary, String authorization) {
    this.displayName = displayName;
    this.summary = summary;
    this.authorization = authorization;
    this.createdAt = Instant.now();
    this.updatedAt = this.createdAt;
  }
  void update(String displayName, String summary, String authorization) {
    this.displayName = displayName;
    this.summary = summary;
    this.authorization = authorization;
    this.updatedAt = Instant.now();
  }
  public UUID getId() { return id; }
  public String getDisplayName() { return displayName; }
  public String getSummary() { return summary; }
  public String getAuthorization() { return authorization; }
  public Instant getCreatedAt() { return createdAt; }
  public Instant getUpdatedAt() { return updatedAt; }
}

