package org.itacademy;

import org.itacademy.domain.event.MenuEvent;
import org.itacademy.domain.event.repository.EventRepository;
import org.itacademy.domain.event.service.EventService;
import org.itacademy.domain.note.controller.MenuNote;
import org.itacademy.domain.note.repository.NoteRepository;
import org.itacademy.domain.note.service.NoteService;
import org.itacademy.domain.task.controller.MenuTask;
import org.itacademy.domain.task.repository.TaskRepository;
import org.itacademy.domain.task.service.TaskService;
import org.itacademy.input.ConsoleInputReader;
import org.itacademy.input.InputReader;
import org.itacademy.menu.AppMenu;
import org.itacademy.repository.JdbcEventRepository;
import org.itacademy.repository.JdbcNoteRepository;
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

        InputReader scanner = new ConsoleInputReader();

        EventRepository eventRepository = new JdbcEventRepository(connection);
        TaskRepository taskRepository = new JdbcTaskRepository(connection);
        NoteRepository noteRepository = new JdbcNoteRepository(connection);

        EventService eventService = new EventService(eventRepository);
        TaskService taskService = new TaskService(taskRepository);
        NoteService noteService = new NoteService(noteRepository, taskRepository);

        MenuEvent menuEvent = new MenuEvent(scanner, eventService);
        MenuTask menuTask = new MenuTask(scanner, taskService);
        MenuNote menuNote = new MenuNote(scanner, noteService);

        AppMenu appMenu = new AppMenu(scanner, menuEvent, menuTask, menuNote);
        appMenu.start();
    }
}