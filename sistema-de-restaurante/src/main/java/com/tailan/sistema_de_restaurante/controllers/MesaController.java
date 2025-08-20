package com.tailan.sistema_de_restaurante.controllers;

import com.tailan.sistema_de_restaurante.dto.mesa.MesaRequestDto;
import com.tailan.sistema_de_restaurante.dto.api.ApiResponseDto;
import com.tailan.sistema_de_restaurante.dto.mesa.MesaResponseDto;
import com.tailan.sistema_de_restaurante.service.MesaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.plaf.SpinnerUI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/mesas")
public class MesaController {
    private final MesaService mesaService;

    public MesaController(MesaService mesaService) {
        this.mesaService = mesaService;
    }

    @PostMapping
    public ResponseEntity<ApiResponseDto> adicionarMesa(@RequestBody @Valid MesaRequestDto mesaRequest){
        MesaResponseDto mesaResponse = mesaService.createMesa( mesaRequest );
        ApiResponseDto response = new ApiResponseDto(mesaResponse, "Mesa adicionado com sucesso");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponseDto> listarMesas(){
        List<MesaResponseDto> mesas = mesaService.getMesasDisponibles();
        ApiResponseDto response = new ApiResponseDto(mesas, "Lista de mesas disponiveis.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{mesaId}")
    public ResponseEntity<ApiResponseDto> atualizarMesa(@PathVariable("mesaId")UUID mesaId){
        MesaResponseDto mesaUpdate = mesaService.updateStatusMesa( mesaId );
        ApiResponseDto response = new ApiResponseDto(mesaUpdate, "Mesa atualizada com sucesso");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{mesaId}")
    public ResponseEntity<ApiResponseDto> deletarMesa(@PathVariable("mesaId")UUID mesaId){
        mesaService.deleteMesa(mesaId);
        ApiResponseDto response = new ApiResponseDto(mesaId, "Mesa deletada com sucesso.");
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }


    }
