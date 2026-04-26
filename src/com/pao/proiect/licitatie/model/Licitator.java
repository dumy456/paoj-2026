package com.pao.proiect.licitatie.model;

// 2. Moștenire Nivel 1
public class Licitator extends Utilizator {
    private double sold;

    public Licitator(int id, String nume, String email, double sold) {
        super(id, nume, email);
        this.sold = sold;
    }


    public double getSold() {
        return sold;
    }

    public void retrageBani(double suma) {
        this.sold -= suma;
    }
}
