-- V4__Create_user_roles_table.sql
CREATE TABLE my_user_roles (
    user_id BIGINT NOT NULL,
    role VARCHAR(255) NOT NULL,
    PRIMARY KEY (user_id, role),
    CONSTRAINT fk_user_roles_user
        FOREIGN KEY (user_id)
        REFERENCES "my_user"(id)
        ON DELETE CASCADE
);