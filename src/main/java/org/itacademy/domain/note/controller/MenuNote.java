package org.itacademy.domain.note.controller;

import org.itacademy.domain.exception.RequestNotFoundException;
import org.itacademy.domain.exception.TitleInBlankException;
import org.itacademy.domain.note.dto.NoteDtoRequest;
import org.itacademy.domain.note.dto.NoteDtoResponse;
import org.itacademy.domain.note.service.NoteService;
import org.itacademy.input.InputReader;

import java.util.List;

public record MenuNote(InputReader scanner, NoteService noteService) {

    public void createNote() {
        try {
            String description = scanner.readString("Enter note description: ");
            Long taskId = (long) scanner.readInt("Enter related task ID: ");

            NoteDtoResponse created = noteService.createNote(new NoteDtoRequest(description, taskId));
            System.out.println("Note created successfully!");
            System.out.println(created);
        } catch (TitleInBlankException | RequestNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void getNotes() {
        List<NoteDtoResponse> notes = noteService.listNotes();

        if (notes.isEmpty()) {
            System.out.println("\n⚠️ There are no notes in the system!");
            return;
        }

        System.out.println("\n=== ALL NOTES ===");
        notes.forEach(System.out::println);
        System.out.println("=================\n");
    }

    public void updateNote() {
        getNotes();
        try {
            Long id = (long) scanner.readInt("Enter note id to update: ");
            String description = scanner.readString("Enter new description: ");

            noteService.updateNote(id, description);
            System.out.println("Note updated successfully!");
        } catch (TitleInBlankException | RequestNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void deleteNote() {
        getNotes();
        try {
            Long id = (long) scanner.readInt("Enter note id to delete: ");

            if (!scanner.readYesNo("Are you sure you want to delete this note?")) {
                return;
            }

            noteService.deleteNote(id);
            System.out.println("Note deleted successfully!");
        } catch (RequestNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }
}