package ru.itmo.wp.service;

import org.springframework.stereotype.Service;
import ru.itmo.wp.domain.Tag;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.form.UserCredentials;
import ru.itmo.wp.repository.TagRepository;
@Service
public class TagService {
    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public Tag findByName(String name) {
        return name == null ? null : tagRepository.findTagByName(name);
    }
    public Tag save(Tag tag) {
        tagRepository.save(tag);
        return tag;
    }
}