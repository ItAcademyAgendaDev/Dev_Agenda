package org.itacademy.model;

import java.util.Date;

public abstract class Agenda {

    private Long id;
    private String name;
    private String description;
    private Date date;

    public Agenda(Long id, String name, String description, Date date) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.date = date;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public Date getDate() { return date; }

    public void setId(Long id) { this.id = id; }

}
