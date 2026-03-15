import org.itacademy.domain.event.model.Event;
import org.itacademy.domain.event.model.EventDto;
import org.itacademy.domain.event.repository.EventRepository;
import org.itacademy.domain.event.service.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class EventServiceTest {

    private EventRepository eventRepository;
    private EventService eventService;

    @BeforeEach
    void setUp() {
        eventRepository = mock(EventRepository.class);
        eventService = new EventService(eventRepository);
    }

    @Test
    void shouldCreateEvent_whenRequestIsValid() {
        // Arrange
        EventDto requestDto = new EventDto(null, "Java Workshop", "Learning Testing", LocalDate.now());
        Event savedEvent = new Event.Builder()
                .id(1L)
                .title(requestDto.title())
                .description(requestDto.description())
                .eventDate(requestDto.eventDate())
                .build();

        when(eventRepository.save(any(Event.class))).thenReturn(savedEvent);

        // Act
        EventDto response = eventService.createEvent(requestDto);

        // Assert
        assertAll(
                () -> assertNotNull(response),
                () -> assertEquals(1L, response.id()),
                () -> assertEquals(requestDto.title(), response.title())
        );
        verify(eventRepository).save(any(Event.class));
    }

    @Test
    void shouldReturnAllEvents() {
        // Arrange
        Event event1 = new Event.Builder().id(1L).title("Event 1").eventDate(LocalDate.now()).build();
        Event event2 = new Event.Builder().id(2L).title("Event 2").eventDate(LocalDate.now()).build();

        when(eventRepository.findAll()).thenReturn(List.of(event1, event2));

        // Act
        List<EventDto> result = eventService.findAll();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Event 1", result.get(0).title());
        verify(eventRepository).findAll();
    }

    @Test
    void shouldUpdateEvent_whenEventExists() {
        // Arrange
        Long id = 1L;
        EventDto updateDto = new EventDto(id, "Updated Title", "Updated Desc", LocalDate.now());
        Event existingEvent = new Event.Builder().id(id).title("Old Title").build();
        Event updatedEvent = new Event.Builder()
                .id(id)
                .title(updateDto.title())
                .description(updateDto.description())
                .eventDate(updateDto.eventDate())
                .build();

        when(eventRepository.findById(id)).thenReturn(Optional.of(existingEvent));
        when(eventRepository.save(any(Event.class))).thenReturn(updatedEvent);

        // Act
        EventDto result = eventService.updateEvent(id, updateDto);

        // Assert
        assertEquals("Updated Title", result.title());
        verify(eventRepository).findById(id);
        verify(eventRepository).save(any(Event.class));
    }

    @Test
    void shouldThrowException_whenUpdatingNonExistingEvent() {
        // Arrange
        Long id = 99L;
        EventDto updateDto = new EventDto(id, "Title", "Desc", LocalDate.now());
        when(eventRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> eventService.updateEvent(id, updateDto)
        );

        assertEquals("Event not found with id: " + id, exception.getMessage());
        verify(eventRepository).findById(id);
        verify(eventRepository, never()).save(any());
    }

    @Test
    void shouldDeleteEvent_whenEventExists() {
        // Arrange
        Long id = 1L;
        when(eventRepository.existsById(id)).thenReturn(true);

        // Act
        eventService.deleteEvent(id);

        // Assert
        verify(eventRepository).deleteById(id);
    }

    @Test
    void shouldThrowException_whenDeletingNonExistingEvent() {
        // Arrange
        Long id = 1L;
        when(eventRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        assertThrows(
                RuntimeException.class,
                () -> eventService.deleteEvent(id)
        );
        verify(eventRepository, never()).deleteById(anyLong());
    }


}
