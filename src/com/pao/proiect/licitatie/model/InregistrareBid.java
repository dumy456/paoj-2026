package com.pao.proiect.licitatie.model;

import java.time.LocalDateTime;


public final class InregistrareBid {
    private final int idLicitatie;
    private final double suma;
    private final LocalDateTime timestamp;

    public InregistrareBid(int idLicitatie, double suma) {
        this.idLicitatie = idLicitatie;
        this.suma = suma;
        this.timestamp = LocalDateTime.now();
    }

    public int getIdLicitatie() {
        return idLicitatie;
    }

    public double getSuma() {
        return suma;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
