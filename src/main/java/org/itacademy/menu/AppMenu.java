package org.itacademy.menu;

import org.itacademy.domain.event.MenuEvent;
import org.itacademy.domain.task.controller.MenuTask;
import org.itacademy.input.InputReader;

public record AppMenu(
        InputReader scanner,
        MenuEvent menuEvent,
        MenuTask menuTask
) {

    public void start() {
        while (true) {
            displayMainMenu();
            int choice = scanner.readInt("Select an option: ");

            switch (choice) {
                case 1 -> MenuEventImpl.showMenu(scanner, menuEvent);
                case 2 -> MenuTaskImpl.showMenu(scanner, menuTask, menuEvent);
                //case 3 -> menuNote.showMenu();
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