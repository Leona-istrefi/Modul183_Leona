package com.kleidung.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.kleidung.model.User;
import com.kleidung.repository.UserRepository;
import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Date;

public class AuthService {

    private static final Dotenv dotenv = Dotenv.load();
    private static final String PEPPER = dotenv.get("PEPPER");
    private static final String JWT_SECRET = dotenv.get("JWT_SECRET");
    private final UserRepository userRepository = new UserRepository();
    private static final Key key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes());

    public void register(String username, String email, String password) throws SQLException {
        SecureRandom random = new SecureRandom();
        byte[] saltBytes = new byte[16];
        random.nextBytes(saltBytes);
        String salt = Base64.getEncoder().encodeToString(saltBytes);

        String passwordWithSaltAndPepper = password + salt + PEPPER;
        String hash = BCrypt.withDefaults().hashToString(12, passwordWithSaltAndPepper.toCharArray());

        User user = new User(0, username, email, salt, hash, "user");
        userRepository.save(user);
    }

    public String login(String usernameOrEmail, String password) throws SQLException {
        System.out.println("Login versucht für: " + usernameOrEmail);

        User user = userRepository.findByUsername(usernameOrEmail);

        if (user == null) {
            user = userRepository.findByEmail(usernameOrEmail);
        }

        System.out.println("User gefunden: " + (user != null));
        if (user == null) return null;

        String passwordWithSaltAndPepper = password + user.getSalt() + PEPPER;
        System.out.println("Passwort kombiniert");
        BCrypt.Result result = BCrypt.verifyer().verify(
                passwordWithSaltAndPepper.toCharArray(),
                user.getPasswordHash()
        );
        System.out.println("BCrypt result: " + result.verified);

        if (!result.verified) return null;

        System.out.println("Erstelle JWT Token...");
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("role", user.getRole())
                .claim("userId", user.getId())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(key)
                .compact();
    }
}