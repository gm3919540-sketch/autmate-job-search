CREATE TABLE IF NOT EXISTS application_packages (
  id UUID PRIMARY KEY,
  profile_id UUID NOT NULL REFERENCES candidate_profiles(id) ON DELETE CASCADE,
  job_id UUID NOT NULL REFERENCES jobs(id) ON DELETE CASCADE,
  resume_content TEXT NOT NULL,
  cover_letter_content TEXT NOT NULL,
  validation_status VARCHAR(32) NOT NULL,
  validation_notes TEXT NOT NULL,
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS application_packages_profile_job_idx ON application_packages(profile_id, job_id, created_at DESC);

