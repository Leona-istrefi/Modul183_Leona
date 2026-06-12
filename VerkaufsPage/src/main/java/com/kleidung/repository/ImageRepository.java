package com.kleidung.repository;

import com.kleidung.DatabaseConnection;
import com.kleidung.model.Image;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ImageRepository {

    public void save(Image image) throws SQLException {
        String sql = "INSERT INTO images (listing_id, filepath) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, image.getListingId());
            stmt.setString(2, image.getFilepath());
            stmt.executeUpdate();
        }
    }

    public Image findByListingId(int listingId) throws SQLException {
        String sql = "SELECT * FROM images WHERE listing_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, listingId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Image(
                        rs.getInt("id"),
                        rs.getInt("listing_id"),
                        rs.getString("filepath")
                );
            }
        }
        return null;
    }
}