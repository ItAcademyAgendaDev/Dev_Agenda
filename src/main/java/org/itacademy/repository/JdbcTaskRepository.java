package org.itacademy.repository;


import org.itacademy.domain.task.model.Task;
import org.itacademy.domain.task.repository.TaskRepository;

import java.sql.*;

public record JdbcTaskRepository(Connection connection) implements TaskRepository {

    @Override
    public Task save(Task task) {
        String sql = "INSERT INTO task (title, description, creation_date, deadline, priority, status) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, task.getTitle());
            stmt.setString(2, task.getDescription());
            stmt.setObject(3, task.getCreationDate());
            stmt.setObject(4, task.getDeadline());
            stmt.setString(5, task.getPriority().name());
            stmt.setString(6, task.getStatus().name());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    task.setId(rs.getLong(1));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error guardando tarea: " + e.getMessage());
        }
        return task;
    }
}
