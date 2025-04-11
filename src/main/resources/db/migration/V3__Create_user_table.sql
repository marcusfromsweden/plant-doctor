-- V3__Create_user_table.sql
CREATE TABLE my_user (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    roles TEXT[]
);