package org.itacademy.domain.event;

import org.itacademy.domain.task.model.Task;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Event {

    private Long id;
    private String name;
    private String description;
    private LocalDate eventDate;
    private List<Task> tasks;

    public Event(String name, String description, LocalDate eventDate, List<Task> tasks) {
        this.name = name;
        this.description = description;
        this.eventDate = eventDate;
        this.tasks = new ArrayList<>();
    }

    public Event(Long id, String name, String description, LocalDate eventDate, List<Task> tasks) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.eventDate = eventDate;
        this.tasks = tasks;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }



}
