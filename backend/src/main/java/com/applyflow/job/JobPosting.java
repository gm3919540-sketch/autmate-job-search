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
@Table(name = "jobs")
public class JobPosting {
  @Id @GeneratedValue @UuidGenerator private UUID id;
  @Column(nullable = false, length = 80) private String source;
  @Column(nullable = false, length = 320) private String company;
  @Column(nullable = false, length = 320) private String title;
  @Column(nullable = false, length = 320) private String location;
  @Column(nullable = false, columnDefinition = "TEXT") private String description;
  @Column(nullable = false, length = 2048) private String url;
  @Column(nullable = false) private Instant createdAt;

  protected JobPosting() { }
  JobPosting(String source, String company, String title, String location, String description, String url) {
    this.source = source; this.company = company; this.title = title; this.location = location;
    this.description = description; this.url = url; this.createdAt = Instant.now();
  }
  public UUID getId() { return id; }
  public String getSource() { return source; }
  public String getCompany() { return company; }
  public String getTitle() { return title; }
  public String getLocation() { return location; }
  public String getDescription() { return description; }
  public String getUrl() { return url; }
  public Instant getCreatedAt() { return createdAt; }
}

