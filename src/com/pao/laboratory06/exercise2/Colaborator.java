package com.pao.laboratory06.exercise2;

public abstract class Colaborator implements IOperatiiCitireScriere{
    protected String nume;
    protected String prenume;
    protected Double venitBrutLunar;
    protected abstract double calculeazaVenitNetAnual();
}

