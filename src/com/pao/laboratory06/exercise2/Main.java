package com.pao.laboratory06.exercise2;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        // Vezi Readme.md pentru cerințe
        Scanner scanner=new Scanner(System.in);
        int numarColaborator=scanner.nextInt();
        Colaborator[] colaborators= new Colaborator[numarColaborator];
        double venitmaxim=0;
        Colaborator topg=null;
        for(int i=0;i<numarColaborator;i++){
            String tipString=scanner.next().trim();
            TipColaborator tip=TipColaborator.valueOf(tipString);
            switch(tip){
                case CIM:
                    colaborators[i]=new CIMColaborator();
                    break;
                case PFA:
                    colaborators[i]=new PFAColaborator();
                    break;
                case SRL:
                    colaborators[i]=new SRLColaborator();
                    break;

            }
            colaborators[i].citeste(scanner);
            colaborators[i].afiseaza();
            if(venitmaxim<colaborators[i].calculeazaVenitNetAnual()){
                venitmaxim=colaborators[i].calculeazaVenitNetAnual();
                topg=colaborators[i];
            }
        }
        System.out.print("Colaborator cu venit net maxim: ");
        topg.afiseaza();
        System.out.println("Colaboratori persoane juridice");
        for(int i=0;i<numarColaborator;i++){
            if (colaborators[i] instanceof PersoanaJuridica){
                colaborators[i].afiseaza();
            }
        }
        System.out.println("Sume si numar colaboratori pe tip: ");
        for(TipColaborator t: TipColaborator.values()){
            double suma=0;
            int nr=0;
            for(int i=0;i<numarColaborator;i++){
                if(colaborators[i].tipContract().equals(t.name())){
                    suma+=colaborators[i].calculeazaVenitNetAnual();
                    nr++;
                }
            }
            System.out.println(t.name()+": suma = "+suma+" lei, numar = "+nr);
        }
//        Collections.sort(colaborators,(Colaborator c1, Colaborator c2)-> Double.compare(c1.calculeazaVenitNetAnual(), c2.calculeazaVenitNetAnual()));
        List<Colaborator> aux=Arrays.stream(colaborators).sorted((Colaborator c1, Colaborator c2)-> Double.compare(c2.calculeazaVenitNetAnual(), c1.calculeazaVenitNetAnual())).collect(Collectors.toList());
        for(int i=0;i<numarColaborator;i++){
            aux.get(i).afiseaza();
        }
    }
}