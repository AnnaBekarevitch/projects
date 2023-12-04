package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.ArticleService;
import ru.itmo.wp.model.service.UserService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class ArticlePage {
    private final ArticleService articleService = new ArticleService();

    private void action(HttpServletRequest request, Map<String, Object> view) {
        if (request.getSession().getAttribute("user") == null) {
            throw new RedirectException("/index");
        }
        // No operations.
    }


    private void create(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        if (request.getSession().getAttribute("user") == null) {
            throw new RedirectException("/index");
        }
        Article article = new Article();

        User user = (User) request.getSession().getAttribute("user");
        article.setTitle(request.getParameter("title"));
        article.setText(request.getParameter("text"));
        System.out.println(request.getParameter("hidden"));
        article.setHidden(Boolean.valueOf(request.getParameter("hidden")));
        System.out.println(user.getId());
        article.setUserLogin(user.getLogin());
        articleService.create(article, user);
        request.getSession().setAttribute("message", "You created new article");
        //request.getSession().setAttribute("user", user);
        throw new RedirectException("/index");
    }
}
