package org.itacademy.domain.note.service;

import org.itacademy.domain.exception.RequestNotFoundException;
import org.itacademy.domain.exception.TitleInBlankException;
import org.itacademy.domain.note.dto.NoteDtoRequest;
import org.itacademy.domain.note.dto.NoteDtoResponse;
import org.itacademy.domain.note.mapper.NoteMapper;
import org.itacademy.domain.note.model.Note;
import org.itacademy.domain.note.repository.NoteRepository;
import org.itacademy.domain.task.repository.TaskRepository;

import java.util.List;

public record NoteService(NoteRepository noteRepository, TaskRepository taskRepository) {

    public NoteDtoResponse createNote(NoteDtoRequest request) {
        validateDescription(request.getDescription());
        validateTaskExists(request.getTaskId());

        Note note = NoteMapper.toEntity(request);
        return NoteMapper.toDto(noteRepository.save(note));
    }

    public List<NoteDtoResponse> listNotes() {
        return noteRepository.findAll().stream()
                .map(NoteMapper::toDto)
                .toList();
    }

    public void updateNote(Long id, String newDescription) {
        validateDescription(newDescription);

        Note foundNote = noteRepository.findById(id)
                .orElseThrow(() -> new RequestNotFoundException("Note not found"));

        foundNote.setDescription(newDescription);
        noteRepository.update(foundNote);
    }

    public void deleteNote(Long id) {
        noteRepository.findById(id)
                .orElseThrow(() -> new RequestNotFoundException("Note not found"));

        noteRepository.deleteById(id);
    }

    private void validateTaskExists(Long taskId) {
        taskRepository.findById(taskId)
                .orElseThrow(() -> new RequestNotFoundException("Task not found for this note"));
    }

    private void validateDescription(String description) {
        if (description == null || description.isBlank()) {
            throw new TitleInBlankException("Note description cannot be empty");
        }
    }
}