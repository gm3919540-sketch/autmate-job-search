package com.applyflow.application;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

interface ApplicationPackageRepository extends JpaRepository<ApplicationPackage, UUID> { }

