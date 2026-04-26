package com.pao.proiect.licitatie.exception;

// A doua excepție custom
public class ResursaNegasitaException extends RuntimeException {
    public ResursaNegasitaException(String mesaj) {
        super(mesaj);
    }
}
