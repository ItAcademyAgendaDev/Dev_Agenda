package org.itacademy.domain.note.repository;

import org.itacademy.domain.note.model.Note;

import java.util.List;
import java.util.Optional;

public interface NoteRepository {

    Note save(Note note);

    List<Note> findAll();

    Optional<Note> findById(Long id);

    void update(Note note);

    void deleteById(Long id);
}