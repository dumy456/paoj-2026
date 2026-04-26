package com.pao.proiect.licitatie.model;

public class Vanzator extends Utilizator{
    protected double rating;

    public Vanzator(int id, String nume, String email, double rating) {
        super(id, nume, email);
        this.rating = rating;
    }


}

