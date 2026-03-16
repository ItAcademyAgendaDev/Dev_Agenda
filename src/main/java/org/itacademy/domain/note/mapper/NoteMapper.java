package org.itacademy.domain.note.mapper;

import org.itacademy.domain.note.dto.NoteDtoRequest;
import org.itacademy.domain.note.dto.NoteDtoResponse;
import org.itacademy.domain.note.model.Note;

public class NoteMapper {

    private NoteMapper() {
    }

    public static Note toEntity(NoteDtoRequest dto) {
        return new Note(dto.getDescription(), dto.getTaskId());
    }

    public static NoteDtoResponse toDto(Note note) {
        return new NoteDtoResponse(
                note.getId(),
                note.getDescription(),
                note.getCreationDate(),
                note.getTaskId()
        );
    }
}