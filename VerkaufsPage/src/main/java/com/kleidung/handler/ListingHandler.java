package com.kleidung.handler;

import com.kleidung.model.Listing;
import com.kleidung.repository.UserRepository;
import com.kleidung.service.ListingService;
import com.kleidung.util.JwtUtil;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.List;

public class ListingHandler implements HttpHandler {

    private final ListingService listingService = new ListingService();
    private final UserRepository userRepository = new UserRepository();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();

        switch (method) {
            case "GET" -> handleGet(exchange);
            case "POST" -> handlePost(exchange);
            case "PUT" -> handlePut(exchange);
            case "DELETE" -> handleDelete(exchange);
            default -> exchange.sendResponseHeaders(405, -1);
        }
    }

    private void handleGet(HttpExchange exchange) throws IOException {
        try {
            List<Listing> listings = listingService.getAll();
            StringBuilder json = new StringBuilder("[");
            for (int i = 0; i < listings.size(); i++) {
                Listing l = listings.get(i);
                json.append("{")
                        .append("\"id\":").append(l.getId()).append(",")
                        .append("\"userId\":").append(l.getUserId()).append(",")
                        .append("\"name\":\"").append(l.getName()).append("\",")
                        .append("\"zustand\":\"").append(l.getZustand()).append("\",")
                        .append("\"groesse\":\"").append(l.getGroesse()).append("\",")
                        .append("\"preis\":").append(l.getPreis()).append(",")
                        .append("\"beschreibung\":\"").append(l.getBeschreibung()).append("\"")
                        .append("}");
                if (i < listings.size() - 1) json.append(",");
            }
            json.append("]");
            String response = json.toString();
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } catch (SQLException e) {
            sendError(exchange, 500, "Fehler: " + e.getMessage());
        }
    }

    private void handlePost(HttpExchange exchange) throws IOException {
        String username = getAuthenticatedUser(exchange);
        if (username == null) return;

        try {
            String body = new String(exchange.getRequestBody().readAllBytes());
            String name = extractParam(body, "name");
            String zustand = extractParam(body, "zustand");
            String groesse = extractParam(body, "groesse");
            double preis = Double.parseDouble(extractParam(body, "preis"));
            String beschreibung = extractParam(body, "beschreibung");

            int userId = userRepository.findByUsername(username).getId();
            listingService.create(userId, name, zustand, groesse, preis, beschreibung);
            sendResponse(exchange, 200, "Inserat erstellt");
        } catch (SQLException e) {
            sendError(exchange, 500, "Fehler: " + e.getMessage());
        }
    }

    private void handlePut(HttpExchange exchange) throws IOException {
        String username = getAuthenticatedUser(exchange);
        if (username == null) return;

        try {
            String[] parts = exchange.getRequestURI().getPath().split("/");
            int id = Integer.parseInt(parts[parts.length - 1]);

            Listing existing = listingService.getById(id);
            if (existing == null) {
                sendError(exchange, 404, "Inserat nicht gefunden");
                return;
            }

            String token = exchange.getRequestHeaders().getFirst("Authorization").substring(7);
            String role = JwtUtil.getRoleFromToken(token);
            int userId = userRepository.findByUsername(username).getId();

            if (!role.equals("admin") && existing.getUserId() != userId) {
                sendError(exchange, 403, "Keine Berechtigung");
                return;
            }

            String body = new String(exchange.getRequestBody().readAllBytes());
            Listing updated = new Listing(
                    id,
                    existing.getUserId(),
                    extractParam(body, "name"),
                    extractParam(body, "zustand"),
                    extractParam(body, "groesse"),
                    Double.parseDouble(extractParam(body, "preis")),
                    extractParam(body, "beschreibung")
            );
            listingService.update(updated);
            sendResponse(exchange, 200, "Inserat aktualisiert");
        } catch (SQLException e) {
            sendError(exchange, 500, "Fehler: " + e.getMessage());
        }
    }

    private void handleDelete(HttpExchange exchange) throws IOException {
        String username = getAuthenticatedUser(exchange);
        if (username == null) return;

        try {
            String[] parts = exchange.getRequestURI().getPath().split("/");
            int id = Integer.parseInt(parts[parts.length - 1]);

            Listing existing = listingService.getById(id);
            if (existing == null) {
                sendError(exchange, 404, "Inserat nicht gefunden");
                return;
            }

            String token = exchange.getRequestHeaders().getFirst("Authorization").substring(7);
            String role = JwtUtil.getRoleFromToken(token);
            int userId = userRepository.findByUsername(username).getId();

            if (!role.equals("admin") && existing.getUserId() != userId) {
                sendError(exchange, 403, "Keine Berechtigung");
                return;
            }

            listingService.delete(id);
            sendResponse(exchange, 200, "Inserat gelöscht");
        } catch (SQLException e) {
            sendError(exchange, 500, "Fehler: " + e.getMessage());
        }
    }

    private String getAuthenticatedUser(HttpExchange exchange) throws IOException {
        String authHeader = exchange.getRequestHeaders().getFirst("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            sendError(exchange, 401, "Nicht eingeloggt");
            return null;
        }
        String token = authHeader.substring(7);
        String username = JwtUtil.validateToken(token);
        if (username == null) {
            sendError(exchange, 401, "Ungültiger Token");
            return null;
        }
        return username;
    }

    private String extractParam(String body, String key) {
        for (String param : body.split("&")) {
            String[] pair = param.split("=");
            if (pair[0].equals(key)) return pair[1];
        }
        return "";
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