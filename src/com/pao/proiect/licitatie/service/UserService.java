package com.pao.proiect.licitatie.service;

import com.pao.proiect.licitatie.model.Utilizator;
import java.util.*;

public class UserService {
    private static UserService instance;
    private Set<Utilizator> utilizatori = new HashSet<>();

    private UserService() {}

    public static UserService getInstance() {
        if (instance == null) instance = new UserService();
        return instance;
    }

    public void inregistreazaUtilizator(Utilizator u) {
        if (u == null) {
            System.out.println("Eroare: Încercare de înregistrare a unui utilizator nul.");
            return;
        }
        utilizatori.add(u);
        System.out.println("Utilizator înregistrat: " + u.getNume());
    }

    public void stergeUtilizator(int id) {
        utilizatori.removeIf(u -> u.getId() == id);
        System.out.println("Utilizator cu ID " + id + " a fost eliminat.");
    }

    public void afiseazaUtilizatori(){
        for(Utilizator u: utilizatori){
            System.out.println(u.toString());
        }
    }
}