CREATE TABLE IF NOT EXISTS users (
    id Serial PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(300) NOT NULL,
    role VARCHAR(255) NOT NULL
);
