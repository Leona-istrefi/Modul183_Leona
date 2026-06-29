package com.kleidung.service;

import com.kleidung.model.Listing;
import com.kleidung.repository.CartRepository;
import com.kleidung.repository.ListingRepository;

import java.sql.SQLException;
import java.util.List;

public class CartService {

    private final CartRepository cartRepository = new CartRepository();
    private final ListingRepository listingRepository = new ListingRepository();

    public void addToCart(int userId, int listingId) throws SQLException {
        cartRepository.add(userId, listingId);
    }

    public void removeFromCart(int userId, int listingId) throws SQLException {
        cartRepository.remove(userId, listingId);
    }

    public List<Listing> getCart(int userId) throws SQLException {
        return cartRepository.getCartListings(userId);
    }

    public boolean isInCart(int userId, int listingId) throws SQLException {
        return cartRepository.isInCart(userId, listingId);
    }

    public void purchase(int userId, int listingId) throws SQLException {
        cartRepository.remove(userId, listingId);
        listingRepository.delete(listingId);
    }
}