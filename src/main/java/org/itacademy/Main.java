package org.itacademy;

import org.itacademy.infrastructure.DatabaseConnectionFactory;
import org.itacademy.infrastructure.DatabaseMigration;
import org.itacademy.infrastructure.JdbcTaskRepository;
import org.itacademy.input.ConsoleInputReader;
import org.itacademy.input.InputReader;
import org.itacademy.model.task.controller.MenuTask;
import org.itacademy.model.task.service.TaskService;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        InputReader scanner = new ConsoleInputReader();


        String url = "jdbc:mysql://localhost:3315/agenda_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        String user = "root";
        String password = "root_password";


        DatabaseMigration.migrate(url, user, password); //se crea la conexión para crear las tablas

        Connection connection = DatabaseConnectionFactory.createConnection(url, user, password); // se crea la conexión para hacer consultas

        JdbcTaskRepository taskRepository = new JdbcTaskRepository(connection); // paso la conexión a mis repositorios (Task, Note, Event)

        //Esto podría ir dentro de un menú global
        TaskService taskService = new TaskService(taskRepository);
        MenuTask menuTask = new MenuTask(scanner, taskService);
        menuTask.createTask();






    }
}