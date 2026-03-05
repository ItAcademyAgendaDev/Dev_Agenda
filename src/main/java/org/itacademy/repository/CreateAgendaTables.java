package org.itacademy.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateAgendaTables {

    private final Connection connection;


    public CreateAgendaTables(Connection connection) {
        this.connection = connection;
    }

    public void createTables(){
       try { Statement stmt = connection.createStatement();


           String createEvent = """
                CREATE TABLE IF NOT EXISTS event (
                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(100) NOT NULL,
                    description VARCHAR(255),
                    event_date DATETIME NOT NULL,
                    repeat_type VARCHAR(20)
                )
            """;
           stmt.execute(createEvent);
           // Crear Task
           String createTask = """
                CREATE TABLE IF NOT EXISTS task (
                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(100) NOT NULL,
                    description VARCHAR(255),
                    due_date DATETIME,
                    priority VARCHAR(20),
                    status VARCHAR(20),
                    completed BOOLEAN DEFAULT FALSE,
                    event_id BIGINT NULL,
                    FOREIGN KEY (event_id) REFERENCES event(id)
                )
            """;
           stmt.execute(createTask);

           // Crear Note
           String createNote = """
                CREATE TABLE IF NOT EXISTS note (
                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                    description VARCHAR(255),
                    task_id BIGINT NULL,
                    FOREIGN KEY (task_id) REFERENCES task(id)
                )
            """;
           stmt.execute(createNote);

           System.out.println("Tablas creadas correctamente.");
       } catch (SQLException e) {
           throw new RuntimeException(e);
       }
    }
}
