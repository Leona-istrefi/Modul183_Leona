package com.kleidung.model;

public class Favorite {

    private int id;
    private int userId;
    private int listingId;

    public Favorite(int id, int userId, int listingId) {
        this.id = id;
        this.userId = userId;
        this.listingId = listingId;
    }

    public int getId() { return id; }
    public int getUserId() { return userId; }
    public int getListingId() { return listingId; }
}