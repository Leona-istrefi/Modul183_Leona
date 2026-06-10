package com.kleidung.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.kleidung.model.User;
import com.kleidung.repository.UserRepository;
import io.github.cdimascio.dotenv.Dotenv;

import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.Base64;

public class AuthService {

    private static final Dotenv dotenv = Dotenv.load();
    private static final String PEPPER = dotenv.get("PEPPER");
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
}