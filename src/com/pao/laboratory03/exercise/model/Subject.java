package com.pao.laboratory03.exercise.model;

public enum Subject {
    PAOJ("Programare Orientata pe Obiecte",6){},
    BD("Baza de Date",4){},
    SO("Sisteme de Operare", 4){},
    RC("Repetitie Calculatoare",3){};
    private String fullname;
    private int credits;
    Subject(String fullname, int credits){
        this.fullname=fullname;
        this.credits=credits;
    }

    public int getCredits() {
        return credits;
    }

    public String getFullname() {
        return fullname;
    }
    @Override
    public String toString(){ return name()+" ("+fullname+", "+credits+")";}

}
