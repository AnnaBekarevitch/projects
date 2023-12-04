package ru.itmo.wp.web.page;

        import ru.itmo.wp.model.domain.Article;
        import ru.itmo.wp.model.domain.User;
        import ru.itmo.wp.model.service.ArticleService;
        import ru.itmo.wp.web.exception.RedirectException;

        import javax.servlet.http.HttpServletRequest;
        import java.util.Map;

/** @noinspection unused*/
public class MyArticlesPage {

    private final ArticleService articleService = new ArticleService();

    private void action(HttpServletRequest request, Map<String, Object> view) {
        if (request.getSession().getAttribute("user") == null) {
            request.getSession().setAttribute("message", "You can't open this page without login");
            throw new RedirectException("/index");
        }
        System.out.println(((User)request.getSession().getAttribute("user")).getId());
        view.put("articles", articleService.findByUserId(((User)request.getSession().getAttribute("user")).getId()));
        //view.put("user", request.getSession().getAttribute("user"));
    }


    private void changeHidden(HttpServletRequest request, Map<String, Object> view) {
        if (request.getSession().getAttribute("user") == null) {
            request.getSession().setAttribute("message", "You can't change hidden of your article");
            throw new RedirectException("/index");
        }
        if (Long.parseLong(request.getParameter("userId")) != ((User)request.getSession().getAttribute("user")).getId()) {
            request.getSession().setAttribute("message", "You can't change hidden of your article");
            throw new RedirectException("/index");
        }
        System.out.println("!!!!!!");
        System.out.println(request.getParameter("articleId"));
        articleService.changeHidden(Long.parseLong(request.getParameter("articleId")));
        request.getSession().setAttribute("message", "You change hidden of your article");

    }
}
