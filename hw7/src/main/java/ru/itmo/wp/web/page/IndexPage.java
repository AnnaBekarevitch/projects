package ru.itmo.wp.web.page;

import com.google.common.base.Strings;
import ru.itmo.wp.model.service.ArticleService;
import ru.itmo.wp.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/** @noinspection unused*/
public class IndexPage {

    private final ArticleService articleService = new ArticleService();



    private void action(HttpServletRequest request, Map<String, Object> view) {

        putMessage(request, view);
    }

    private void findAllShowing(HttpServletRequest request, Map<String, Object> view) {
        view.put("articles", articleService.findAllShowing());

    }


    private void putMessage(HttpServletRequest request, Map<String, Object> view) {
        String message = (String) request.getSession().getAttribute("message");
        if (!Strings.isNullOrEmpty(message)) {
            view.put("message", message);
            request.getSession().removeAttribute("message");
        }
    }
}
