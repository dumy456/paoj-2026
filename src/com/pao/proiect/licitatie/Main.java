package com.pao.proiect.licitatie;

import com.pao.proiect.licitatie.model.*;
import com.pao.proiect.licitatie.service.*;
import com.pao.proiect.licitatie.exception.*;

import java.sql.SQLOutput;

public class Main {
    public static void main(String[] args) {
        LicitatieService licitatieService = LicitatieService.getInstance();
        UserService userService = UserService.getInstance();

        System.out.println("--- DEMO SISTEM LICITATII ---");
        // 1
        Licitator l1 = new Licitator(1, "Andrei", "andrei@yahoo.com", 5000);
        userService.inregistreazaUtilizator(l1);
        // 2
        Vanzator v1 = new Vanzator(2, "ElectroShop", "contact@electro.ro",3);
        userService.inregistreazaUtilizator(v1);
        // 3
        licitatieService.adaugaProdus(new Produs(101, "iPhone 15", 4000));
        licitatieService.adaugaProdus(new Produs(102, "Televizor 4K", 1500));
        licitatieService.adaugaProdus(new Produs(103, "Căști Bluetooth", 200));
        // 4
        System.out.println("\nProduse disponibile (sortate):");
        licitatieService.afiseazaProduseSortateDupaPret();

        // 5
        System.out.println("\nCăutare ID 101: " + licitatieService.cautaProdus(101));

        // 6
        try {
            licitatieService.plaseazaOferta(l1, 102, 1600);
        } catch (OfertaInvalidaException e) { System.out.println(e.getMessage()); }

        // 6(eroare)
        try {
            licitatieService.plaseazaOferta(l1, 101, 3000);
        } catch (OfertaInvalidaException e) {
            System.out.println("Eroare capturată: " + e.getMessage());
        }
        // 7
        System.out.println("Numar de oferte pt un produs(dupa id)");
        System.out.println(licitatieService.numarOfertePerProdus(102));
        // 8
        System.out.println("Cea mai mare oferta pentru produs(dupa id)");
        licitatieService.getCeaMaiMareOferta(102);
        // 9
        licitatieService.afiseazaIstoric();
        //10
        userService.stergeUtilizator(1);
        System.out.println("Utilizatori rămași: ");
        userService.afiseazaUtilizatori();
        //11
        licitatieService.afiseazaProduseAccesibile(2000);
    }
}