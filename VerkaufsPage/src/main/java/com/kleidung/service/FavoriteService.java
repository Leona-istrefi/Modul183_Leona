package com.kleidung.service;

import com.kleidung.model.Listing;
import com.kleidung.repository.FavoriteRepository;

import java.sql.SQLException;
import java.util.List;

public class FavoriteService {

    private final FavoriteRepository favoriteRepository = new FavoriteRepository();

    public void toggle(int userId, int listingId) throws SQLException {
        if (favoriteRepository.isFavorite(userId, listingId)) {
            favoriteRepository.remove(userId, listingId);
        } else {
            favoriteRepository.add(userId, listingId);
        }
    }

    public List<Integer> getFavoriteIds(int userId) throws SQLException {
        return favoriteRepository.getFavoriteListingIds(userId);
    }

    public List<Listing> getFavoriteListings(int userId) throws SQLException {
        return favoriteRepository.getFavoriteListings(userId);
    }
}