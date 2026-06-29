package com.kleidung;

import com.kleidung.handler.*;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/register", new RegisterHandler());
        server.createContext("/login", new LoginHandler());
        server.createContext("/image", new ImageHandler());
        server.createContext("/listings", new ListingHandler());
        server.createContext("/uploads", new StaticFileHandler());
        server.createContext("/favorites", new FavoriteHandler());
        server.createContext("/profile", new ProfileHandler());
        server.createContext("/cart", new CartHandler());
        server.start();
        System.out.println("Server läuft auf Port 8080");
    }
}