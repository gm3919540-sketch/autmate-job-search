CREATE TABLE IF NOT EXISTS approvals (
  id UUID PRIMARY KEY,
  package_id UUID NOT NULL UNIQUE REFERENCES application_packages(id) ON DELETE CASCADE,
  actor VARCHAR(120) NOT NULL,
  decision VARCHAR(32) NOT NULL,
  note TEXT NOT NULL,
  decided_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS applications (
  id UUID PRIMARY KEY,
  package_id UUID NOT NULL UNIQUE REFERENCES application_packages(id) ON DELETE CASCADE,
  profile_id UUID NOT NULL REFERENCES candidate_profiles(id) ON DELETE CASCADE,
  job_id UUID NOT NULL REFERENCES jobs(id) ON DELETE CASCADE,
  status VARCHAR(32) NOT NULL,
  submitted_at TIMESTAMPTZ,
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS application_events (
  id UUID PRIMARY KEY,
  application_id UUID NOT NULL REFERENCES applications(id) ON DELETE CASCADE,
  event_type VARCHAR(80) NOT NULL,
  payload TEXT NOT NULL,
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS application_events_application_created_idx ON application_events(application_id, created_at);

