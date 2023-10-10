package ru.itmo.wp.servlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CaptchaServlet  extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uri = request.getRequestURI();
        HttpSession session = request.getSession();
        Object value = request.getParameter("captcha");
        Object correct = session.getAttribute("correct");
        if (correct != null && value != null) {
            if (correct.equals(value)) {
                session.setAttribute("passed", true);
                response.sendRedirect("/index.html");
                session.setAttribute("correct", null);
            }
        }
    }
}
