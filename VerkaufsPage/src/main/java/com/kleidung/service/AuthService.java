package com.kleidung.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.kleidung.model.User;
import com.kleidung.repository.UserRepository;
import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Date;

public class AuthService {

    private static final Dotenv dotenv = Dotenv.load();
    private static final String PEPPER = dotenv.get("PEPPER");
    private static final String JWT_SECRET = dotenv.get("JWT_SECRET");
    private final UserRepository userRepository = new UserRepository();

    public void register(String username, String email, String password) throws SQLException {
        SecureRandom random = new SecureRandom();
        byte[] saltBytes = new byte[16];
        random.nextBytes(saltBytes);
        String salt = Base64.getEncoder().encodeToString(saltBytes);

        String passwordWithSaltAndPepper = password + salt + PEPPER;
        String hash = BCrypt.withDefaults().hashToString(12, passwordWithSaltAndPepper.toCharArray());

        User user = new User(0, username, email, salt, hash);
        userRepository.save(user);
    }

    public String login(String username, String password) throws SQLException {
        User user = userRepository.findByUsername(username);
        if (user == null) return null;

        String passwordWithSaltAndPepper = password + user.getSalt() + PEPPER;
        BCrypt.Result result = BCrypt.verifyer().verify(
                passwordWithSaltAndPepper.toCharArray(),
                user.getPasswordHash()
        );

        if (!result.verified) return null;

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 24h
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET)
                .compact();
    }
}