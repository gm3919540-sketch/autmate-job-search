package com.applyflow.application;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ApplicationTrackingService {
  private final ApplicationPackageRepository packages;
  private final ApplicationApprovalRepository approvals;
  private final JobApplicationRepository applications;
  private final ApplicationEventRepository events;
  public ApplicationTrackingService(ApplicationPackageRepository packages, ApplicationApprovalRepository approvals, JobApplicationRepository applications, ApplicationEventRepository events) {
    this.packages = packages; this.approvals = approvals; this.applications = applications; this.events = events;
  }
  public ApplicationTrackingViews.Approval decide(ApplicationRequests.Approval request) {
    if (approvals.existsByPackageId(request.packageId())) throw new IllegalStateException("This package has already been decided");
    ApplicationPackage item = packages.findById(request.packageId()).orElseThrow(() -> new EntityNotFoundException("Application package not found"));
    if (item.getValidationStatus() != ValidationStatus.VALID) throw new IllegalStateException("Only a valid package can enter the approval queue");
    ApplicationApproval approval = approvals.save(new ApplicationApproval(item.getId(), request.actor(), request.decision(), request.note() == null ? "" : request.note()));
    if (request.decision() == ApprovalDecision.APPROVED) {
      JobApplication application = applications.save(new JobApplication(item));
      events.save(new ApplicationEvent(application.getId(), "APPROVED", "Human approval recorded; application is ready for permitted submission."));
    }
    return approvalView(approval);
  }
  public ApplicationTrackingViews.Application submit(UUID applicationId) {
    JobApplication application = application(applicationId);
    application.markSubmitted();
    events.save(new ApplicationEvent(application.getId(), "SUBMITTED", "Submission was explicitly confirmed after approval."));
    return view(application);
  }
  @Transactional(readOnly = true)
  public ApplicationTrackingViews.Application get(UUID applicationId) { return view(application(applicationId)); }
  @Transactional(readOnly = true)
  public List<ApplicationTrackingViews.Application> list(UUID profileId) { return applications.findAllByProfileIdOrderByCreatedAtDesc(profileId).stream().map(this::view).toList(); }
  private JobApplication application(UUID id) { return applications.findById(id).orElseThrow(() -> new EntityNotFoundException("Application not found")); }
  private ApplicationTrackingViews.Approval approvalView(ApplicationApproval item) { return new ApplicationTrackingViews.Approval(item.getId(), item.getPackageId(), item.getActor(), item.getDecision(), item.getNote(), item.getDecidedAt()); }
  private ApplicationTrackingViews.Application view(JobApplication item) {
    List<ApplicationTrackingViews.Event> history = events.findAllByApplicationIdOrderByCreatedAtAsc(item.getId()).stream().map(event -> new ApplicationTrackingViews.Event(event.getEventType(), event.getPayload(), event.getCreatedAt())).toList();
    return new ApplicationTrackingViews.Application(item.getId(), item.getPackageId(), item.getProfileId(), item.getJobId(), item.getStatus(), item.getSubmittedAt(), item.getCreatedAt(), history);
  }
}

