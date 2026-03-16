import org.itacademy.domain.exception.RequestNotFoundException;
import org.itacademy.domain.exception.TitleInBlankException;
import org.itacademy.domain.note.dto.NoteDtoRequest;
import org.itacademy.domain.note.dto.NoteDtoResponse;
import org.itacademy.domain.note.model.Note;
import org.itacademy.domain.note.repository.NoteRepository;
import org.itacademy.domain.note.service.NoteService;
import org.itacademy.domain.task.model.Priority;
import org.itacademy.domain.task.model.Status;
import org.itacademy.domain.task.model.Task;
import org.itacademy.domain.task.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NoteServiceTest {

    private NoteRepository noteRepository;
    private TaskRepository taskRepository;
    private NoteService noteService;

    @BeforeEach
    void setUp() {
        noteRepository = mock(NoteRepository.class);
        taskRepository = mock(TaskRepository.class);
        noteService = new NoteService(noteRepository, taskRepository);
    }

    @Test
    void shouldCreateNote_whenRequestIsValidAndTaskExists() {
        NoteDtoRequest request = new NoteDtoRequest("Study Java notes", 1L);

        Task existingTask = new Task(
                1L,
                "Task 1",
                "desc",
                LocalDate.now(),
                null,
                Priority.MIDDLE,
                Status.NOT_COMPLETED,
                null
        );

        Note savedNote = new Note(
                1L,
                request.getDescription(),
                LocalDate.now(),
                request.getTaskId()
        );

        when(taskRepository.findById(1L)).thenReturn(Optional.of(existingTask));
        when(noteRepository.save(any(Note.class))).thenReturn(savedNote);

        NoteDtoResponse response = noteService.createNote(request);

        assertAll(
                () -> assertNotNull(response),
                () -> assertEquals("Study Java notes", response.getDescription()),
                () -> assertEquals(1L, response.getTaskId())
        );

        verify(taskRepository).findById(1L);
        verify(noteRepository).save(any(Note.class));
    }

    @Test
    void shouldThrowException_whenTaskDoesNotExist() {
        NoteDtoRequest request = new NoteDtoRequest("Invalid note", 99L);

        when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(
                RequestNotFoundException.class,
                () -> noteService.createNote(request)
        );

        verify(taskRepository).findById(99L);
        verify(noteRepository, never()).save(any());
    }

    @Test
    void shouldReturnAllNotes() {
        Note note1 = new Note(1L, "Note 1", LocalDate.now(), 1L);
        Note note2 = new Note(2L, "Note 2", LocalDate.now(), 1L);

        when(noteRepository.findAll()).thenReturn(List.of(note1, note2));

        var result = noteService.listNotes();

        assertEquals(2, result.size());
        verify(noteRepository).findAll();
    }

    @Test
    void shouldUpdateNote_whenNoteExists() {
        Note existingNote = new Note(1L, "Old desc", LocalDate.now(), 1L);

        when(noteRepository.findById(1L)).thenReturn(Optional.of(existingNote));

        noteService.updateNote(1L, "New desc");

        assertEquals("New desc", existingNote.getDescription());
        verify(noteRepository).update(existingNote);
    }

    @Test
    void shouldDeleteNote_whenNoteExists() {
        Note existingNote = new Note(1L, "Delete me", LocalDate.now(), 1L);

        when(noteRepository.findById(1L)).thenReturn(Optional.of(existingNote));

        noteService.deleteNote(1L);

        verify(noteRepository).deleteById(1L);
    }

    @Test
    void shouldThrowException_whenDescriptionIsBlank() {
        NoteDtoRequest request = new NoteDtoRequest("   ", 1L);

        assertThrows(
                TitleInBlankException.class,
                () -> noteService.createNote(request)
        );

        verify(noteRepository, never()).save(any());
    }
}