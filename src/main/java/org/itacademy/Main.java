package org.itacademy;

import org.itacademy.domain.event.MenuEvent;
import org.itacademy.domain.event.repository.EventRepository;
import org.itacademy.domain.event.service.EventService;
import org.itacademy.domain.task.controller.MenuTask;
import org.itacademy.domain.task.repository.TaskRepository;
import org.itacademy.domain.task.service.TaskService;
import org.itacademy.input.ConsoleInputReader;
import org.itacademy.menu.AppMenu;
import org.itacademy.repository.JdbcEventRepository;
import org.itacademy.repository.JdbcTaskRepository;
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

        EventRepository jdbcEventRepository = new JdbcEventRepository(connection);
        MenuEvent menuEvent = new MenuEvent(new ConsoleInputReader(), new EventService(jdbcEventRepository));

        TaskRepository jdbcTaskRepository = new JdbcTaskRepository(connection);
        MenuTask menuTask = new MenuTask(new ConsoleInputReader(), new TaskService(jdbcTaskRepository));

        AppMenu commonMenu = new AppMenu(new ConsoleInputReader(), menuEvent, menuTask);
        commonMenu.start();






        System.out.println("Añadir evento");
        menuEvent.createEvent();
        System.out.println("añadir tarea al evento");



        // while (){menu.createTask(event1.getId()));







    }
}