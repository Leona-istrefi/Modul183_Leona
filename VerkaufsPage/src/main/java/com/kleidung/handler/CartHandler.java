package com.kleidung.handler;

import com.kleidung.model.Listing;
import com.kleidung.service.CartService;
import com.kleidung.util.CorsUtil;
import com.kleidung.util.JwtUtil;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.List;

public class CartHandler implements HttpHandler {

    private final CartService cartService = new CartService();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        CorsUtil.addCorsHeaders(exchange);
        if (CorsUtil.handleOptions(exchange)) return;

        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();

        if (method.equals("POST") && path.contains("/buy")) {
            handleBuy(exchange);
            return;
        }

        switch (method) {
            case "GET" -> handleGet(exchange);
            case "POST" -> handleAdd(exchange);
            case "DELETE" -> handleRemove(exchange);
            default -> exchange.sendResponseHeaders(405, -1);
        }
    }

    private void handleGet(HttpExchange exchange) throws IOException {
        Integer userId = getAuthenticatedUserId(exchange);
        if (userId == null) return;

        try {
            List<Listing> listings = cartService.getCart(userId);
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
                        .append("\"beschreibung\":\"").append(l.getBeschreibung()).append("\",")
                        .append("\"isPublic\":").append(l.isPublic())
                        .append("}");
                if (i < listings.size() - 1) json.append(",");
            }
            json.append("]");
            sendJson(exchange, 200, json.toString());
        } catch (SQLException e) {
            sendError(exchange, 500, "Fehler: " + e.getMessage());
        }
    }

    private void handleAdd(HttpExchange exchange) throws IOException {
        Integer userId = getAuthenticatedUserId(exchange);
        if (userId == null) return;

        try {
            String[] parts = exchange.getRequestURI().getPath().split("/");
            int listingId = Integer.parseInt(parts[parts.length - 1]);
            cartService.addToCart(userId, listingId);
            sendResponse(exchange, 200, "Zum Warenkorb hinzugefügt");
        } catch (SQLException e) {
            sendError(exchange, 500, "Fehler: " + e.getMessage());
        }
    }

    private void handleRemove(HttpExchange exchange) throws IOException {
        Integer userId = getAuthenticatedUserId(exchange);
        if (userId == null) return;

        try {
            String[] parts = exchange.getRequestURI().getPath().split("/");
            int listingId = Integer.parseInt(parts[parts.length - 1]);
            cartService.removeFromCart(userId, listingId);
            sendResponse(exchange, 200, "Aus Warenkorb entfernt");
        } catch (SQLException e) {
            sendError(exchange, 500, "Fehler: " + e.getMessage());
        }
    }

    private void handleBuy(HttpExchange exchange) throws IOException {
        Integer userId = getAuthenticatedUserId(exchange);
        if (userId == null) return;

        try {
            String[] parts = exchange.getRequestURI().getPath().split("/");
            int listingId = Integer.parseInt(parts[parts.length - 2]);
            cartService.purchase(userId, listingId);
            sendResponse(exchange, 200, "Gekauft");
        } catch (SQLException e) {
            sendError(exchange, 500, "Fehler: " + e.getMessage());
        }
    }

    private Integer getAuthenticatedUserId(HttpExchange exchange) throws IOException {
        String authHeader = exchange.getRequestHeaders().getFirst("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            sendError(exchange, 401, "Nicht eingeloggt");
            return null;
        }
        Integer userId = JwtUtil.getUserIdFromToken(authHeader.substring(7));
        if (userId == null) {
            sendError(exchange, 401, "Ungültiger Token");
            return null;
        }
        return userId;
    }

    private void sendJson(HttpExchange exchange, int code, String json) throws IOException {
        byte[] bytes = json.getBytes("UTF-8");
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(code, bytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.close();
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