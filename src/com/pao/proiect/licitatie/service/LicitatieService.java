package com.pao.proiect.licitatie.service;

import com.pao.proiect.licitatie.model.*;
import com.pao.proiect.licitatie.exception.*;
import java.util.*;

public class LicitatieService {
    private static LicitatieService instance;
    
    private Map<Integer, Produs> produseInLicitatie = new HashMap<>();
    private List<InregistrareBid> istoricBiduri = new ArrayList<>();

    private LicitatieService() {}

    public static LicitatieService getInstance() {
        if (instance == null) instance = new LicitatieService();
        return instance;
    }

    public void adaugaProdus(Produs p) {
        if (p == null || p.getNume()==null || p.getNume().trim().isEmpty()){
            System.out.println("Eroare: Produs invalid (null sau nume lipsă).");
            return;
        }
        if (p.getPretPornire() < 0) {
            System.out.println("Eroare: Prețul nu poate fi negativ.");
            return;
        }
        produseInLicitatie.put(p.getId(), p);
    }

    public void plaseazaOferta(Licitator l,int produsId, double suma) throws OfertaInvalidaException {
        if (l == null) {
            throw new IllegalArgumentException("Licitatorul nu poate fi null!");
        }
        Produs p = produseInLicitatie.get(produsId);
        if (p == null) throw new ResursaNegasitaException("Produsul nu exista!");
        if (suma <= p.getPretPornire()) {
            throw new OfertaInvalidaException("Suma trebuie sa fie mai mare decat pretul actual!");
        }
        istoricBiduri.add(new InregistrareBid(produsId, suma));
        System.out.println("Oferta de " + suma + " oferita de "+l.getNume()+" acceptata pentru " + p.getNume());
    }

    public void afiseazaProduseSortateDupaPret() {
        List<Produs> lista = new ArrayList<>(produseInLicitatie.values());
        Collections.sort(lista);
        for(Produs p: lista) {
            System.out.println(p);
        }
    }
    public Produs cautaProdus(Integer id){
        return produseInLicitatie.get(id);
    }
    public long numarOfertePerProdus(int produsId) {
        return istoricBiduri.stream()
                .filter(bid -> bid.getIdLicitatie() == produsId)
                .count();
    }

    public void afiseazaIstoric() {
        istoricBiduri.forEach(bid -> System.out.println("Bid: " + bid.getSuma() + " la ora " + bid.getTimestamp()));
    }
    public double getCeaMaiMareOferta(int produsId) {
        return istoricBiduri.stream()
                .filter(bid -> bid.getIdLicitatie() == produsId)
                .mapToDouble(InregistrareBid::getSuma)
                .max()
                .orElse(0.0);
    }

    public void afiseazaProduseAccesibile(double bugetMaxim) {
        System.out.println("\n--- Produse accesibile sub bugetul de " + bugetMaxim + " RON ---");

        boolean gasit = produseInLicitatie.values().stream()
                .filter(p -> p.getPretPornire() <= bugetMaxim)
                .peek(System.out::println)
                .count() > 0;
        if (!gasit) {
            System.out.println("Nu există produse atât de ieftine în acest moment.");
        }
    }
}