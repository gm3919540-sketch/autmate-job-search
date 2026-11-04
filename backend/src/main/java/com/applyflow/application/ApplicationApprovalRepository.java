package com.applyflow.application;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

interface ApplicationApprovalRepository extends JpaRepository<ApplicationApproval, UUID> { boolean existsByPackageId(UUID packageId); }

