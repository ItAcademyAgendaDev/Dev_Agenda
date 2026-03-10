package org.itacademy.domain.event.controller;

import org.itacademy.domain.event.model.Event;
import org.itacademy.domain.event.model.EventDto;
import org.itacademy.domain.event.service.EventService;
import org.itacademy.input.InputReader;

import java.net.StandardSocketOptions;
import java.time.LocalDate;

public record MenuEvent (InputReader scanner, EventService eventService) {

    public void createEvent(){
        String title = scanner.readString("Enter event title: ");
        String description = scanner.readYesNo("Do you want to add description") ? scanner.readString("Enter description")
                : "No description";

        LocalDate eventDate = LocalDate.of(
                scanner().readInt("Year: "),
                scanner().readInt("Month: "),
                scanner().readInt("Day: ")
        );

        EventDto requestEvent = new EventDto(null, title, description, eventDate);
        EventDto responseEvent = eventService.createEvent(requestEvent);

        System.out.println("Event created successfully!");
        System.out.println(responseEvent);
    }
}
