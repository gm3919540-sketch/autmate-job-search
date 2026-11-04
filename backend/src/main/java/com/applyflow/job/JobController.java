package com.applyflow.job;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
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
@RequestMapping("/api/jobs")
public class JobController {
  private final JobService jobs;
  private final JobMatchService matching;
  public JobController(JobService jobs, JobMatchService matching) { this.jobs = jobs; this.matching = matching; }
  @PostMapping @ResponseStatus(HttpStatus.CREATED)
  public JobViews.Job create(@Valid @RequestBody JobRequests.CreateJob request) { return jobs.create(request); }
  @GetMapping("/{jobId}") public JobViews.Job get(@PathVariable UUID jobId) { return jobs.get(jobId); }
  @PostMapping("/{jobId}/match") public JobViews.Match match(@PathVariable UUID jobId, @RequestParam UUID profileId) { return matching.match(jobId, profileId); }
  @org.springframework.web.bind.annotation.ExceptionHandler(EntityNotFoundException.class) @ResponseStatus(HttpStatus.NOT_FOUND)
  public ErrorResponse notFound(EntityNotFoundException error) { return new ErrorResponse(error.getMessage()); }
  record ErrorResponse(String message) { }
}

