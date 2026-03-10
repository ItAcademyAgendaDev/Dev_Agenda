package org.itacademy.domain.note.controller;

import org.itacademy.domain.note.controller.builder.NoteBuilder;
import org.itacademy.domain.note.model.Note;
import org.itacademy.domain.note.service.NoteService;
import org.itacademy.input.InputReader;

import java.util.List;

public record MenuNote(InputReader scanner, NoteService noteService) {

    public void createNote() {
        Note createdNote = noteService.createNote(
                new NoteBuilder(scanner)
                        .withDescription()
                        .withTaskId()
                        .build()
        );

        System.out.println("\n✅ Note created successfully!");
        System.out.println(createdNote);
    }

    public void getNotes() {
        List<Note> allNotes = noteService.listNotes();

        if (allNotes.isEmpty()) {
            System.out.println("There are no notes in the system!");
        } else {
            System.out.println("PRINTING NOTES...");
            allNotes.forEach(System.out::println);
        }
    }

    public void updateNote() {
        Long id = (long) scanner.readInt("Enter the id of the note to update: ");
        String newDescription = scanner.readString("Enter the new description: ");

        noteService.updateNote(id, newDescription);
        System.out.println("✅ Note updated successfully!");
    }

    public void deleteNote() {
        Long id = (long) scanner.readInt("Enter the id of the note to delete: ");

        noteService.deleteNote(id);
        System.out.println("✅ Note deleted successfully!");
    }
}