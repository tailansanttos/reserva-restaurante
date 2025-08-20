package com.tailan.sistema_de_restaurante.mapper;

import com.tailan.sistema_de_restaurante.dto.mesa.MesaResponseDto;
import com.tailan.sistema_de_restaurante.dto.reserva.ReservaResponseDto;
import com.tailan.sistema_de_restaurante.dto.user.UserResponseDto;
import com.tailan.sistema_de_restaurante.model.reserva.Reservation;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReservaMapper {
    public ReservaResponseDto entityToDtoResponse(Reservation reservation) {
        UserResponseDto userResponseDto = new UserResponseDto(reservation.getUser().getId(), reservation.getUser().getEmail(), reservation.getUser().getRoles());
        MesaResponseDto mesaResponseDto = new MesaResponseDto(reservation.getMesa().getId(), reservation.getMesa().getName(), reservation.getMesa().getCapacity(), reservation.getMesa().getStatus());
        return new ReservaResponseDto(userResponseDto, mesaResponseDto, reservation.getReservationDate(), reservation.getStatus(), reservation.getId());
    }

    public List<ReservaResponseDto> listReservationResponseDto(List<Reservation> reservations) {
        return  reservations.stream().map(this::entityToDtoResponse).collect(Collectors.toList());
    }

}
