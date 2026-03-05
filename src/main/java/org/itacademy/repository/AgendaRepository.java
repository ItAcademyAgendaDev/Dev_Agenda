package org.itacademy.repository;

import org.itacademy.model.Agenda;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AgendaRepository {

    Agenda save(Agenda agenda);

    Optional<Agenda> findById(Long id);

    List<Agenda> findAll();

    Agenda update(Agenda agenda);

    void delete(Long id);
}
 