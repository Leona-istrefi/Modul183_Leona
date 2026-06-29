package com.kleidung.repository;

import com.kleidung.DatabaseConnection;
import com.kleidung.model.Listing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CartRepository {

    public void add(int userId, int listingId) throws SQLException {
        String sql = "INSERT INTO cart (user_id, listing_id) VALUES (?, ?) ON CONFLICT DO NOTHING";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, listingId);
            stmt.executeUpdate();
        }
    }

    public void remove(int userId, int listingId) throws SQLException {
        String sql = "DELETE FROM cart WHERE user_id = ? AND listing_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, listingId);
            stmt.executeUpdate();
        }
    }

    public List<Listing> getCartListings(int userId) throws SQLException {
        String sql = "SELECT l.* FROM listings l JOIN cart c ON l.id = c.listing_id WHERE c.user_id = ?";
        List<Listing> listings = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                listings.add(new Listing(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("name"),
                        rs.getString("zustand"),
                        rs.getString("groesse"),
                        rs.getDouble("preis"),
                        rs.getString("beschreibung"),
                        rs.getBoolean("is_public")
                ));
            }
        }
        return listings;
    }

    public boolean isInCart(int userId, int listingId) throws SQLException {
        String sql = "SELECT 1 FROM cart WHERE user_id = ? AND listing_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, listingId);
            return stmt.executeQuery().next();
        }
    }
}