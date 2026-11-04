package com.applyflow.application;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "application_events")
public class ApplicationEvent {
  @Id @GeneratedValue @UuidGenerator private UUID id;
  @Column(nullable = false) private UUID applicationId;
  @Column(nullable = false, length = 80) private String eventType;
  @Column(nullable = false, columnDefinition = "TEXT") private String payload;
  @Column(nullable = false) private Instant createdAt;
  protected ApplicationEvent() { }
  ApplicationEvent(UUID applicationId, String eventType, String payload) { this.applicationId = applicationId; this.eventType = eventType; this.payload = payload; this.createdAt = Instant.now(); }
  public UUID getId() { return id; }
  public String getEventType() { return eventType; }
  public String getPayload() { return payload; }
  public Instant getCreatedAt() { return createdAt; }
}

