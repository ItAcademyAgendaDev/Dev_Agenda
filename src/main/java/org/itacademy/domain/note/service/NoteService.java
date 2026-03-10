package org.itacademy.domain.note.service;

import org.itacademy.domain.note.model.Note;
import org.itacademy.domain.note.repository.NoteRepository;
import org.itacademy.domain.task.repository.TaskRepository;

import java.util.List;

public record NoteService(NoteRepository noteRepository, TaskRepository taskRepository) {

    public Note createNote(Note note) {
        if (taskRepository.findById(note.getTaskId()) == null) {
            throw new IllegalArgumentException("No task found with that ID.");
        }
        return noteRepository.save(note);
    }

    public List<Note> listNotes() {
        return noteRepository.findAll();
    }

    public void updateNote(Long id, String description) {
        Note existing = noteRepository.findById(id);

        if (existing == null) {
            throw new IllegalArgumentException("No note found with that ID.");
        }

        existing.setDescription(description);
        boolean updated = noteRepository.update(existing);

        if (!updated) {
            throw new IllegalStateException("Could not update the note.");
        }
    }

    public void deleteNote(Long id) {
        boolean deleted = noteRepository.deleteById(id);

        if (!deleted) {
            throw new IllegalArgumentException("No note found with that ID.");
        }
    }
}