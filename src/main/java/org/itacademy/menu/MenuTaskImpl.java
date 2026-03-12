package org.itacademy.menu;

import org.itacademy.domain.event.MenuEvent;
import org.itacademy.domain.task.controller.MenuTask;
import org.itacademy.input.InputReader;

public class MenuTaskImpl {
    public static void showMenu(InputReader scanner, MenuTask menuTask, MenuEvent menuEvent) {
        while (true) {
            System.out.println("""
                    
                    --- EVENT MANAGEMENT ---
                    1. Create Task
                    2. List All Task
                    3. Update Status
                    4. List Completed Tasks
                    5. Delete Task
                    0. Back to Main Menu
                    """);

            int choice = scanner.readInt("Option: ");
            switch (choice) {
                case 1 -> {
                    if (scanner.readYesNo("Do you want to add to an event?")) {
                        menuEvent.displayAllEvents();
                        Long idEvent = (long) scanner.readInt("Choose an event ID: ");
                        menuEvent.getById(idEvent);
                        menuTask.createTask(idEvent);
                    } else {
                        menuTask.createTask(null);
                    }

                }

                case 2 -> menuTask.getTasks();
                case 3 -> menuTask.markAsCompleted();
                case 4 -> menuTask.getCompletedTasks();
                case 5 -> menuTask.deleteTask();
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }
}
