package com.kleidung.handler;

import com.kleidung.model.Image;
import com.kleidung.service.ImageService;
import com.kleidung.util.CorsUtil;
import com.kleidung.util.JwtUtil;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ImageHandler implements HttpHandler {

    private final ImageService imageService = new ImageService();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        CorsUtil.addCorsHeaders(exchange);
        if (CorsUtil.handleOptions(exchange)) return;

        try {
            String method = exchange.getRequestMethod();

            if (method.equals("GET")) {
                handleGet(exchange);
                return;
            }

            if (!method.equals("POST")) {
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
            int listingId = Integer.parseInt(parts[parts.length - 1]);

            String filename = exchange.getRequestHeaders().getFirst("X-Filename");
            if (filename == null) filename = "bild.jpg";

            InputStream imageStream = exchange.getRequestBody();
            imageService.uploadImage(listingId, imageStream, filename);
            sendResponse(exchange, 200, "Bild hochgeladen");

        } catch (Exception e) {
            sendError(exchange, 500, "Fehler: " + e.getMessage());
        }
    }

    private void handleGet(HttpExchange exchange) throws IOException {
        try {
            String[] parts = exchange.getRequestURI().getPath().split("/");
            int listingId = Integer.parseInt(parts[parts.length - 1]);

            Image image = imageService.getByListingId(listingId);
            String response = image == null
                    ? "{\"filepath\":null}"
                    : "{\"filepath\":\"" + image.getFilepath() + "\"}";

            byte[] bytes = response.getBytes("UTF-8");
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, bytes.length);
            OutputStream os = exchange.getResponseBody();
            os.write(bytes);
            os.close();
        } catch (Exception e) {
            sendError(exchange, 500, "Fehler: " + e.getMessage());
        }
    }

    private void sendResponse(HttpExchange exchange, int code, String message) throws IOException {
        byte[] bytes = message.getBytes("UTF-8");
        exchange.sendResponseHeaders(code, bytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.close();
    }

    private void sendError(HttpExchange exchange, int code, String message) throws IOException {
        sendResponse(exchange, code, message);
    }
}