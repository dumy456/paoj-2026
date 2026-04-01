package com.pao.laboratory06.exercise2;

import java.util.Scanner;

public class SRLColaborator extends Colaborator implements PersoanaJuridica{
    private double cheltuieliLunare;
    @Override
    protected double calculeazaVenitNetAnual() {
        return (venitBrutLunar - cheltuieliLunare) * 12 * 0.84;
    }

    @Override
    public void citeste(Scanner in) {
        this.nume=in.next().trim();
        this.prenume=in.next().trim();
        this.venitBrutLunar=in.nextDouble();
        this.cheltuieliLunare=in.nextDouble();
    }

    @Override
    public void afiseaza() {
        System.out.println(tipContract()+": "+nume+" "+ prenume+", venit net anual: "+calculeazaVenitNetAnual()+" lei");
    }

    @Override
    public String tipContract() {
        return TipColaborator.SRL.name();
    }
}
