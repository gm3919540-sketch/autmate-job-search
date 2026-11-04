package com.applyflow.application;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApplicationTrackingController {
  private final ApplicationTrackingService tracking;
  public ApplicationTrackingController(ApplicationTrackingService tracking) { this.tracking = tracking; }
  @PostMapping("/approvals") @ResponseStatus(HttpStatus.CREATED)
  public ApplicationTrackingViews.Approval decide(@Valid @RequestBody ApplicationRequests.Approval request) { return tracking.decide(request); }
  @PostMapping("/applications/{applicationId}/submit")
  public ApplicationTrackingViews.Application submit(@PathVariable UUID applicationId) { return tracking.submit(applicationId); }
  @GetMapping("/applications/{applicationId}")
  public ApplicationTrackingViews.Application get(@PathVariable UUID applicationId) { return tracking.get(applicationId); }
  @GetMapping("/applications")
  public List<ApplicationTrackingViews.Application> list(@RequestParam UUID profileId) { return tracking.list(profileId); }
  @org.springframework.web.bind.annotation.ExceptionHandler(EntityNotFoundException.class) @ResponseStatus(HttpStatus.NOT_FOUND)
  public ErrorResponse notFound(EntityNotFoundException error) { return new ErrorResponse(error.getMessage()); }
  @org.springframework.web.bind.annotation.ExceptionHandler(IllegalStateException.class) @ResponseStatus(HttpStatus.CONFLICT)
  public ErrorResponse conflict(IllegalStateException error) { return new ErrorResponse(error.getMessage()); }
  record ErrorResponse(String message) { }
}

