package com.kleidung.handler;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.kleidung.model.User;
import com.kleidung.repository.UserRepository;
import com.kleidung.util.CorsUtil;
import com.kleidung.util.JwtUtil;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.Base64;
import java.util.UUID;

public class ProfileHandler implements HttpHandler {

    private final UserRepository userRepository = new UserRepository();
    private static final String UPLOAD_DIR = "uploads/profiles/";

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        CorsUtil.addCorsHeaders(exchange);
        if (CorsUtil.handleOptions(exchange)) return;

        String method = exchange.getRequestMethod();

        switch (method) {
            case "GET" -> handleGet(exchange);
            case "PUT" -> handlePut(exchange);
            case "POST" -> handleProfilePicture(exchange);
            default -> exchange.sendResponseHeaders(405, -1);
        }
    }

    private void handleGet(HttpExchange exchange) throws IOException {
        Integer userId = getAuthenticatedUserId(exchange);
        if (userId == null) return;

        try {
            User user = userRepository.findById(userId);
            if (user == null) {
                sendError(exchange, 404, "User nicht gefunden");
                return;
            }

            String json = "{"
                    + "\"id\":" + user.getId() + ","
                    + "\"username\":\"" + user.getUsername() + "\","
                    + "\"email\":\"" + user.getEmail() + "\","
                    + "\"role\":\"" + user.getRole() + "\","
                    + "\"profilePicture\":" + (user.getProfilePicture() != null ? "\"" + user.getProfilePicture() + "\"" : "null")
                    + "}";

            sendJson(exchange, 200, json);
        } catch (SQLException e) {
            sendError(exchange, 500, "Fehler: " + e.getMessage());
        }
    }

    private void handlePut(HttpExchange exchange) throws IOException {
        Integer userId = getAuthenticatedUserId(exchange);
        if (userId == null) return;

        try {
            String body = new String(exchange.getRequestBody().readAllBytes());
            String newUsername = extractParam(body, "username");
            String newEmail = extractParam(body, "email");
            String newPassword = extractParam(body, "password");

            User currentUser = userRepository.findById(userId);
            if (currentUser == null) {
                sendError(exchange, 404, "User nicht gefunden");
                return;
            }

            String pepper = io.github.cdimascio.dotenv.Dotenv.load().get("PEPPER");

            // Prüfen ob neuer Username gleich wie aktueller
            if (!newUsername.isEmpty() && newUsername.equals(currentUser.getUsername())) {
                sendError(exchange, 400, "Neuer Username ist gleich wie der aktuelle");
                return;
            }

            // Prüfen ob neues Passwort gleich wie aktuelles
            if (!newPassword.isEmpty()) {
                BCrypt.Result result = BCrypt.verifyer().verify(
                        (newPassword + currentUser.getSalt() + pepper).toCharArray(),
                        currentUser.getPasswordHash()
                );
                if (result.verified) {
                    sendError(exchange, 400, "Neues Passwort ist gleich wie das aktuelle");
                    return;
                }
            }

            if (!newUsername.isEmpty()) userRepository.updateUsername(userId, newUsername);
            if (!newEmail.isEmpty()) userRepository.updateEmail(userId, newEmail);

            if (!newPassword.isEmpty()) {
                SecureRandom random = new SecureRandom();
                byte[] saltBytes = new byte[16];
                random.nextBytes(saltBytes);
                String newSalt = Base64.getEncoder().encodeToString(saltBytes);
                String hash = BCrypt.withDefaults().hashToString(12, (newPassword + newSalt + pepper).toCharArray());
                userRepository.updatePassword(userId, newSalt, hash);
            }

            sendResponse(exchange, 200, "Profil aktualisiert");
        } catch (SQLException e) {
            if (e.getMessage().contains("unique") || e.getMessage().contains("duplicate")) {
                sendError(exchange, 409, "Username bereits vergeben");
            } else {
                sendError(exchange, 500, "Fehler: " + e.getMessage());
            }
        }
    }

    private void handleProfilePicture(HttpExchange exchange) throws IOException {
        Integer userId = getAuthenticatedUserId(exchange);
        if (userId == null) return;

        try {
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String filename = exchange.getRequestHeaders().getFirst("X-Filename");
            if (filename == null) filename = "profile.jpg";

            String uniqueFilename = UUID.randomUUID() + "_" + filename;
            Path filePath = uploadPath.resolve(uniqueFilename);

            InputStream imageStream = exchange.getRequestBody();
            Files.copy(imageStream, filePath);

            String filepath = UPLOAD_DIR + uniqueFilename;
            userRepository.updateProfilePicture(userId, filepath);

            sendResponse(exchange, 200, "Profilbild hochgeladen");
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