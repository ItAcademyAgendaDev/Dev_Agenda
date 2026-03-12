CREATE TABLE IF NOT EXISTS task (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    creation_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deadline DATETIME,
    priority VARCHAR(20),
    status VARCHAR(20),
    event_id BIGINT NULL,

    CONSTRAINT fk_task_event
    FOREIGN KEY (event_id)
    REFERENCES event(id)
    ON DELETE CASCADE
    );
)