package com.applyflow.job;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "job_requirements")
public class JobRequirement {
  @Id @GeneratedValue @UuidGenerator private UUID id;
  @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "job_id", nullable = false) private JobPosting job;
  @Column(nullable = false, length = 80) private String category;
  @Column(nullable = false, columnDefinition = "TEXT") private String requirement;
  @Column(nullable = false) private boolean mandatory;
  protected JobRequirement() { }
  JobRequirement(JobPosting job, String category, String requirement, boolean mandatory) {
    this.job = job; this.category = category; this.requirement = requirement; this.mandatory = mandatory;
  }
  public UUID getId() { return id; }
  public String getCategory() { return category; }
  public String getRequirement() { return requirement; }
  public boolean isMandatory() { return mandatory; }
}

