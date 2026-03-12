package org.itacademy.domain.event.model;

import org.itacademy.domain.task.model.Task;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Event {

    private Long id;
    private String title;
    private String description;
    private LocalDate eventDate;

    private Event(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.description = builder.description;
        this.eventDate = builder.eventDate;
    }
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public LocalDate getEventDate() { return eventDate; }


    public static class Builder {
        private Long id;
        private String title;
        private String description;
        private LocalDate eventDate;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder title(String title) { this.title = title; return this; }
        public Builder description(String description) { this.description = description; return this; }
        public Builder eventDate(LocalDate eventDate) { this.eventDate = eventDate; return this; }

        public Event build() {
            if (title == null || title.isBlank()) throw new IllegalStateException("Title is required");
            return new Event(this);
        }


    }



}
