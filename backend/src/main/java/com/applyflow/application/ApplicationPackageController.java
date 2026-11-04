package com.applyflow.application;

import jakarta.persistence.EntityNotFoundException;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/application-packages")
public class ApplicationPackageController {
  private final ApplicationPackageService packages;
  public ApplicationPackageController(ApplicationPackageService packages) { this.packages = packages; }
  @PostMapping @ResponseStatus(HttpStatus.CREATED)
  public ApplicationPackageView create(@RequestParam UUID profileId, @RequestParam UUID jobId) { return packages.create(profileId, jobId); }
  @GetMapping("/{packageId}") public ApplicationPackageView get(@PathVariable UUID packageId) { return packages.get(packageId); }
  @org.springframework.web.bind.annotation.ExceptionHandler(EntityNotFoundException.class) @ResponseStatus(HttpStatus.NOT_FOUND)
  public ErrorResponse notFound(EntityNotFoundException error) { return new ErrorResponse(error.getMessage()); }
  @org.springframework.web.bind.annotation.ExceptionHandler(IllegalStateException.class) @ResponseStatus(HttpStatus.CONFLICT)
  public ErrorResponse conflict(IllegalStateException error) { return new ErrorResponse(error.getMessage()); }
  record ErrorResponse(String message) { }
}

