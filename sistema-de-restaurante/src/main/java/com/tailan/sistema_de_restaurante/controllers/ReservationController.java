package com.tailan.sistema_de_restaurante.controllers;

import com.tailan.sistema_de_restaurante.dto.api.ApiResponseDto;
import com.tailan.sistema_de_restaurante.dto.reserva.ReservaRequestDto;
import com.tailan.sistema_de_restaurante.dto.reserva.ReservaResponseDto;

import com.tailan.sistema_de_restaurante.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
@CrossOrigin("*")
@Tag(name = "Gerenciamento de Reservas", description = "APIs para que usuários autenticados gerenciem suas reservas")
@RestController
@RequestMapping("/reservas")
public class ReservationController {
    private final ReservationService reservaService;

    public ReservationController(ReservationService reservaService) {
        this.reservaService = reservaService;
    }

    @Operation(summary = "Criar nova reserva", description = "Cria uma nova reserva para uma mesa específica em nome do usuário logado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reserva realizada com sucesso.",
                    content = @Content(schema = @Schema(implementation = ReservaResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Dados da requisição inválidos, mesa não disponível ou horário fora do expediente.",
                    content = @Content(schema = @Schema(hidden = true)))
    })
    @PostMapping("/{mesaId}")
    public ResponseEntity<ApiResponseDto> reservarMesa(@PathVariable("mesaId")UUID mesaId,
                                                       @RequestBody @Valid ReservaRequestDto reservaRequestDto){
        ReservaResponseDto reservaResponse = reservaService.reservarMesa(mesaId, reservaRequestDto);
        ApiResponseDto apiResponse = new ApiResponseDto(reservaResponse, "Reserva realizada com sucesso!");
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @Operation(summary = "Listar reservas do usuário", description = "Retorna uma lista de todas as reservas do usuário autenticado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de reservas retornada com sucesso.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReservaResponseDto.class))),
    })
    @GetMapping
    public ResponseEntity<ApiResponseDto> listarReservas(){
        List<ReservaResponseDto> listReservaResponse = reservaService.listReservationsResponse();
        ApiResponseDto apiResponse = new ApiResponseDto(listReservaResponse, "Suas reservas.");
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @Operation(summary = "Cancelar reserva", description = "Cancela uma reserva existente. Apenas o usuário que a criou pode cancelar.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Reserva cancelada com sucesso.",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "403", description = "Acesso negado. O usuário não tem permissão para cancelar esta reserva.",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", description = "Reserva não encontrada.",
                    content = @Content(schema = @Schema(hidden = true)))
    })
    @PutMapping("/{reservaId}")
    public ResponseEntity<ApiResponseDto> cancelarReserva(@PathVariable("reservaId")UUID reservaId){
       reservaService.cancelReserva(reservaId);
        ApiResponseDto apiResponse = new ApiResponseDto(reservaId, "Reserva cancelada com sucesso!");
        return new ResponseEntity<>(apiResponse, HttpStatus.NO_CONTENT);
    }
}
