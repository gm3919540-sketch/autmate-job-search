package com.applyflow.job;

import com.applyflow.profile.ProfileService;
import com.applyflow.profile.ProfileViews;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class JobMatchService {
  private final JobService jobs;
  private final ProfileService profiles;
  private final JobMatchRepository matches;
  public JobMatchService(JobService jobs, ProfileService profiles, JobMatchRepository matches) { this.jobs = jobs; this.profiles = profiles; this.matches = matches; }
  public JobViews.Match match(UUID jobId, UUID profileId) {
    ProfileViews.Profile profile = profiles.get(profileId);
    List<JobRequirement> requirements = jobs.requirements(jobId);
    String evidence = (profile.summary() + " " + profile.facts().stream().filter(ProfileViews.Fact::verified).map(ProfileViews.Fact::factText).reduce("", (left, right) -> left + " " + right)).toLowerCase(Locale.ROOT);
    List<String> matched = new ArrayList<>();
    List<String> gaps = new ArrayList<>();
    for (JobRequirement requirement : requirements) {
      if (evidence.contains(requirement.getRequirement().toLowerCase(Locale.ROOT))) matched.add(requirement.getRequirement());
      else gaps.add(requirement.getRequirement());
    }
    boolean hardEligible = requirements.stream().filter(JobRequirement::isMandatory).allMatch(item -> matched.contains(item.getRequirement()));
    int score = requirements.isEmpty() ? 0 : Math.round((matched.size() * 100.0f) / requirements.size());
    String explanation = hardEligible ? "Mandatory requirements are supported by verified evidence." : "One or more mandatory requirements lack verified evidence.";
    JobMatch saved = matches.save(new JobMatch(jobId, profileId, score, hardEligible, explanation));
    return new JobViews.Match(saved.getId(), saved.getScore(), saved.isHardEligible(), matched, gaps, saved.getExplanation(), saved.getCreatedAt());
  }
}

