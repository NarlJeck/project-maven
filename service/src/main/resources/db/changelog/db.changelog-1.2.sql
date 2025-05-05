--liquibase formatted sql

--changeset narel:1
ALTER TABLE users ALTER COLUMN password SET DEFAULT '{noop}123';
