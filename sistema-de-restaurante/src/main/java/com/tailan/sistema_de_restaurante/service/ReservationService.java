package com.tailan.sistema_de_restaurante.service;

import com.tailan.sistema_de_restaurante.dto.mesa.MesaRequestDto;
import com.tailan.sistema_de_restaurante.dto.reserva.ReservaRequestDto;
import com.tailan.sistema_de_restaurante.dto.reserva.ReservaResponseDto;

import java.util.List;
import java.util.UUID;

public interface ReservationService {
    public ReservaResponseDto reservarMesa(UUID mesaId, ReservaRequestDto reservaRequestDto);
    public List<ReservaResponseDto> listReservationsResponse();
    public void cancelReserva(UUID reservaId);
}
