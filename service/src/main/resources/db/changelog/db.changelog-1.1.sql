--liquibase formatted sql

--changeset narel:1
ALTER TABLE users ALTER COLUMN password TYPE VARCHAR(128);
--rollback DROP TABLE users;