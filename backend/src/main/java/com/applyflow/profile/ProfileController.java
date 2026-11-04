package com.applyflow.profile;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {
  private final ProfileService profiles;
  public ProfileController(ProfileService profiles) { this.profiles = profiles; }
  @PostMapping @ResponseStatus(HttpStatus.CREATED)
  public ProfileViews.Profile create(@Valid @RequestBody ProfileRequests.CreateProfile request) { return profiles.create(request); }
  @GetMapping("/{profileId}")
  public ProfileViews.Profile get(@PathVariable UUID profileId) { return profiles.get(profileId); }
  @PostMapping("/{profileId}/facts") @ResponseStatus(HttpStatus.CREATED)
  public ProfileViews.Fact addFact(@PathVariable UUID profileId, @Valid @RequestBody ProfileRequests.CreateFact request) { return profiles.addFact(profileId, request); }
  @PostMapping("/{profileId}/facts/{factId}/verify")
  public ProfileViews.Fact verifyFact(@PathVariable UUID profileId, @PathVariable UUID factId) { return profiles.verifyFact(profileId, factId); }
  @org.springframework.web.bind.annotation.ExceptionHandler(EntityNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ErrorResponse notFound(EntityNotFoundException error) { return new ErrorResponse(error.getMessage()); }
  record ErrorResponse(String message) { }
}

