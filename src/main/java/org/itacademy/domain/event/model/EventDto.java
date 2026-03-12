package org.itacademy.domain.event.model;

import java.time.LocalDate;

public record EventDto(Long id, String title, String description, LocalDate eventDate) {

    @Override
    public String toString() {
        return String.format(
                """
                
                EVENT [#%s]
                ------------------------------------------
                Title:       %s
                Date:        %s
                Description: %s
                ------------------------------------------
                """,
                (id != null ? id : "NEW"), // Si es antes de guardar, ponemos "NEW"
                title,
                eventDate,
                (description != null && !description.isBlank() ? description : "No description")
        );
    }
}
