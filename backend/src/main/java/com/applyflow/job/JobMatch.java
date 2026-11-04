package com.applyflow.job;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "job_matches")
public class JobMatch {
  @Id @GeneratedValue @UuidGenerator private UUID id;
  @Column(nullable = false) private UUID jobId;
  @Column(nullable = false) private UUID profileId;
  @Column(nullable = false) private int score;
  @Column(nullable = false) private boolean hardEligible;
  @Column(nullable = false, columnDefinition = "TEXT") private String explanation;
  @Column(nullable = false) private Instant createdAt;
  protected JobMatch() { }
  JobMatch(UUID jobId, UUID profileId, int score, boolean hardEligible, String explanation) {
    this.jobId = jobId; this.profileId = profileId; this.score = score; this.hardEligible = hardEligible;
    this.explanation = explanation; this.createdAt = Instant.now();
  }
  public UUID getId() { return id; }
  public int getScore() { return score; }
  public boolean isHardEligible() { return hardEligible; }
  public String getExplanation() { return explanation; }
  public Instant getCreatedAt() { return createdAt; }
}

