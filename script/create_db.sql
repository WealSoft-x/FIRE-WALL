-- have to execute by postgres user.
-- Crate Database
CREATE DATABASE firewall;
\c firewall
-- Create schema
CREATE SCHEMA proxy;
-- Create Role
CREATE ROLE proxy_user WITH LOGIN PASSWORD 'dbpasswd';

create table proxy.users (
  id SERIAL PRIMARY KEY,
  mail VARCHAR(255) UNIQUE NOT NULL,
  password VARCHAR(255) NOT NULL,
  name VARCHAR(10) NOT NULL,
  is_admin BOOLEAN default FALSE,
  initial_certification_count int default 0,
  token VARCHAR(255),
  token_out_time TIMESTAMP,
  updated_at TIMESTAMP,
  created_at TIMESTAMP
);

-- Grant
GRANT USAGE ON SCHEMA proxy TO proxy_user;
GRANT CONNECT ON DATABASE firewall TO proxy_user;
GRANT ALL ON SCHEMA proxy TO proxy_user;
-- create table
GRANT ALL ON ALL TABLES IN SCHEMA proxy TO proxy_user;
GRANT ALL ON ALL SEQUENCES IN SCHEMA proxy TO proxy_user;