package com.tailan.sistema_de_restaurante.exceptions;

public class MesaNotFoundException extends RuntimeException {
    public MesaNotFoundException(String message) {
        super(message);
    }
}
