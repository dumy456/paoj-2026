package com.pao.proiect.licitatie;

import com.pao.proiect.licitatie.model.*;
import com.pao.proiect.licitatie.service.*;
import com.pao.proiect.licitatie.exception.*;

public class Main {
    public static void main(String[] args) {
        LicitatieService licitatieService = LicitatieService.getInstance();
        UserService userService = UserService.getInstance();

        System.out.println("--- ETAPA II: PERSISTENȚĂ DB + TRANZACȚII + AUDIT ---");

        Vanzator v1 = new Vanzator(0, "ElectroShop", "contact@electro.ro", 4.9);
        userService.inregistreazaUtilizator(v1);

        Licitator l1 = new Licitator(0, "Andrei Licitatorul", "andrei@yahoo.com", 10000.0);
        userService.inregistreazaUtilizator(l1);

        licitatieService.adaugaProdus(new Produs(0, "iPhone 15 Pro", 4500.0, v1.getId()));
        licitatieService.adaugaProdus(new Produs(0, "Casti Bluetooth", 300.0, v1.getId()));

        System.out.println("\nProduse în DB (Sortate):");
        licitatieService.afiseazaProduseSortateDupaPret();

        System.out.println("\nCăutare produs ID 1: " + licitatieService.cautaProdus(1));

        try {
            licitatieService.plaseazaOferta(l1, 1, 4800.0);
        } catch (OfertaInvalidaException e) {
            System.err.println(e.getMessage());
        }

        System.out.println("\nNumăr oferte pentru produsul 1: " + licitatieService.numarOfertePerProdus(1));

        licitatieService.afiseazaProduseAccesibile(1000.0);

        licitatieService.afiseazaIstoricComplect();

        System.out.println("\nExistă email-ul 'andrei@yahoo.com'? " + userService.existaEmail("andrei@yahoo.com"));

        // userService.stergeUtilizator(l1.getId());

        System.out.println("\nVerifică fișierul 'audit.csv' generat în rădăcina proiectului!");
    }
}