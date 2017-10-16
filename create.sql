CREATE DATABASE dms2;
CREATE SCHEMA dms2;
CREATE EXTENSION pgcrypto;

CREATE TABLE IF NOT EXISTS dms2.users(
    user_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    username TEXT NOT NULL,
    email TEXT NOT NULL,
    password_hash TEXT NOT NULL -- https://stackoverflow.com/questions/14918763/generating-postgresql-user-password
);

CREATE TYPE dms2.denial_source AS ENUM(
    'DMS', 'Summit', 'Apollo', 'Other'
);

CREATE TYPE dms2.denial_category AS ENUM(
    'NICU', 'MedSurg', 'PSYCH', 'Readmit'
);

CREATE TABLE IF NOT EXISTS dms2.denials(
  denial_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  date_assigned TIMESTAMP,
  assigned_to UUID REFERENCES dms2.users(user_id),

  dates_denied TEXT,
  denial_reason TEXT,
  pages INTEGER,
  patient_id TEXT,
  patient_name TEXT
);

CREATE TABLE IF NOT EXISTS dms2.appeals(
  appeal_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  denial UUID REFERENCES dms2.denials(denial_id),
  date_submitted TIMESTAMP NOT NULL,
  date_reviewed TIMESTAMP,
  body TEXT
);
