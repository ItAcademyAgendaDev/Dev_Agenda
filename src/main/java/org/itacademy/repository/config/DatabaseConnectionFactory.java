package org.itacademy.repository.config;

import java.sql.DriverManager;
import java.sql.Connection;

public class DatabaseConnectionFactory  {
    private DatabaseConnectionFactory(){}

    public static Connection createConnection(String url, String user, String password) {
        try {
            return DriverManager.getConnection(url, user, password);
        }catch (Exception ex){
            throw new RuntimeException();
        }
    }
}
