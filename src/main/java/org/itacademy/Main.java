package org.itacademy;

import org.itacademy.repository.config.DatabaseConnectionFactory;
import org.itacademy.repository.config.DatabaseMigration;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3315/agenda_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        String user = "root";
        String password = "root_password";

        DatabaseMigration.migrate(url, user, password);
        Connection connection = DatabaseConnectionFactory.createConnection(url, user, password);

    }
}