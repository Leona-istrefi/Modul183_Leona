package com.kleidung.repository;

import com.kleidung.DatabaseConnection;
import com.kleidung.model.Listing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FavoriteRepository {

    public void add(int userId, int listingId) throws SQLException {
        String sql = "INSERT INTO favorites (user_id, listing_id) VALUES (?, ?) ON CONFLICT DO NOTHING";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, listingId);
            stmt.executeUpdate();
        }
    }

    public void remove(int userId, int listingId) throws SQLException {
        String sql = "DELETE FROM favorites WHERE user_id = ? AND listing_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, listingId);
            stmt.executeUpdate();
        }
    }

    public boolean isFavorite(int userId, int listingId) throws SQLException {
        String sql = "SELECT 1 FROM favorites WHERE user_id = ? AND listing_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, listingId);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }

    public List<Integer> getFavoriteListingIds(int userId) throws SQLException {
        String sql = "SELECT listing_id FROM favorites WHERE user_id = ?";
        List<Integer> ids = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ids.add(rs.getInt("listing_id"));
            }
        }
        return ids;
    }

    public List<Listing> getFavoriteListings(int userId) throws SQLException {
        String sql = "SELECT l.* FROM listings l " +
                "JOIN favorites f ON l.id = f.listing_id " +
                "WHERE f.user_id = ?";
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
}