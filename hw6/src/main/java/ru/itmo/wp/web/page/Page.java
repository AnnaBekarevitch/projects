package ru.itmo.wp.web.page;
import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.repository.UserRepository;
import ru.itmo.wp.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class Page {
    private HttpServletRequest request;
    private final UserService userService = new UserService();

    protected void action(HttpServletRequest request, Map<String, Object> view) {
        // No operations.
    }

    public void before(HttpServletRequest request, Map<String, Object> view) {
        this.request = request;
        int userCount = userService.findCount();
        view.put("userCount", userCount);
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            view.put("user", user);
        }
        String message = (String) request.getSession().getAttribute("message");
        if (message != null) {
            view.put("message", message);
            request.getSession().removeAttribute("message");
        }

    }

    public void after(HttpServletRequest request, Map<String, Object> view) {

    }
    void setMessage(String message) {
        request.getSession().setAttribute("message", message);
    }
    public String getMessage() {
        return (String)request.getSession().getAttribute("message");
    }
    public void setUser(User user) {
        request.getSession().setAttribute("user", user);
    }


    public User getUser() {
        return (User) request.getSession().getAttribute("user");
    }

}
