package com.applyflow.workflow;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/workflows/current")
public class WorkflowController {
  private final WorkflowService workflows;
  public WorkflowController(WorkflowService workflows) { this.workflows = workflows; }
  @GetMapping public WorkflowSnapshot current() { return workflows.current(); }
  @PostMapping("/analyze") public WorkflowSnapshot analyze() { return workflows.analyze(); }
  @PostMapping("/approve") public WorkflowSnapshot approve() { return workflows.approve(); }
  @PostMapping("/submit") public WorkflowSnapshot submit() { return workflows.markSubmitted(); }
  @ExceptionHandler(IllegalStateException.class) @ResponseStatus(HttpStatus.CONFLICT)
  public ApiError conflict(IllegalStateException error) { return new ApiError(error.getMessage()); }
  record ApiError(String message) { }
}

