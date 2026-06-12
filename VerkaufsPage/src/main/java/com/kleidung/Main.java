package com.kleidung;

import com.kleidung.handler.ImageHandler;
import com.kleidung.handler.ListingHandler;
import com.kleidung.handler.LoginHandler;
import com.kleidung.handler.RegisterHandler;
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
        server.start();
        System.out.println("Server läuft auf Port 8080");
    }
}