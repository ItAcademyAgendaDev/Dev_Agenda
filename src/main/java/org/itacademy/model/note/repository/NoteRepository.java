package org.itacademy.model.note.repository;

import org.itacademy.model.note.model.Note;

import java.util.List;
import java.util.Optional;

public interface NoteRepository {
    Note save(Note note);
    Optional<Note> findById(Long id);
    List<Note> findAll();
    boolean update(Note note);
    boolean deleteById(Long id);
}