package com.applyflow.knowledge;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
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
@RequestMapping("/api/profiles/{profileId}/knowledge")
public class CandidateKnowledgeController {
  private final CandidateKnowledgeService knowledge;
  public CandidateKnowledgeController(CandidateKnowledgeService knowledge) { this.knowledge = knowledge; }
  @PostMapping("/facts/{factId}") @ResponseStatus(HttpStatus.CREATED)
  public CandidateKnowledgeService.KnowledgeFact index(@PathVariable UUID profileId, @PathVariable UUID factId) { return knowledge.index(profileId, factId); }
  @GetMapping
  public List<CandidateKnowledgeService.KnowledgeResult> search(@PathVariable UUID profileId, @RequestParam @NotBlank String query) { return knowledge.search(profileId, query); }
  @org.springframework.web.bind.annotation.ExceptionHandler(EntityNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ErrorResponse notFound(EntityNotFoundException error) { return new ErrorResponse(error.getMessage()); }
  @org.springframework.web.bind.annotation.ExceptionHandler(IllegalStateException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public ErrorResponse unavailable(IllegalStateException error) { return new ErrorResponse(error.getMessage()); }
  record ErrorResponse(String message) { }
}

