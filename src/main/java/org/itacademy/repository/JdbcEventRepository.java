package org.itacademy.repository;

import org.itacademy.domain.event.model.Event;
import org.itacademy.domain.event.model.EventDto;
import org.itacademy.domain.event.repository.EventRepository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public record JdbcEventRepository (Connection connection) implements EventRepository {

    @Override
    public Event save(Event event) {
        String sql = "INSERT INTO event (title, description, event_date) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, event.getTitle());
            stmt.setString(2, event.getDescription());
            stmt.setObject(3, event.getEventDate());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return findById(rs.getLong(1)).orElse(event);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error saving event: " + e.getMessage());
        }
        return event;
    }

    @Override
    public Optional<Event> findById(Long id) {
        String sql = "SELECT * FROM event WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding event", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Event> findAll() {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT * FROM event";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                events.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving events", e);
        }
        return events;
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM event WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting event", e);
        }
    }

    @Override
    public boolean existsById(Long id) {
        return false;
    }

    @Override
    public Event updateEvent(Event event) {
        String sql = "UPDATE event SET title = ?, description = ?, event_date = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, event.getTitle());
            stmt.setString(2, event.getDescription());
            stmt.setObject(3, event.getEventDate());
            stmt.setLong(4, event.getId());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("Updating event failed, no rows affected.");
            }

            return event;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating event with id: " + event.getId(), e);
        }
    }


    private Event mapRow(ResultSet rs) throws SQLException {
        return new Event.Builder()
                .id(rs.getLong("id"))
                .title(rs.getString("title"))
                .description(rs.getString("description"))
                .eventDate(rs.getObject("event_date", LocalDate.class))
                .build();
    }

}
