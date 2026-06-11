package com.kleidung.model;

public class Listing {

    private int id;
    private int userId;
    private String name;
    private String zustand;
    private String groesse;
    private double preis;
    private String beschreibung;

    public Listing(int id, int userId, String name, String zustand, String groesse, double preis, String beschreibung) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.zustand = zustand;
        this.groesse = groesse;
        this.preis = preis;
        this.beschreibung = beschreibung;
    }

    public int getId() { return id; }
    public int getUserId() { return userId; }
    public String getName() { return name; }
    public String getZustand() { return zustand; }
    public String getGroesse() { return groesse; }
    public double getPreis() { return preis; }
    public String getBeschreibung() { return beschreibung; }
}