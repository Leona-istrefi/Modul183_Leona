package com.kleidung.model;

public class Image {

    private int id;
    private int listingId;
    private String filepath;

    public Image(int id, int listingId, String filepath) {
        this.id = id;
        this.listingId = listingId;
        this.filepath = filepath;
    }

    public int getId() { return id; }
    public int getListingId() { return listingId; }
    public String getFilepath() { return filepath; }
}