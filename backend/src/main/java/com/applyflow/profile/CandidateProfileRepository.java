package com.applyflow.profile;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

interface CandidateProfileRepository extends JpaRepository<CandidateProfile, UUID> { }

