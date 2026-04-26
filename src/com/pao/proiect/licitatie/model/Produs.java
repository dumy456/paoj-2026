package com.pao.proiect.licitatie.model;

// 5. Clasa Produs (pentru Comparable)
public class Produs implements Comparable<Produs> {
    private int id;
    private String nume;
    private double pretPornire;

    public Produs(int id, String nume, double pretPornire) {
        this.id = id;
        this.nume = nume;
        this.pretPornire = pretPornire;
    }

    public int getId() {
        return id;
    }

    public String getNume() {
        return nume;
    }

    public double getPretPornire() {
        return pretPornire;
    }

    @Override
    public int compareTo(Produs o) {
        return Double.compare(this.pretPornire, o.pretPornire);
    }

    @Override
    public String toString() {
        return "Produs: " + nume + " (Pret: " + pretPornire + ")";
    }
}
