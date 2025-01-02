CREATE TABLE IF NOT EXISTS responses (
    id Serial PRIMARY KEY,
    message VARCHAR(300) NOT NULL,
    creation_date TIMESTAMP NOT NULL,
    update_date TIMESTAMP DEFAULT NULL, 
    user_id INT NOT NULL,
    topic_id INT NOT NULL,
    enabled BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (topic_id) REFERENCES topics(id)
)