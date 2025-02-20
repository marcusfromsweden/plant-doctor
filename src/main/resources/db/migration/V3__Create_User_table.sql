CREATE TABLE user_info
(
    id       SERIAL PRIMARY KEY,
    name     VARCHAR(255) NOT NULL,
    email    VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    roles    VARCHAR(255) NOT NULL
);