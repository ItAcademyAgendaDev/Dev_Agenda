package org.itacademy.model.note.service;

import org.itacademy.model.note.model.Note;
import org.itacademy.model.note.repository.NoteRepository;
import org.itacademy.model.task.repository.TaskRepository;

import java.util.List;

public class NoteService {
    private final NoteRepository noteRepository;
    private final TaskRepository taskRepository;

    public NoteService(NoteRepository noteRepository, TaskRepository taskRepository) {
        this.noteRepository = noteRepository;
        this.taskRepository = taskRepository;
    }

    public Note createNote(Note note) {
        if (taskRepository.findById(note.getTaskId()).isEmpty()) {
            throw new IllegalArgumentException("No task found with that ID.");
        }
        return noteRepository.save(note);
    }

    public List<Note> listNotes() {
        return noteRepository.findAll();
    }

    public void updateNote(Long id, String description) {
        Note existing = noteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No note found with that ID."));

        existing.setDescription(description);
        noteRepository.update(existing);
    }

    public void deleteNote(Long id) {
        boolean deleted = noteRepository.deleteById(id);
        if (!deleted) {
            throw new IllegalArgumentException("No note found with that ID.");
        }
    }
}