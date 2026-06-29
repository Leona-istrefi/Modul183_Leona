package com.kleidung.handler;

import com.kleidung.service.AuthService;
import com.kleidung.util.CorsUtil;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

public class RegisterHandler implements HttpHandler {

    private final AuthService authService = new AuthService();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        CorsUtil.addCorsHeaders(exchange);
        if (CorsUtil.handleOptions(exchange)) return;

        if (!exchange.getRequestMethod().equals("POST")) {
            exchange.sendResponseHeaders(405, -1);
            return;
        }

        InputStream is = exchange.getRequestBody();
        String body = new String(is.readAllBytes());
        System.out.println("Body: " + body);

        String username = extractParam(body, "username");
        String email = extractParam(body, "email");
        String password = extractParam(body, "password");

        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            sendResponse(exchange, 400, "Alle Felder sind Pflichtfelder");
            return;
        }

        if (!email.contains("@")) {
            sendResponse(exchange, 400, "Ungültige Email-Adresse");
            return;
        }

        if (password.length() < 4) {
            sendResponse(exchange, 400, "Passwort muss mindestens 4 Zeichen haben");
            return;
        }

        try {
            authService.register(username, email, password);
            sendResponse(exchange, 200, "Registrierung erfolgreich");
        } catch (SQLException e) {
            if (e.getMessage().contains("unique") || e.getMessage().contains("duplicate")) {
                sendResponse(exchange, 409, "Username oder Email bereits vergeben");
            } else {
                sendResponse(exchange, 500, "Fehler: " + e.getMessage());
            }
        }
    }

    private String extractParam(String body, String key) {
        for (String param : body.split("&")) {
            String[] pair = param.split("=");
            if (pair.length == 2) {
                try {
                    String decodedKey = java.net.URLDecoder.decode(pair[0], "UTF-8").trim();
                    String decodedValue = java.net.URLDecoder.decode(pair[1], "UTF-8").trim();
                    if (decodedKey.equals(key)) return decodedValue;
                } catch (Exception e) {
                    return "";
                }
            }
        }
        return "";
    }

    private void sendResponse(HttpExchange exchange, int code, String message) throws IOException {
        byte[] bytes = message.getBytes("UTF-8");
        exchange.sendResponseHeaders(code, bytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.close();
    }
}