CREATE TABLE IF NOT EXISTS topics (
    id Serial PRIMARY KEY,
    course VARCHAR(255) NOT NULL,
    title VARCHAR(255) NOT NULL,
    message TEXT NOT NULL,
    creation_date TIMESTAMP NOT NULL,
    user_id Serial NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id),
    enabled BOOLEAN DEFAULT TRUE
);