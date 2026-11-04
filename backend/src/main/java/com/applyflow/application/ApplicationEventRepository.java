package com.applyflow.application;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

interface ApplicationEventRepository extends JpaRepository<ApplicationEvent, UUID> {
  List<ApplicationEvent> findAllByApplicationIdOrderByCreatedAtAsc(UUID applicationId);
}

