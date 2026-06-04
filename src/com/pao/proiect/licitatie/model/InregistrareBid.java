package com.pao.proiect.licitatie.model;

import java.time.LocalDateTime;

public final class InregistrareBid {
    private final int produsId;
    private final int licitatorId;
    private final double suma;
    private final LocalDateTime timestamp;

    public InregistrareBid(int produsId, int licitatorId, double suma) {
        this.produsId = produsId;
        this.licitatorId = licitatorId;
        this.suma = suma;
        this.timestamp = LocalDateTime.now();
    }

    public InregistrareBid(int produsId, int licitatorId, double suma, LocalDateTime timestamp) {
        this.produsId = produsId;
        this.licitatorId = licitatorId;
        this.suma = suma;
        this.timestamp = timestamp;
    }

    public int getProdusId() { return produsId; }
    public int getLicitatorId() { return licitatorId; }
    public double getSuma() { return suma; }
    public LocalDateTime getTimestamp() { return timestamp; }
}