package org.itacademy.domain.event.model;

import java.time.LocalDate;

public record EventDto(Long id, String title, String description, LocalDate eventDate) {}
