package org.itacademy;

import org.itacademy.repository.AgendaRepository;
import org.itacademy.repository.CreateAgendaTables;
import org.itacademy.repository.DatabaseConnectionFactory;
import org.itacademy.repository.JdbcAgendaRepository;

import java.sql.Connection;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        DatabaseConnectionFactory factory = new DatabaseConnectionFactory(
                        "jdbc:mysql://localhost:3315/agenda_db",
                        "root",
                        "root_password"
        );
        Connection connection = factory.createConnection();

        CreateAgendaTables createAgendaTables = new CreateAgendaTables(connection);

        createAgendaTables.createTables();

        AgendaRepository repository = new JdbcAgendaRepository(connection);



    }
}