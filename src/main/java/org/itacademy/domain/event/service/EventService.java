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

    public List<Event> findAll(){
        return eventRepository.findAll();
    }


    private EventDto mapToDTO(Event event) {
        return new EventDto(event.getId(), event.getTitle(), event.getDescription(), event.getEventDate());
    }
}
