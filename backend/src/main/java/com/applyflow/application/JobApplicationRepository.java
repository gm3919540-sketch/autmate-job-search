package com.applyflow.application;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

interface JobApplicationRepository extends JpaRepository<JobApplication, UUID> {
  List<JobApplication> findAllByProfileIdOrderByCreatedAtDesc(UUID profileId);
}

