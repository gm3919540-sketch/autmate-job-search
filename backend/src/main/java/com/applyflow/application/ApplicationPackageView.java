package com.applyflow.application;

import java.time.Instant;
import java.util.UUID;

public record ApplicationPackageView(UUID id, UUID profileId, UUID jobId, String resumeContent, String coverLetterContent, ValidationStatus validationStatus, String validationNotes, Instant createdAt) { }

