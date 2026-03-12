package org.itacademy.domain.event.repository;

import org.itacademy.domain.event.model.Event;
import org.itacademy.domain.event.model.EventDto;

import java.util.List;
import java.util.Optional;

public interface EventRepository {
    Event save(Event event);
    Optional<Event> findById(Long id);
    List<Event> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
    Event updateEvent (Event event);
}
