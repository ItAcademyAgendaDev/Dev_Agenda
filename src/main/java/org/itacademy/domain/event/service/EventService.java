package org.itacademy.domain.event.service;

import org.itacademy.domain.event.model.Event;
import org.itacademy.domain.event.model.EventDto;
import org.itacademy.domain.event.repository.EventRepository;

import java.util.List;

public record EventService(EventRepository eventRepository){

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
}
