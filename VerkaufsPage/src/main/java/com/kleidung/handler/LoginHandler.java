package com.kleidung.handler;

import com.kleidung.service.AuthService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

public class LoginHandler implements HttpHandler {

    private final AuthService authService = new AuthService();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equals("POST")) {
            exchange.sendResponseHeaders(405, -1);
            return;
        }

        InputStream is = exchange.getRequestBody();
        String body = new String(is.readAllBytes());

        String username = extractParam(body, "username");
        String password = extractParam(body, "password");

        try {
            String token = authService.login(username, password);
            if (token == null) {
                String response = "Falscher Username oder Passwort";
                exchange.sendResponseHeaders(401, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } else {
                String response = "{\"token\":\"" + token + "\"}";
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(200, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        } catch (SQLException e) {
            String response = "Fehler: " + e.getMessage();
            exchange.sendResponseHeaders(500, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    private String extractParam(String body, String key) {
        for (String param : body.split("&")) {
            String[] pair = param.split("=");
            if (pair[0].equals(key)) return pair[1];
        }
        return "";
    }
}