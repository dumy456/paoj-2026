package com.pao.laboratory05.angajati;

import java.util.Scanner;

/**
 * Exercise 3 — Angajați
 *
 * Cerințele complete se află în:
 *   src/com/pao/laboratory05/Readme.md  →  secțiunea "Exercise 3 — Angajați"
 *
 * Creează fișierele de la zero în acest pachet, apoi rulează Main.java
 * pentru a verifica output-ul așteptat din Readme.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Cerințele se află în Readme.md — secțiunea Exercise 3.");
        Scanner scanner= new Scanner(System.in);
        AngajatService service=AngajatService.getInstance();
        while (true) {
            System.out.println("\n===== Gestionare Angajați =====");
            System.out.println("1. Adaugă angajat");
            System.out.println("2. Listare după salariu");
            System.out.println("3. Caută după departament");
            System.out.println("0. Ieșire");
            System.out.print("Opțiune: ");
            // citește opțiunea și execută acțiunea
            String option = scanner.nextLine().trim();
           switch (option) {
                    case "1":
                        System.out.print("Nume: ");
                        String nume = scanner.nextLine().trim();
                        System.out.print("Departament (nume): ");
                        String dname= scanner.nextLine().trim();
                        System.out.println("Departament (locatie): ");
                        String locatie= scanner.nextLine().trim();
                        System.out.println("Salariu: ");
                        Double salariu = Double.parseDouble(scanner.nextLine().trim());
                        Departament dep=new Departament(dname,locatie);
                        Angajat a= new Angajat(nume,dep,salariu);
                        service.addAngajat(a);
                        break;

                    case "2":
                        service.listBySalary();
                        break;

                    case "3":
                        System.out.println("Departament: ");
                        String numeDept=scanner.nextLine().trim();
                        service.findByDepartament(numeDept);
                        break;
                    case "0":
                        System.out.println("La revedere!");
                        System.exit(0);
                        break;

                    default:
                        System.out.println("Opțiune invalidă.");
                }
        }
    }
}
