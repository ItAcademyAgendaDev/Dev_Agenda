package org.itacademy.domain.note.repository;

import org.itacademy.domain.note.model.Note;

import java.util.List;

public interface NoteRepository {
    Note save(Note note);
    List<Note> findAll();
    Note findById(Long id);
    boolean update(Note note);
    boolean deleteById(Long id);
}