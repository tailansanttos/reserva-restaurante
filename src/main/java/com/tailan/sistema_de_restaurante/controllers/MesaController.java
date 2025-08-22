package com.tailan.sistema_de_restaurante.controllers;

import com.tailan.sistema_de_restaurante.dto.mesa.MesaRequestDto;
import com.tailan.sistema_de_restaurante.dto.api.ApiResponseDto;
import com.tailan.sistema_de_restaurante.dto.mesa.MesaResponseDto;
import com.tailan.sistema_de_restaurante.dto.user.UserRegisterDto;
import com.tailan.sistema_de_restaurante.service.MesaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.UUID;
@CrossOrigin("*")
@Tag(name = "Gerenciamento de mesas", description = "APIs para gerenciar mesas, somente ADMINS")
@RestController
@RequestMapping("/mesas")
public class MesaController {
    private final MesaService mesaService;

    public MesaController(MesaService mesaService) {
        this.mesaService = mesaService;
    }

    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Registrar nova mesa", description = "Registrar uma nova mesa no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Mesa criada com sucesso."
                    ,content = @Content(schema = @Schema(implementation = MesaRequestDto.class))),
    })
    @PostMapping
    public ResponseEntity<ApiResponseDto> adicionarMesa(@RequestBody @Valid MesaRequestDto mesaRequest){
        MesaResponseDto mesaResponse = mesaService.createMesa( mesaRequest );
        ApiResponseDto response = new ApiResponseDto(mesaResponse, "Mesa adicionado com sucesso");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Listar mesas disponíveis", description = "Retorna uma lista de todas as mesas disponíveis para reserva.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de mesas retornada com sucesso.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MesaResponseDto.class))),
    })
    @GetMapping
    public ResponseEntity<ApiResponseDto> listarMesas(){
        List<MesaResponseDto> mesas = mesaService.getMesasDisponibles();
        ApiResponseDto response = new ApiResponseDto(mesas, "Lista de mesas disponiveis.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Atualizar status da mesa", description = "Atualiza o status de uma mesa (ex: para 'INATIVA' se estiver quebrada). Apenas para ADMINS.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status da mesa atualizado com sucesso.",
                    content = @Content(schema = @Schema(implementation = MesaResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Mesa não encontrada no sistema.",
                    content = @Content(schema = @Schema(hidden = true)))
    })
    @PutMapping("/{mesaId}")
    public ResponseEntity<ApiResponseDto> atualizarMesa(@PathVariable("mesaId")UUID mesaId){
        MesaResponseDto mesaUpdate = mesaService.updateStatusMesa( mesaId );
        ApiResponseDto response = new ApiResponseDto(mesaUpdate, "Mesa atualizada com sucesso");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Deletar mesa", description = "Deleta uma mesa do sistema. Apenas para ADMINS.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Mesa deletada com sucesso.",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", description = "Mesa não encontrada.",
                    content = @Content(schema = @Schema(hidden = true)))
    })
    @DeleteMapping("/{mesaId}")
    public ResponseEntity<ApiResponseDto> deletarMesa(@PathVariable("mesaId")UUID mesaId){
        mesaService.deleteMesa(mesaId);
        ApiResponseDto response = new ApiResponseDto(mesaId, "Mesa deletada com sucesso.");
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }


    }
