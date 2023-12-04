package ru.itmo.wp.model.service;

import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.repository.EventRepository;
import ru.itmo.wp.model.repository.impl.EventRepositoryImpl;

public class EventService {

    private final EventRepository eventRepository = new EventRepositoryImpl(Event.class);

    public void save(Event event) {
        eventRepository.save(event);
    }

}
