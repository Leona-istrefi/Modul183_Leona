package com.kleidung.handler;

import com.kleidung.service.ImageService;
import com.kleidung.util.JwtUtil;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.kleidung.util.CorsUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

public class ImageHandler implements HttpHandler {

    private final ImageService imageService = new ImageService();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            System.out.println("ImageHandler aufgerufen");
            CorsUtil.addCorsHeaders(exchange);
            if (CorsUtil.handleOptions(exchange)) return;

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
            System.out.println("Path parts: " + parts.length);
            int listingId = Integer.parseInt(parts[parts.length - 1]);
            System.out.println("Listing ID: " + listingId);

            String filename = exchange.getRequestHeaders().getFirst("X-Filename");
            if (filename == null) filename = "bild.jpg";
            System.out.println("Filename: " + filename);

            InputStream imageStream = exchange.getRequestBody();
            imageService.uploadImage(listingId, imageStream, filename);
            sendResponse(exchange, 200, "Bild hochgeladen");

        } catch (Exception e) {
            System.out.println("Fehler: " + e.getMessage());
            e.printStackTrace();
            String response = "Fehler: " + e.getMessage();
            exchange.sendResponseHeaders(500, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
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