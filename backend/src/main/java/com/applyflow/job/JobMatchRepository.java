package com.applyflow.job;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

interface JobMatchRepository extends JpaRepository<JobMatch, UUID> { }

