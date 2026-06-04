package com.pao.proiect.licitatie.model;

public abstract class Utilizator {
    protected int id;
    protected String nume;
    protected String email;

    public Utilizator(int id, String nume, String email) {
        this.id = id; this.nume = nume; this.email = email;
    }
    public abstract String getTip();
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNume() { return nume; }
    public String getEmail() { return email; }

    @Override
    public String toString() { return "[" + getTip() + "] " + nume + " (ID: " + id + ")"; }
}
