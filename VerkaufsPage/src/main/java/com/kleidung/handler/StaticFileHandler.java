package com.kleidung.handler;

import com.kleidung.util.CorsUtil;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

public class StaticFileHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        CorsUtil.addCorsHeaders(exchange);
        if (CorsUtil.handleOptions(exchange)) return;

        String path = exchange.getRequestURI().getPath().replaceFirst("/uploads/", "");
        File file = new File("uploads", path);

        if (!file.exists()) {
            exchange.sendResponseHeaders(404, -1);
            return;
        }

        byte[] bytes = Files.readAllBytes(file.toPath());
        exchange.getResponseHeaders().set("Content-Type", "image/jpeg");
        exchange.sendResponseHeaders(200, bytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.close();
    }
}