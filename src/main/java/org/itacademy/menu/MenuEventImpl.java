package org.itacademy.menu;

import org.itacademy.domain.event.MenuEvent;
import org.itacademy.input.InputReader;

public record MenuEventImpl() {
    public static void showMenu(InputReader scanner, MenuEvent menuEvent) {
        while (true) {
            System.out.println("""
                    
                    --- EVENT MANAGEMENT ---
                    1. Create Event
                    2. List All Events
                    3. Update Event
                    4. Delete Event
                    0. Back to Main Menu
                    """);

            int choice = scanner.readInt("Option: ");
            switch (choice) {
                case 1 -> menuEvent.createEvent();
                case 2 -> menuEvent.displayAllEvents();
                case 3 -> menuEvent.updateEvent();
                case 4 -> menuEvent.deleteEvent();
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }
}
