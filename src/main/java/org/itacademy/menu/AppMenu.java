package org.itacademy.menu;

import org.itacademy.domain.event.MenuEvent;
import org.itacademy.domain.note.controller.MenuNote;
import org.itacademy.domain.task.controller.MenuTask;
import org.itacademy.input.InputReader;

public class AppMenu {
    private static AppMenu instance;
    private final InputReader scanner;
    private final MenuEvent menuEvent;
    private final MenuTask menuTask;
    private final MenuNote menuNote;

    private AppMenu(InputReader scanner, MenuEvent menuEvent, MenuTask menuTask, MenuNote menuNote) {
        this.scanner = scanner;
        this.menuEvent = menuEvent;
        this.menuTask = menuTask;
        this.menuNote = menuNote;
    }


    public static AppMenu getInstance(InputReader scanner, MenuEvent menuEvent, MenuTask menuTask, MenuNote menuNote) {
        if (instance == null) {
            instance = new AppMenu(scanner, menuEvent, menuTask, menuNote);
        }
        return instance;
    }
    public void start() {
        while (true) {
            displayMainMenu();
            int choice = scanner.readInt("Select an option: ");

            switch (choice) {
                case 1 -> MenuEventImpl.showMenu(scanner, menuEvent);
                case 2 -> MenuTaskImpl.showMenu(scanner, menuTask, menuEvent);
                case 3 -> MenuNoteImpl.showMenu(scanner, menuNote);
                case 0 -> {
                    System.out.println("Exiting Agenda... Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    private void displayMainMenu() {
        System.out.println("""
                
                ==== IT ACADEMY AGENDA ====
                1. Manage Events
                2. Manage Tasks
                3. Manage Notes
                0. Exit
                ===========================
                """);
    }
}