package com.kleidung.handler;

import com.kleidung.service.ImageService;
import com.kleidung.util.JwtUtil;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

public class ImageHandler implements HttpHandler {

    private final ImageService imageService = new ImageService();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equals("POST")) {
            exchange.sendResponseHeaders(405, -1);
            return;
        }

        String authHeader = exchange.getRequestHeaders().getFirst("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            sendError(exchange, 401, "Nicht eingeloggt");
            return;
        }
        String token = authHeader.substring(7);
        if (JwtUtil.validateToken(token) == null) {
            sendError(exchange, 401, "Ungültiger Token");
            return;
        }

        String[] parts = exchange.getRequestURI().getPath().split("/");
        int listingId = Integer.parseInt(parts[parts.length - 2]);

        String filename = exchange.getRequestHeaders().getFirst("X-Filename");
        if (filename == null) filename = "bild.jpg";

        try {
            InputStream imageStream = exchange.getRequestBody();
            imageService.uploadImage(listingId, imageStream, filename);
            sendResponse(exchange, 200, "Bild hochgeladen");
        } catch (SQLException e) {
            sendError(exchange, 500, "Fehler: " + e.getMessage());
        }
    }

    private void sendResponse(HttpExchange exchange, int code, String message) throws IOException {
        exchange.sendResponseHeaders(code, message.length());
        OutputStream os = exchange.getResponseBody();
        os.write(message.getBytes());
        os.close();
    }

    private void sendError(HttpExchange exchange, int code, String message) throws IOException {
        sendResponse(exchange, code, message);
    }
}