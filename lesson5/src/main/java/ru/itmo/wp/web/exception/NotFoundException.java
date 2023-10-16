package ru.itmo.wp.web.exception;

import javax.servlet.http.HttpServletRequest;

public class NotFoundException extends Exception {
    private void action(HttpServletRequest request) {
        // No operations.
    }
}
