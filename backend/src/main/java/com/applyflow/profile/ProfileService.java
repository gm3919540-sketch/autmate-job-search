package com.applyflow.profile;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProfileService {
  private final CandidateProfileRepository profiles;
  private final CandidateFactRepository facts;
  public ProfileService(CandidateProfileRepository profiles, CandidateFactRepository facts) {
    this.profiles = profiles;
    this.facts = facts;
  }
  public ProfileViews.Profile create(ProfileRequests.CreateProfile request) {
    return view(profiles.save(new CandidateProfile(request.displayName(), request.summary(), request.authorization())));
  }
  @Transactional(readOnly = true)
  public ProfileViews.Profile get(UUID id) { return view(profile(id)); }
  public ProfileViews.Fact addFact(UUID profileId, ProfileRequests.CreateFact request) {
    CandidateFact fact = facts.save(new CandidateFact(profile(profileId), request.category(), request.factText(), request.source()));
    return factView(fact);
  }
  public ProfileViews.Fact verifyFact(UUID profileId, UUID factId) {
    CandidateFact fact = facts.findById(factId).orElseThrow(() -> new EntityNotFoundException("Candidate fact not found"));
    if (!facts.findAllByProfileIdOrderByCreatedAtDesc(profileId).stream().anyMatch(item -> item.getId().equals(factId))) {
      throw new EntityNotFoundException("Candidate fact not found for profile");
    }
    fact.verify();
    return factView(fact);
  }
  private CandidateProfile profile(UUID id) { return profiles.findById(id).orElseThrow(() -> new EntityNotFoundException("Candidate profile not found")); }
  private ProfileViews.Profile view(CandidateProfile profile) {
    List<ProfileViews.Fact> profileFacts = facts.findAllByProfileIdOrderByCreatedAtDesc(profile.getId()).stream().map(this::factView).toList();
    return new ProfileViews.Profile(profile.getId(), profile.getDisplayName(), profile.getSummary(), profile.getAuthorization(), profile.getCreatedAt(), profile.getUpdatedAt(), profileFacts);
  }
  private ProfileViews.Fact factView(CandidateFact fact) {
    return new ProfileViews.Fact(fact.getId(), fact.getCategory(), fact.getFactText(), fact.isVerified(), fact.getSource(), fact.getCreatedAt());
  }
}

