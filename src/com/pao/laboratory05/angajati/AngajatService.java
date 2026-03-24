package com.pao.laboratory05.angajati;

import com.pao.laboratory05.biblioteca.BibliotecaService;

import java.util.Arrays;

public class AngajatService {
    private Angajat[] angajati=new Angajat[0];
    private static class Holder{
        public static AngajatService instance= new AngajatService();
    }
    public static AngajatService getInstance(){
        return AngajatService.Holder.instance;
    }
    private AngajatService(){}
    void addAngajat(Angajat a){
        Angajat[] aux= new Angajat[angajati.length+1];
        System.arraycopy(angajati,0,aux,0,angajati.length);
        aux[angajati.length]=a;
        angajati=aux;
        System.out.println(String.format("Angajat adaugat: %s",a.getNume()));
    }
    public void printAll(){
        for(Angajat a:angajati)
            System.out.println(a.toString());
    }
    public void listBySalary(){
        Angajat[] copy= angajati.clone();
        Arrays.sort(copy);
        for(Angajat a:copy)
            System.out.println(a.toString());
    }
    public void findByDepartament(String numeDept) {
        boolean found=false;
        for(Angajat a: angajati){
            if(a.getDepartament().nume().equalsIgnoreCase(numeDept)){
                System.out.println(a.toString());
                found=true;
            }
        }
        if(!found){
            System.out.println("Niciun angajat in departamentul: "+numeDept);
        }
    }
}
