package com.applyflow.job;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

interface JobPostingRepository extends JpaRepository<JobPosting, UUID> { }

