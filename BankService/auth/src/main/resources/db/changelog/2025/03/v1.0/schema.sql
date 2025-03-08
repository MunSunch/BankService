CREATE SCHEMA IF NOT EXISTS auth;
GO

CREATE TABLE IF NOT EXISTS auth.users (
    uuid UUID PRIMARY KEY,
    username text NOT NULL UNIQUE,
    password text NOT NULL,
    role text NOT NULL
);