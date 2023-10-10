package ru.itmo.wp.servlet;
import ru.itmo.wp.util.ImageUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;
import java.util.concurrent.ThreadLocalRandom;
import javax.servlet.http.HttpSession;


public class CaptchaFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println(request.getMethod());
        if(!request.getMethod().equals("GET")){
            chain.doFilter(request, response);
        }
        HttpSession session = request.getSession();
        String captchaURI = request.getContextPath() + "/captcha.html";
        boolean passed = session != null && session.getAttribute("passed") != null;
        boolean passRequest = request.getRequestURI().equals(captchaURI);
        if (session.getAttribute("correct") == null) {
            String randomNum = String.valueOf(ThreadLocalRandom.current().nextInt(100, 1000));
            session.setAttribute("correct", randomNum);
        }
        if (passRequest) {
            byte[] img = ImageUtils.toPng((String) session.getAttribute("correct"));
            response.getOutputStream().print("<img src=\"data:image/png;base64," + Base64.getEncoder().encodeToString(img) + "\">");
        }
        if (passed || passRequest) {
            chain.doFilter(request, response);
        } else {
            response.sendRedirect(captchaURI);
        }
    }


}
