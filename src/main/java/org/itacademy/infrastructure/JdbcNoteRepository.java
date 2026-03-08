package org.itacademy.infrastructure;

import org.itacademy.model.note.model.Note;
import org.itacademy.model.note.repository.NoteRepository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcNoteRepository implements NoteRepository {

    private final Connection connection;

    public JdbcNoteRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Note save(Note note) {
        String sql = "INSERT INTO note(description, creation_date, task_id) VALUES (?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, note.getDescription());
            ps.setTimestamp(2, Timestamp.valueOf(note.getCreationDate()));
            ps.setLong(3, note.getTaskId());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    note.setId(rs.getLong(1));
                }
            }

            return note;
        } catch (SQLException e) {
            throw new RuntimeException("Error saving note", e);
        }
    }

    @Override
    public Optional<Note> findById(Long id) {
        String sql = "SELECT * FROM note WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding note by id", e);
        }
    }

    @Override
    public List<Note> findAll() {
        String sql = "SELECT * FROM note ORDER BY id ASC";
        List<Note> notes = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                notes.add(mapRow(rs));
            }

            return notes;
        } catch (SQLException e) {
            throw new RuntimeException("Error listing notes", e);
        }
    }

    @Override
    public boolean update(Note note) {
        String sql = "UPDATE note SET description = ?, task_id = ? WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, note.getDescription());
            ps.setLong(2, note.getTaskId());
            ps.setLong(3, note.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating note", e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM note WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting note", e);
        }
    }

    private Note mapRow(ResultSet rs) throws SQLException {
        Long id = rs.getLong("id");
        String description = rs.getString("description");
        LocalDateTime creationDate = rs.getTimestamp("creation_date").toLocalDateTime();
        Long taskId = rs.getLong("task_id");

        return new Note(id, description, creationDate, taskId);
    }
}