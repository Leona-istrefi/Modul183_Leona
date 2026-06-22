package com.kleidung.model;

public class User {
    private int id;
    private String username;
    private String email;
    private String salt;
    private String passwordHash;
    private String role;
    private String profilePicture;

    public User(int id, String username, String email, String salt, String passwordHash, String role, String profilePicture) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.salt = salt;
        this.passwordHash = passwordHash;
        this.role = role;
        this.profilePicture = profilePicture;
    }

    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getSalt() { return salt; }
    public String getPasswordHash() { return passwordHash; }
    public String getRole() { return role; }
    public String getProfilePicture() { return profilePicture; }
}