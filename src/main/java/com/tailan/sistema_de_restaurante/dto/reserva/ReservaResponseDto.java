package com.tailan.sistema_de_restaurante.dto.reserva;

import com.tailan.sistema_de_restaurante.dto.mesa.MesaResponseDto;
import com.tailan.sistema_de_restaurante.dto.user.UserResponseDto;
import com.tailan.sistema_de_restaurante.model.reserva.StatusReserva;

import java.time.LocalDateTime;
import java.util.UUID;

public record ReservaResponseDto(UserResponseDto user,
                                 MesaResponseDto mesa,
                                 LocalDateTime reservationDate,
                                 StatusReserva reservationStatus,
                                 UUID reservaId) {
}
