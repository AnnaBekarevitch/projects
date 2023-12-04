package ru.itmo.wp.model.service;

import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.repository.ArticleRepository;
import ru.itmo.wp.model.repository.UserRepository;
import ru.itmo.wp.model.repository.impl.ArticleRepositoryImpl;
import ru.itmo.wp.model.repository.impl.UserRepositoryImpl;

import java.util.List;

public class ArticleService {
    private final ArticleRepository articleRepository = new ArticleRepositoryImpl();

    public void create(Article article, User user) {

        articleRepository.save(article, user);
    }
    public void changeHidden(long id) {

        articleRepository.update(id);
    }


    public List<Article> findAll() {
        return articleRepository.findAll();
    }


    public List<Article> findAllShowing() {
        return articleRepository.findAllShowing();
    }
    public List<Article> findByUserId(long id) {
        return articleRepository.findByUserId(id);
    }
}
