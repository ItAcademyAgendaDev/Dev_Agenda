package org.itacademy.menu;

import org.itacademy.domain.note.controller.MenuNote;
import org.itacademy.input.InputReader;

public class MenuNoteImpl {

    public static void showMenu(InputReader scanner, MenuNote menuNote) {
        while (true) {
            System.out.println("""
                    
                    --- NOTE MANAGEMENT ---
                    1. Create Note
                    2. List All Notes
                    3. Update Note
                    4. Delete Note
                    0. Back to Main Menu
                    """);

            int choice = scanner.readInt("Option: ");

            switch (choice) {
                case 1 -> menuNote.createNote();
                case 2 -> menuNote.getNotes();
                case 3 -> menuNote.updateNote();
                case 4 -> menuNote.deleteNote();
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }
}