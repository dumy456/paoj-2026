package com.pao.proiect.licitatie.model;

public class Licitator extends Utilizator {
    private double buget;

    public Licitator(int id, String nume, String email, double buget) {
        super(id, nume, email);
        this.buget = buget;
    }

    public double getBuget() {
        return buget;
    }

    public void setBuget(double buget) {
        this.buget = buget;
    }

    @Override
    public String getTip() {
        return "LICITATOR";
    }
}
