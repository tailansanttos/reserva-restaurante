package com.tailan.sistema_de_restaurante.exceptions;

public class ReservationIndisponivelException extends RuntimeException {
    public ReservationIndisponivelException(String message) {
        super(message);
    }
}
