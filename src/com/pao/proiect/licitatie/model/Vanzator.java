package com.pao.proiect.licitatie.model;

public class Vanzator extends Utilizator {
    private double rating;

    public Vanzator(int id, String nume, String email, double rating) {
        super(id, nume, email);
        this.rating = rating;
    }

    public double getRating() {
        return rating;
    }

    @Override
    public String getTip() {
        return "VANZATOR";
    }
}
