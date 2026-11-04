package com.applyflow.application;

import com.applyflow.job.JobService;
import com.applyflow.job.JobViews;
import com.applyflow.profile.ProfileService;
import com.applyflow.profile.ProfileViews;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ApplicationPackageService {
  private final ApplicationPackageRepository packages;
  private final ProfileService profiles;
  private final JobService jobs;
  private final ClaimValidator validator;
  public ApplicationPackageService(ApplicationPackageRepository packages, ProfileService profiles, JobService jobs, ClaimValidator validator) {
    this.packages = packages; this.profiles = profiles; this.jobs = jobs; this.validator = validator;
  }
  public ApplicationPackageView create(UUID profileId, UUID jobId) {
    ProfileViews.Profile profile = profiles.get(profileId);
    JobViews.Job job = jobs.get(jobId);
    List<String> verifiedFacts = profile.facts().stream().filter(ProfileViews.Fact::verified).map(ProfileViews.Fact::factText).toList();
    if (verifiedFacts.isEmpty()) throw new IllegalStateException("At least one verified candidate fact is required before generating an application package");
    ClaimValidator.ValidationResult validation = validator.validate(verifiedFacts, verifiedFacts, job.company(), job.title());
    String resume = String.join("\n", verifiedFacts.stream().map(fact -> "- " + fact).toList());
    String coverLetter = "Dear " + job.company() + " hiring team,\n\nI am applying for the " + job.title() + " role. The following verified experience is relevant:\n"
        + String.join("\n", verifiedFacts.stream().map(fact -> "- " + fact).toList()) + "\n\nSincerely,\n" + profile.displayName();
    ApplicationPackage created = packages.save(new ApplicationPackage(profileId, jobId, resume, coverLetter, validation.status(), String.join("\n", validation.notes())));
    return view(created);
  }
  @Transactional(readOnly = true)
  public ApplicationPackageView get(UUID id) { return view(packages.findById(id).orElseThrow(() -> new EntityNotFoundException("Application package not found"))); }
  private ApplicationPackageView view(ApplicationPackage item) {
    return new ApplicationPackageView(item.getId(), item.getProfileId(), item.getJobId(), item.getResumeContent(), item.getCoverLetterContent(), item.getValidationStatus(), item.getValidationNotes(), item.getCreatedAt());
  }
}

