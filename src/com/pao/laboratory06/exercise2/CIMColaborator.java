package com.pao.laboratory06.exercise2;

import java.util.Scanner;

public class CIMColaborator extends Colaborator implements PersoanaFizica{
    private boolean bonus;
    @Override
    protected double calculeazaVenitNetAnual() {
        double rez=venitBrutLunar*12*0.55;
        if(areBonus()){
            return rez*1.1;
        }else{
            return rez;
        }
    }
    @Override
    public void citeste(Scanner in) {
        this.nume=in.next().trim();
        this.prenume=in.next().trim();
        this.venitBrutLunar=in.nextDouble();
        String bonus=in.next().trim();
        if(bonus.equals("DA")){
            this.bonus=true;
        }else{
            this.bonus=false;
        }
    }

    @Override
    public void afiseaza() {
        System.out.println(tipContract()+": "+nume+" "+ prenume+", venit net anual: "+String.format("%.2f",calculeazaVenitNetAnual())+" lei");
    }

    @Override
    public String tipContract() {
        return TipColaborator.CIM.name();
    }

    @Override
    public boolean areBonus() {
        return bonus;
    }
}
