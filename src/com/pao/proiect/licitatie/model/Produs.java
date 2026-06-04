package com.pao.proiect.licitatie.model;

public class Produs implements Comparable<Produs> {
    private int id;
    private String nume;
    private double pretPornire;
    private int vanzatorId;

    public Produs(int id, String nume, double pretPornire, int vanzatorId) {
        this.id = id; this.nume = nume; this.pretPornire = pretPornire; this.vanzatorId = vanzatorId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNume() { return nume; }
    public double getPretPornire() { return pretPornire; }
    public void setPretPornire(double pretPornire) { this.pretPornire = pretPornire; }
    public int getVanzatorId() { return vanzatorId; }

    @Override
    public int compareTo(Produs o) { return Double.compare(this.pretPornire, o.pretPornire); }

    @Override
    public String toString() { return "Produs ID " + id + ": " + nume + " | Pret: " + pretPornire + " RON"; }
}