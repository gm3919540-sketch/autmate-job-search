package com.applyflow.profile;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "candidate_facts")
public class CandidateFact {
  @Id @GeneratedValue @UuidGenerator private UUID id;
  @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "profile_id", nullable = false)
  private CandidateProfile profile;
  @Column(nullable = false, length = 80) private String category;
  @Column(name = "fact_text", nullable = false, columnDefinition = "TEXT") private String factText;
  @Column(nullable = false) private boolean verified;
  @Column(nullable = false, length = 255) private String source;
  @Column(nullable = false) private Instant createdAt;

  protected CandidateFact() { }
  CandidateFact(CandidateProfile profile, String category, String factText, String source) {
    this.profile = profile;
    this.category = category;
    this.factText = factText;
    this.source = source;
    this.verified = false;
    this.createdAt = Instant.now();
  }
  void verify() { this.verified = true; }
  public UUID getId() { return id; }
  public String getCategory() { return category; }
  public String getFactText() { return factText; }
  public boolean isVerified() { return verified; }
  public String getSource() { return source; }
  public Instant getCreatedAt() { return createdAt; }
}

