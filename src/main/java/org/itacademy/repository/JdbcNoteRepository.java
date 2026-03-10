package org.itacademy.repository;

import org.itacademy.domain.note.model.Note;
import org.itacademy.domain.note.repository.NoteRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public record JdbcNoteRepository(Connection connection) implements NoteRepository {

    @Override
    public Note save(Note note) {
        String sql = "INSERT INTO note (description, task_id) VALUES (?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, note.getDescription());
            stmt.setLong(2, note.getTaskId());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                note.setId(rs.getLong(1));
                return findById(note.getId());
            }

        } catch (SQLException e) {
            System.err.println("Error saving note: " + e.getMessage());
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
    public Note findById(Long id) {
        String sql = "SELECT * FROM note WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapRow(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error finding note", e);
        }

        return null;
    }

    @Override
    public boolean update(Note note) {
        String sql = "UPDATE note SET description = ?, task_id = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, note.getDescription());
            stmt.setLong(2, note.getTaskId());
            stmt.setLong(3, note.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error updating note", e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM note WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting note", e);
        }
    }

    private Note mapRow(ResultSet rs) throws SQLException {
        return new Note(
                rs.getLong("id"),
                rs.getString("description"),
                rs.getTimestamp("creation_date").toLocalDateTime(),
                rs.getLong("task_id")
        );
    }
}