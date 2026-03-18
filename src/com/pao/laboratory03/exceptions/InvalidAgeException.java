package com.pao.laboratory03.exceptions;

public class InvalidAgeException extends RuntimeException {
    public InvalidAgeException(int age) {
        super("Varsta "+age+" nu este valida (0-150)");
    }
}
