package com.kleidung.handler;

import com.kleidung.service.AuthService;
import com.kleidung.util.CorsUtil;
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
        try {
            CorsUtil.addCorsHeaders(exchange);
            if (CorsUtil.handleOptions(exchange)) return;
            if (!exchange.getRequestMethod().equals("POST")) {
                exchange.sendResponseHeaders(405, -1);
                return;
            }

            InputStream is = exchange.getRequestBody();
            String body = new String(is.readAllBytes());
            System.out.println("Login Body: " + body);

            String username = extractParam(body, "username");
            String password = extractParam(body, "password");
            System.out.println("Username: " + username);
            System.out.println("Password: " + password);

            String token = authService.login(username, password);
            System.out.println("Token erhalten: " + token);
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