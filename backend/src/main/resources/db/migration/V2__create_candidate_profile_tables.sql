CREATE TABLE IF NOT EXISTS candidate_profiles (
  id UUID PRIMARY KEY,
  display_name VARCHAR(320) NOT NULL,
  summary TEXT NOT NULL,
  authorization VARCHAR(120) NOT NULL,
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE candidate_facts
  ADD CONSTRAINT candidate_facts_profile_fk
  FOREIGN KEY (profile_id) REFERENCES candidate_profiles(id) ON DELETE CASCADE;

CREATE INDEX IF NOT EXISTS candidate_facts_profile_created_idx
  ON candidate_facts(profile_id, created_at DESC);

