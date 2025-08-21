package com.tailan.sistema_de_restaurante.dto.reserva;

import com.tailan.sistema_de_restaurante.model.reserva.StatusReserva;

import java.time.LocalDateTime;

public record ReservaRequestDto(LocalDateTime reservationDate, Integer quantidadePessoas ) {
}
