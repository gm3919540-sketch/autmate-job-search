package com.applyflow.profile;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

interface CandidateFactRepository extends JpaRepository<CandidateFact, UUID> {
  List<CandidateFact> findAllByProfileIdOrderByCreatedAtDesc(UUID profileId);
}

