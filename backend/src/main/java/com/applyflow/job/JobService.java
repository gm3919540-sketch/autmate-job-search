package com.applyflow.job;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class JobService {
  private final JobPostingRepository jobs;
  private final JobRequirementRepository requirements;
  public JobService(JobPostingRepository jobs, JobRequirementRepository requirements) { this.jobs = jobs; this.requirements = requirements; }
  public JobViews.Job create(JobRequests.CreateJob request) {
    JobPosting job = jobs.save(new JobPosting(request.source(), request.company(), request.title(), request.location(), request.description(), request.url()));
    request.requirements().forEach(item -> requirements.save(new JobRequirement(job, item.category(), item.requirement(), item.mandatory())));
    return view(job);
  }
  @Transactional(readOnly = true)
  public JobViews.Job get(UUID id) { return view(job(id)); }
  List<JobRequirement> requirements(UUID jobId) { return requirements.findAllByJobIdOrderByMandatoryDesc(jobId); }
  JobPosting job(UUID id) { return jobs.findById(id).orElseThrow(() -> new EntityNotFoundException("Job not found")); }
  private JobViews.Job view(JobPosting job) {
    List<JobViews.Requirement> items = requirements(job.getId()).stream().map(item -> new JobViews.Requirement(item.getId(), item.getCategory(), item.getRequirement(), item.isMandatory())).toList();
    return new JobViews.Job(job.getId(), job.getSource(), job.getCompany(), job.getTitle(), job.getLocation(), job.getDescription(), job.getUrl(), job.getCreatedAt(), items);
  }
}

