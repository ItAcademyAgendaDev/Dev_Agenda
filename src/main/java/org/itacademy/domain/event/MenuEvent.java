package org.itacademy.domain.event;

import org.itacademy.domain.event.model.EventDto;
import org.itacademy.domain.event.service.EventService;
import org.itacademy.input.InputReader;

import java.time.LocalDate;

public record MenuEvent (InputReader scanner, EventService eventService) {

    public EventDto createEvent(){
        String title = scanner.readString("Enter event title: ");
        String description = scanner.readYesNo("Do you want to add description") ? scanner.readString("Enter description")
                : "No description";

        System.out.println("Select the event date:");
        LocalDate eventDate = LocalDate.of(
                scanner().readInt("Year: "),
                scanner().readInt("Month: "),
                scanner().readInt("Day: ")
        );

        EventDto requestEvent = new EventDto(null, title, description, eventDate);
        EventDto responseEvent = eventService.createEvent(requestEvent);

        System.out.println("Event created successfully!");
        System.out.println(responseEvent);
        return requestEvent;

    }
    //posibilidad de cambiar la fecha, pero que no te devuelva la de hoy
    public void updateEvent() {
        displayAllEvents();
        Long id = (long) scanner.readInt("Enter the ID of the event to update: ");

        String newTitle = scanner.readString("New title: ");
        String newDesc = scanner.readString("New description: ");
        System.out.println("Enter new date: ");
        LocalDate newDate = LocalDate.of(
                scanner().readInt("Enter new Year: "),
                scanner().readInt("Enter new month: "),
                scanner().readInt("Enter new day: ")
        );

        try {
            EventDto updated = eventService.updateEvent(id, new EventDto(id, newTitle, newDesc, newDate));
            System.out.println("Updated successfully: " + updated);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteEvent() {
        displayAllEvents();
        Long id = (long) scanner.readInt("Enter the ID of the event to delete: ");

        if (scanner.readYesNo("Are you sure you want to delete event #" + id + "?")) {
            try {
                eventService.deleteEvent(id);
                System.out.println("Event deleted successfully!");
            } catch (RuntimeException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }


    public void displayAllEvents() {
        System.out.println("\n--- LIST OF EVENTS ---");
        var events = eventService.findAll();

        if (events.isEmpty()) {
            System.out.println("No events found.");
        } else {
            events.forEach(System.out::println);
        }
    }

    public void getById(Long id) {
        eventService.getById(id);
    }

}
