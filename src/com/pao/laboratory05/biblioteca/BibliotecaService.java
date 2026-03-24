package com.pao.laboratory05.biblioteca;
import java.util.Arrays;
import java.util.Comparator;

public class BibliotecaService {
    private Carte[] carti=new Carte[0];
    private static class Holder{
        public static BibliotecaService instance= new BibliotecaService();
    }
    public static BibliotecaService getInstance(){
        return Holder.instance;
    }
    private BibliotecaService(){}
    public void addCarte(Carte carte){
        Carte[] aux = new Carte[carti.length+1];
        System.arraycopy(carti,0,aux,0,carti.length);
        aux[carti.length]=carte;
        carti=aux;
        System.out.println(String.format("Carte adaugata: %s",carte.getTitlu()));
    }
    public void listSortedByRating(){
        Carte[] copy=carti.clone();
        Arrays.sort(copy);
        for(int i=0;i<copy.length;i++){
            System.out.println(i+1+". "+copy[i].toString());
        }
    }
    public void listSortedBy(Comparator<Carte> comparator){
        Carte[] copy=carti.clone();
        Arrays.sort(copy,comparator);
        for(int i=0;i<copy.length;i++){
            System.out.println(i+1+". "+copy[i].toString());
        }
    }

}
