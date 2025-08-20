package com.tailan.sistema_de_restaurante.controllers;

import com.tailan.sistema_de_restaurante.dto.api.ApiResponseDto;
import com.tailan.sistema_de_restaurante.dto.reserva.ReservaRequestDto;
import com.tailan.sistema_de_restaurante.dto.reserva.ReservaResponseDto;
import com.tailan.sistema_de_restaurante.service.ReservaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/reservas")
public class ReservationController {
    private final ReservaService reservaService;

    public ReservationController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @PostMapping("/{mesaId}")
    public ResponseEntity<ApiResponseDto> reservarMesa(@PathVariable("mesaId")UUID mesaId, @RequestBody @Valid ReservaRequestDto reservaRequestDto){
        ReservaResponseDto reservaResponse = reservaService.reservarMesa(mesaId, reservaRequestDto);
        ApiResponseDto apiResponse = new ApiResponseDto(reservaResponse, "Reserva realizada com sucesso!");
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponseDto> listarReservas(){
        List<ReservaResponseDto> listReservaResponse = reservaService.listReservationsResponse();
        ApiResponseDto apiResponse = new ApiResponseDto(listReservaResponse, "Reserva realizada com sucesso!");
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/{reservaId}")
    public ResponseEntity<ApiResponseDto> cancelarReserva(@PathVariable("reservaId")UUID reservaId){
       reservaService.cancelReserva(reservaId);
        ApiResponseDto apiResponse = new ApiResponseDto(reservaId, "Reserva realizada com sucesso!");
        return new ResponseEntity<>(apiResponse, HttpStatus.NO_CONTENT);
    }
}
