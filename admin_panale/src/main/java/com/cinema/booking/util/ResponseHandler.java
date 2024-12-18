package com.cinema.booking.util;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.google.gson.Gson;

public class ResponseHandler {
    private static final Gson gson = new Gson();

    public static void sendJsonResponse(HttpServletResponse response, Object data) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(gson.toJson(data));
    }

    public static void sendErrorResponse(HttpServletResponse response, String message, int status) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(gson.toJson(new ErrorResponse(message)));
    }

    private static class ErrorResponse {
        private final String error;

        public ErrorResponse(String error) {
            this.error = error;
        }
    }
}
