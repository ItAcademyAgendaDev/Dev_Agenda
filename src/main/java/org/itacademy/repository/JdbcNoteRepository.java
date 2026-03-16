package org.itacademy.repository;

import org.itacademy.domain.note.model.Note;
import org.itacademy.domain.note.repository.NoteRepository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public record JdbcNoteRepository(Connection connection) implements NoteRepository {

    @Override
    public Note save(Note note) {
        String sql = "INSERT INTO note (description, task_id) VALUES (?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, note.getDescription());
            stmt.setLong(2, note.getTaskId());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return findById(rs.getLong(1)).orElse(note);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error saving note", e);
        }

        return note;
    }

    @Override
    public List<Note> findAll() {
        List<Note> notes = new ArrayList<>();
        String sql = "SELECT * FROM note";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                notes.add(mapRow(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving notes", e);
        }

        return notes;
    }

    @Override
    public Optional<Note> findById(Long id) {
        String sql = "SELECT * FROM note WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error finding note", e);
        }

        return Optional.empty();
    }

    @Override
    public void update(Note note) {
        String sql = "UPDATE note SET description = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, note.getDescription());
            stmt.setLong(2, note.getId());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("Updating note failed, no rows affected.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error updating note", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM note WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting note", e);
        }
    }

    private Note mapRow(ResultSet rs) throws SQLException {
        return new Note(
                rs.getLong("id"),
                rs.getString("description"),
                rs.getObject("creation_date", LocalDate.class),
                rs.getLong("task_id")
        );
    }
}