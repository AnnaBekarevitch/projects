package ru.itmo.wp.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

import java.util.stream.Collectors;


public class SessionServlet extends HttpServlet {

    private ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
    private void writeResponse(HttpServletResponse response, Object json) throws IOException {
        response.setContentType("application/json");
        response.getWriter().print(new Gson().toJson(json));
        response.getWriter().flush();
    }
    @Override
    protected  void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        HttpSession session = request.getSession();
        if (uri.endsWith("auth")) {
            if (request.getParameter("user") != null
                    && session.getAttribute("user") == null) {
                session.setAttribute("user",
                        request.getParameter("user").trim());
            }
            writeResponse(response, session.getAttribute("user"));
        }
        else if (uri.endsWith("findAll")) {
            writeResponse(response, data);
        }
        else if (uri.endsWith("add")) {
            Object text = request.getParameter("text").trim().length() == 0
                            ? null
                            : request.getParameter("text");
            HashMap<String, String> packet = new HashMap<>();
            packet.put("user", (String) session.getAttribute("user"));
            packet.put("text", (String) text);
            data.add(packet);
            writeResponse(response, text);
        }

    }

}
