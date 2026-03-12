package org.itacademy.repository;


import org.itacademy.domain.task.exception.TaskNotFoundException;
import org.itacademy.domain.task.model.Priority;
import org.itacademy.domain.task.model.Status;
import org.itacademy.domain.task.model.Task;
import org.itacademy.domain.task.repository.TaskRepository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public record JdbcTaskRepository(Connection connection) implements TaskRepository {

    @Override
    public Task save(Task task) {
        String sql = "INSERT INTO task (title, description, deadline, priority, status, event_id) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, task.getTitle());
            stmt.setString(2, task.getDescription());
            stmt.setObject(3, task.getDeadline());
            stmt.setString(4, task.getPriority().name());
            stmt.setString(5, task.getStatus().name());
            stmt.setObject(6, task.getEventId());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                task.setId(rs.getLong(1));
                return findById(task.getId()).orElseThrow(() -> new TaskNotFoundException("Error finding created task, task not found in database"));
            }

        } catch (SQLException e) {
            System.err.println("Error saving task: " + e.getMessage());
        }
        return task;
    }

    @Override
    public Optional<Task> findById(Long id) {

        String sql = "SELECT * FROM task WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setLong(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapRow(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error finding task", e);
        }

        return Optional.empty();
    }

    public List<Task> findAll() {

        List<Task> tasks = new ArrayList<>();

        String sql = "SELECT * FROM task";

        try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                tasks.add(mapRow(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving tasks", e);
        }

        return tasks;
    }

    @Override
    public void update(Task task) {
        String sql = "UPDATE task SET title = ?, description = ?, deadline = ?, priority = ?, status = ?, event_id = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, task.getTitle());
            stmt.setString(2, task.getDescription());
            stmt.setObject(3, task.getDeadline());
            stmt.setString(4, task.getPriority().name());
            stmt.setString(5, task.getStatus().name());
            stmt.setLong(6, task.getId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Updating task failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error updating task with ID: " + task.getId(), e);
        }
    }

    public List<Task> findByStatus() {

        List<Task> tasks = new ArrayList<>();

        String sql = "SELECT * FROM task WHERE status = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, Status.COMPLETED.name());

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                tasks.add(mapRow(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving completed tasks", e);
        }

        return tasks;
    }

    @Override
    public void deleteById(Long id) {

        String sql = "DELETE FROM task WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setLong(1, id);

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting task", e);
        }
    }

    private Task mapRow(ResultSet rs) throws SQLException {

        return new Task(rs.getLong("id"), rs.getString("title"), rs.getString("description"), rs.getObject("creation_date", LocalDate.class), rs.getObject("deadline", LocalDate.class), Priority.valueOf(rs.getString("priority")), Status.valueOf(rs.getString("status")), rs.getLong("event_id"));
    }
}
