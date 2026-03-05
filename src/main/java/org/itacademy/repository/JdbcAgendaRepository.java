package org.itacademy.repository;

import org.itacademy.model.Agenda;
import org.itacademy.model.Event;
import org.itacademy.model.Note;
import org.itacademy.model.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcAgendaRepository implements AgendaRepository{

    private final Connection connection;

    public JdbcAgendaRepository(Connection connection) {
        this.connection = connection;
    }

    // CREATE
    @Override
    public Agenda save(Agenda agenda) {
        String sql = "INSERT INTO agenda (name, description, date) VALUES (?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, agenda.getName());
            ps.setString(2, agenda.getDescription());
            ps.setTimestamp(3, new Timestamp(agenda.getDate().getTime()));

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                agenda.setId(rs.getLong(1));
            }

            return agenda;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // READ BY ID
    @Override
    public Optional<Agenda> findById(Long id) {
        String sql = "SELECT * FROM agenda WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Agenda agenda = mapRow(rs);
                return Optional.of(agenda);
            }

            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // READ ALL
    @Override
    public List<Agenda> findAll() {
        String sql = "SELECT * FROM agenda";
        List<Agenda> list = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }

            return list;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // UPDATE
    @Override
    public Agenda update(Agenda agenda) {
        String sql = "UPDATE agenda SET name = ?, description = ?, date = ? WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, agenda.getName());
            ps.setString(2, agenda.getDescription());
            ps.setTimestamp(3, new Timestamp(agenda.getDate().getTime()));
            ps.setLong(4, agenda.getId());

            ps.executeUpdate();
            return agenda;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // DELETE
    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM agenda WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Mapper
    private Agenda mapRow(ResultSet rs) throws SQLException {

        String type = rs.getString("type");

        switch (type) {
            case "TASK":
                return new Task(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getTimestamp("date")
                );

            case "EVENT":
                return new Event(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getTimestamp("date")
                );

            case "NOTE":
                return new Note(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getTimestamp("date")
                );

            default:
                throw new IllegalArgumentException("Unknown type: " + type);
        }
    }
}
