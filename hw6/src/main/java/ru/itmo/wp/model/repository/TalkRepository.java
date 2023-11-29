package ru.itmo.wp.model.repository;

import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.domain.Talk;

import java.util.List;

public interface TalkRepository {
    void save(Talk talk);
    List<Talk> findByUserId(long sourceUserId);

}