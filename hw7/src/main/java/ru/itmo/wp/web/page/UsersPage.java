package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.service.UserService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/** @noinspection unused*/
public class UsersPage {
    private final UserService userService = new UserService();

    private void action(HttpServletRequest request, Map<String, Object> view) {

        // No operations.
    }

    private void findAll(HttpServletRequest request, Map<String, Object> view) {
        view.put("users", userService.findAll());
    }

    private void findUser(HttpServletRequest request, Map<String, Object> view) {

        view.put("user",
                userService.find(Long.parseLong(request.getParameter("userId"))));
    }

    private void changeStatus(HttpServletRequest request, Map<String, Object> view) {
        if (request.getSession().getAttribute("user") == null) {
            request.getSession().setAttribute("message", "You can't do nothing without login");
            throw new RedirectException("/index");
        }
        if (!((User)request.getSession().getAttribute("user")).isAdmin()) {
            request.getSession().setAttribute("message", "You must be admin");
            throw new RedirectException("/index");
        }

        userService.changeStatus(Long.parseLong(request.getParameter("userId")));
User user = userService.find(((User)request.getSession().getAttribute("user")).getId());
        if (Long.parseLong(request.getParameter("userId")) == user.getId()
                && !user.isAdmin()) {
            request.getSession().setAttribute("message", "You are not admin now");
            request.getSession().setAttribute("user", user);
            throw new RedirectException("/index");


        }
    }
}
