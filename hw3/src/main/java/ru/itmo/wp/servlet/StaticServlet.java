package ru.itmo.wp.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class StaticServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uri = request.getRequestURI();
        Path absolute_path = Paths.get(getServletContext().getRealPath("")).getParent().getParent();
        boolean setContextType = false;
        for (String fileName : uri.split("\\+")) {
            File file = Paths.get(absolute_path.toString(),
                    "src", "main", "webapp", "static", fileName).toFile();
            if (!file.isFile()) {
                file = new File(getServletContext().getRealPath("/static/" + fileName));
            }
            if (!file.isFile()) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                continue;
            }
            if (!setContextType) {
                response.setContentType(getServletContext().getMimeType(file.getName()));
                setContextType = true;
            }
            try (OutputStream outputStream = response.getOutputStream()) {
                Files.copy(file.toPath(), outputStream);
                outputStream.flush();
            }
        }
    }
}
