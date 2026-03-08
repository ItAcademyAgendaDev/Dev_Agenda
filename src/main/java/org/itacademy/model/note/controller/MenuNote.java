package org.itacademy.model.note.controller;

import org.itacademy.input.InputReader;
import org.itacademy.model.note.controller.builder.NoteBuilder;
import org.itacademy.model.note.model.Note;
import org.itacademy.model.note.service.NoteService;

public class MenuNote {
    private final InputReader scanner;
    private final NoteService noteService;

    public MenuNote(InputReader scanner, NoteService noteService) {
        this.scanner = scanner;
        this.noteService = noteService;
    }

    public void createNote() {
        Note note = new NoteBuilder(scanner)
                .withDescription()
                .withTaskId()
                .build();

        noteService.createNote(note);
        System.out.println("Note created successfully.");
    }

    public void listNotes() {
        noteService.listNotes().forEach(System.out::println);
    }

    public void updateNote() {
        Long id = scanner.readLong("Enter note id: ");
        String description = scanner.readString("Enter new description: ");
        noteService.updateNote(id, description);
        System.out.println("Note updated successfully.");
    }

    public void deleteNote() {
        Long id = scanner.readLong("Enter note id: ");
        noteService.deleteNote(id);
        System.out.println("Note deleted successfully.");
    }
}