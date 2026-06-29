package com.kleidung.repository;

import com.kleidung.DatabaseConnection;
import com.kleidung.model.Listing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ListingRepository {

    public void save(Listing listing) throws SQLException {
        String sql = "INSERT INTO listings (user_id, name, zustand, groesse, preis, beschreibung, is_public) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, listing.getUserId());
            stmt.setString(2, listing.getName());
            stmt.setString(3, listing.getZustand());
            stmt.setString(4, listing.getGroesse());
            stmt.setDouble(5, listing.getPreis());
            stmt.setString(6, listing.getBeschreibung());
            stmt.setBoolean(7, listing.isPublic());
            stmt.executeUpdate();
        }
    }

    public List<Listing> findAll() throws SQLException {
        return queryList("SELECT * FROM listings", null);
    }

    public List<Listing> findVisible(Integer currentUserId) throws SQLException {
        String sql = currentUserId == null
                ? "SELECT * FROM listings WHERE is_public = true"
                : "SELECT * FROM listings WHERE is_public = true OR user_id = ?";
        return queryList(sql, currentUserId);
    }

    private List<Listing> queryList(String sql, Integer currentUserId) throws SQLException {
        List<Listing> listings = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            if (currentUserId != null) {
                stmt.setInt(1, currentUserId);
            }
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                listings.add(mapRow(rs));
            }
        }
        return listings;
    }

    public Listing findById(int id) throws SQLException {
        String sql = "SELECT * FROM listings WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return mapRow(rs);
        }
        return null;
    }

    public void update(Listing listing) throws SQLException {
        String sql = "UPDATE listings SET name=?, zustand=?, groesse=?, preis=?, beschreibung=?, is_public=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, listing.getName());
            stmt.setString(2, listing.getZustand());
            stmt.setString(3, listing.getGroesse());
            stmt.setDouble(4, listing.getPreis());
            stmt.setString(5, listing.getBeschreibung());
            stmt.setBoolean(6, listing.isPublic());
            stmt.setInt(7, listing.getId());
            stmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String deleteImages = "DELETE FROM images WHERE listing_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(deleteImages)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }

        String deleteListing = "DELETE FROM listings WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(deleteListing)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    private Listing mapRow(ResultSet rs) throws SQLException {
        return new Listing(
                rs.getInt("id"),
                rs.getInt("user_id"),
                rs.getString("name"),
                rs.getString("zustand"),
                rs.getString("groesse"),
                rs.getDouble("preis"),
                rs.getString("beschreibung"),
                rs.getBoolean("is_public")
        );
    }
}