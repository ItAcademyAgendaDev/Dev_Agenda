package org.itacademy.domain.event.service;

import org.itacademy.domain.event.model.Event;
import org.itacademy.domain.event.model.EventDto;
import org.itacademy.domain.event.model.EventWithTasksDto;
import org.itacademy.domain.event.repository.EventRepository;
import org.itacademy.domain.task.dto.TaskDtoResponse;
import org.itacademy.domain.task.mapper.TaskMapper;
import org.itacademy.domain.task.repository.TaskRepository;
import org.itacademy.domain.task.service.TaskService;

import java.util.List;

public class EventService {
    private static EventService instance;
    private final EventRepository eventRepository;
    private final TaskRepository taskRepository;

    private EventService(EventRepository eventRepository, TaskRepository taskRepository1) {
        this.eventRepository = eventRepository;
        this.taskRepository = taskRepository1;
    }

    public static EventService getInstance(EventRepository eventRepository, TaskRepository taskRepository) {
        if (instance == null) {
            instance = new EventService(eventRepository, taskRepository);
        }
        return instance;
    }

    public EventDto createEvent(EventDto eventDto){
        Event event = new Event.Builder()
                .title(eventDto.title())
                .description(eventDto.description())
                .eventDate(eventDto.eventDate())
                .build();

        Event savedEvent = eventRepository.save(event);
        return mapToDTO(savedEvent);
    }

    public List<EventDto> findAll() {
        return eventRepository.findAll().stream()
                .map(this::mapToDTO)
                .toList();
    }

    public EventDto updateEvent(Long id, EventDto dto) {
        return eventRepository.findById(id)
                .map(existingEvent -> {
                    Event updatedEvent = new Event.Builder()
                            .id(id)
                            .title(dto.title())
                            .description(dto.description())
                            .eventDate(dto.eventDate())
                            .build();
                    return mapToDTO(eventRepository.save(updatedEvent));
                })
                .orElseThrow(() -> new RuntimeException("Event not found with id: " + id));
    }

    public void deleteEvent(Long id) {
        if (!eventRepository.existsById(id)) {
            throw new RuntimeException("Cannot delete: Event not found");
        }
        eventRepository.deleteById(id);
    }

    private EventDto mapToDTO(Event event) {
        return new EventDto(event.getId(), event.getTitle(), event.getDescription(), event.getEventDate());
    }

    public void getById(Long id) {
        eventRepository.findById(id).orElseThrow(() -> new RuntimeException("Event not found with id: " + id));
    }

    public EventWithTasksDto getEventWithTasks(Long eventId) {
        EventDto event = eventRepository.findById(eventId)
                .map(this::mapToDTO)
                .orElseThrow(() -> new RuntimeException("Evento no encontrado"));

        List<TaskDtoResponse> tasks = taskRepository.findByEventId(eventId).stream()
                .map(TaskMapper::toDto)
                .toList();

        return new EventWithTasksDto(
                event,
                tasks
        );
    }
}
