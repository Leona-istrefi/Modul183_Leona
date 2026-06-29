package com.kleidung.service;

import com.kleidung.model.Listing;
import com.kleidung.repository.ListingRepository;

import java.sql.SQLException;
import java.util.List;

public class ListingService {

    private final ListingRepository listingRepository = new ListingRepository();

    public void create(int userId, String name, String zustand, String groesse, double preis, String beschreibung, boolean isPublic) throws SQLException {
        Listing listing = new Listing(0, userId, name, zustand, groesse, preis, beschreibung, isPublic);
        listingRepository.save(listing);
    }

    public List<Listing> getAll() throws SQLException {
        return listingRepository.findAll();
    }

    public List<Listing> getVisible(Integer currentUserId) throws SQLException {
        return listingRepository.findVisible(currentUserId);
    }

    public Listing getById(int id) throws SQLException {
        return listingRepository.findById(id);
    }

    public void update(Listing listing) throws SQLException {
        listingRepository.update(listing);
    }

    public void delete(int id) throws SQLException {
        listingRepository.delete(id);
    }
}