package org.itacademy.repository;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

public class Connection {

    private final String url;
    private final String user;
    private final String password;

    public Connection(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public Connection createConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
